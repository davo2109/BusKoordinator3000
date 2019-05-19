package at.fhv.itb.ss19.busmaster.application;

import at.fhv.itb.ss19.busmaster.domain.DayType;
import at.fhv.itb.ss19.busmaster.domain.MonthEnum;
import at.fhv.itb.ss19.busmaster.domain.Operation;
import at.fhv.itb.ss19.busmaster.domain.RouteRide;
import at.fhv.itb.ss19.busmaster.domain.security.IOperation;
import at.fhv.itb.ss19.busmaster.domain.security.IRoute;
import at.fhv.itb.ss19.busmaster.domain.security.IRouteRide;
import at.fhv.itb.ss19.busmaster.persistence.CreateOperationFacade;
import at.fhv.itb.ss19.busmaster.persistence.CreateOperationFacadeImpl;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class CreateOperations {
	private CreateOperationFacade _facade;
	private List<Operation> _operations;
	private Map<Long, IOperation> _groupedOperations;
	private Set<LocalDate> _holidays;
	private DayType temporaryDayType;
	private boolean _planningUnitDay;

	public CreateOperations() {
		this(new CreateOperationFacadeImpl());
	}
	
	@SuppressWarnings("deprecation")
	public CreateOperations(CreateOperationFacade facade) {
		_facade = facade;
		_operations = new LinkedList<>();

		// Initialisierung der Feiertage der JollyDay-Klasse für Österreich
		_holidays = new HashSet<>();
		HolidayManager hm = HolidayManager.getInstance(HolidayCalendar.AUSTRIA);
		hm.getHolidays(LocalDate.now().getYear()).forEach(holiday -> _holidays.add(holiday.getDate()));
	}

	public List<? extends IRoute> getValidRoutesOfMonth(int month) {
		return _facade.getValidRoutesByMonth(month);
	}

	public List<? extends IRoute> getValidRoutesOfDay(LocalDate date) {
		return _facade.getValidRoutesByDay(date);
	}

	/**
	 * Alle noch nicht in einer Operation mit dem gleichen Daytype zugeteilten Rides holen
	 * 
	 * @param route		die selektierte Route zu der die Rides geholt werden sollen
	 * @param daytype	
	 * @return			Liste mit den offenen Rides
	 */
	public List<? extends IRouteRide> getOpenRides(IRoute route, DayType daytype) {
		return route.getOpenRouteRides(_operations, daytype);
	}

	public void setPlanningUnitDay(boolean planningUnit) {
		_planningUnitDay = planningUnit;
	}

	public List<? extends IOperation> getOperationsByDate(LocalDate date) {
		_operations = _facade.getOperationsByDate(Date.valueOf(date));
		_groupedOperations = null;
		if (_operations != null && !_operations.isEmpty()) {
			// Wenn Operation grad erstellt wurde und noch keine Rides enthält muss der
			// DayType gesetzt werden, temporär in einer Variable nach der Erstellung
			// gespeichert und hier wieder abgerufen
			_operations.forEach(oper -> {
				if (oper.getDayType() == null) {
					oper.setDayType(temporaryDayType);
				}
			});
			return _operations;
		}

		return new ArrayList<IOperation>();
	}

	/**
	 * Operationen üben den Monat anhand der Checksum gruppieren -> gleiche Checksum
	 * bedeutet gleiche Operation nur am anderen Tag
	 * 
	 * @param month Der zu gruppierende Monat
	 * @return Liste mit den Repräsentanten der jeweiligen Operationgruppierung
	 */
	public List<IOperation> getGroupedOperationsOfMonth(int month) {
		_operations = _facade.getOperationsByMonth(month);
		_groupedOperations = new HashMap<Long, IOperation>();

		for (Operation oper : _operations) {
			// Wenn Operation grad erstellt wurde und noch keine Rides enthält muss der
			// DayType gesetzt werden, temporär in einer Variable nach der Erstellung
			// gespeichert und hier wieder abgerufen
			if (oper.getDayType() == null) {
				oper.setDayType(temporaryDayType);
			}
			_groupedOperations.putIfAbsent(oper.getCheckSum(), oper);
		}

		if (!_groupedOperations.isEmpty()) {
			return _groupedOperations.values().stream().collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * Operation von einem Tag erstellen
	 * 
	 * @param day Tag für den die Operation erstellt werden soll
	 */
	public void createOperationForDay(LocalDate day) {
        createOperations(DayType.getDayTypeOfDate(day), day);
	}

	/**
	 * Operationen über den ganzen Monat mit dem angegebenen Tagtyp erstellen
	 * 
	 * @param daytype Tagtyp für den die Operations erstellt werden sollen
	 * @param month   Monat für den die Operations erstellt werden sollen
	 */
	public void createOperationsForMonth(DayType daytype, int month) {
		LocalDate date = LocalDate.of(LocalDate.now().getYear(), month, 1);
		createOperations(daytype, date);
	}

	private void createOperations(DayType daytype, LocalDate date) {
		temporaryDayType = daytype;
		LocalDate day = date;
		LocalDate stopDate = day.plusDays(1);
		List<Operation> newOperations = new LinkedList<>();

		if (!_planningUnitDay) {
			stopDate = day.plusMonths(1);
		}

		// für jeden Tag bis zum stopDate durchgehen und prüfen
		while (day.isBefore(stopDate)) {
			if ((day.getDayOfWeek() == DayOfWeek.SUNDAY || _holidays.contains(day))
					&& daytype == DayType.SUNDAYANDHOLIDAY) {
				newOperations.add(createOperation(daytype, day));
			} else if (day.getDayOfWeek() == DayOfWeek.SATURDAY && daytype == DayType.SATURDAY) {
				newOperations.add(createOperation(daytype, day));
			} else if ((day.getDayOfWeek() != DayOfWeek.SATURDAY && day.getDayOfWeek() != DayOfWeek.SUNDAY
					&& !_holidays.contains(day)) && daytype == DayType.SCHOOLDAY) {
				newOperations.add(createOperation(daytype, day));
			} else if ((day.getDayOfWeek() != DayOfWeek.SATURDAY && day.getDayOfWeek() != DayOfWeek.SUNDAY
					&& !_holidays.contains(day)) && daytype == DayType.WORKDAY) {
				newOperations.add(createOperation(daytype, day));
			}
			day = day.plusDays(1);
		}

		_facade.saveOperations(newOperations);

		_operations.addAll(newOperations);
	}

	/**
	 * Tatsächliche Operation für einen Tag erstellen um Codeverdoppelung zu
	 * vermeiden
	 * 
	 * @param daytype
	 * @param date
	 * @return
	 */
	private Operation createOperation(DayType daytype, LocalDate date) {
		Operation oper = new Operation();
		oper.setDayType(daytype);
		oper.setDate(date);
		return oper;
	}

	/**
	 * Operation je nach Planungseinheit löschen
	 * 
	 * @param selectedOperation
	 */
	public void deleteOperation(IOperation selectedOperation) {
		Operation oper = (Operation) selectedOperation;

		if (_planningUnitDay) {
			_facade.deleteOperation(oper);
			_operations.remove(oper);
		} else {
			List<Operation> deleteOperation = new LinkedList<>();
			for (Operation operation : _operations) {
				if (operation.getCheckSum() == oper.getCheckSum()) {
					deleteOperation.add(operation);
				}
			}

			for (Operation operation : deleteOperation) {
				_operations.remove(operation);
			}

			_facade.deleteOperations(deleteOperation);
		}
	}

	public void addRideToOperation(IRouteRide selectedRide, IOperation selectedOperation) {
		rideOperationWorker(selectedRide, selectedOperation, true);
	}

	public void removeRideFromOperation(IRouteRide selectedRide, IOperation selectedOperation) {
		rideOperationWorker(selectedRide, selectedOperation, false);
	}

	/**
	 * Methode um eine ausgewählte Fahrt einer Operation zuzuweisen oder zu
	 * entfernen
	 * 
	 * @param selectedRide      gewählte Fahrt
	 * @param selectedOperation gewählte Operation
	 * @param addRide           Fahrt hinzufügen = true oder entfernen = false
	 */
	private void rideOperationWorker(IRouteRide selectedRide, IOperation selectedOperation, boolean addRide) {
		Operation oper = (Operation) selectedOperation;
		RouteRide ride = (RouteRide) selectedRide;
		
		oper.setDayType(selectedRide.getStartTime().getDaytype());

		if (_planningUnitDay) {
			if (addRide) {
				oper.getCapsuledEntity().getRouteRides().add(ride.getCapsuledEntity());
			} else {
				oper.getCapsuledEntity().getRouteRides().remove(ride.getCapsuledEntity());
			}
			_facade.saveOperation(oper);
		} else {
			List<Operation> changedOperations = new LinkedList<>();
			for (Operation operation : _operations) {
				if (operation.getCheckSum() == oper.getCheckSum()) {
					if (addRide) {
						operation.getCapsuledEntity().getRouteRides().add(ride.getCapsuledEntity());
					} else {
						operation.getCapsuledEntity().getRouteRides().remove(ride.getCapsuledEntity());
					}
					changedOperations.add(operation);
				}
			}
			changedOperations.forEach(operation -> operation.invalidateChecksum());
			_facade.saveOperations(changedOperations);
		}
	}

    /**
     * Methode um die Anzahl der Freien Fahrten innerhalb eines Monates oder Tages,
     * je nachdem was im UI gewählt ist zurückzugeben.
     *
     * @param route             - IRoute für gewählte Route
     * @param isPlanningUnitDay - boolean für Zeitraum true = Tag, false = Monat
     * @param month             - Enum Monat
     * @return - int mit Anzahl der freien Fahrten
     */
    public int getOpenRouteRideCount(IRoute route, boolean isPlanningUnitDay, MonthEnum month) {
        Set<IRouteRide> rides = new LinkedHashSet<>(route.getRouteRides());
        int openRidesCount = 0;
        if (isPlanningUnitDay) {
            //Berechnung für einen Tag
            openRidesCount = rides.size();
            for (IRouteRide ride : rides) {
                for (IOperation operation : _operations) {
                    if (operation.getRouteRides().contains(ride)) {
                        openRidesCount--;
                    }
                }
            }
            return openRidesCount;
        } else if (!rides.isEmpty()) {
            //Berechnung für ganzen Monat
            Calendar firstDay = Calendar.getInstance();
            firstDay.set(LocalDate.now().getYear(), month.ordinal(), 1);
            int ridesWorkday = route.getNumberOfRidesPerDayType(DayType.WORKDAY);
            int ridesSunday = route.getNumberOfRidesPerDayType(DayType.SUNDAYANDHOLIDAY);
            int ridesSaturday = route.getNumberOfRidesPerDayType(DayType.SATURDAY);
            //für jeden Tag im Monat Fahrten addiert
            while (firstDay.get(Calendar.MONTH) == month.ordinal()) {
                LocalDate currentDay = firstDay.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (DayType.getDayTypeOfDate(currentDay) == DayType.WORKDAY) {
                    openRidesCount += ridesWorkday;
                } else if (DayType.getDayTypeOfDate(currentDay) == DayType.SUNDAYANDHOLIDAY) {
                    openRidesCount += ridesSunday;
                } else if (DayType.getDayTypeOfDate(currentDay) == DayType.SATURDAY) {
                    openRidesCount += ridesSaturday;
                }
                firstDay.add(Calendar.DATE, 1);
            }
            //besetzte wieder abgezogen
            for (IOperation operation : _operations) {
                Set<IRouteRide> operationRides = new HashSet<>(operation.getRouteRides());
                for (IRouteRide ride : operationRides) {
                    if (rides.contains(ride)) {
                        openRidesCount--;
                    }
                }
            }
            return openRidesCount;
        }
        return 0;

    }
    
    public DayType getDayTypeOfDate(LocalDate date) {
        return DayType.getDayTypeOfDate(date);
    }
}
