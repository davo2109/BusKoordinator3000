package at.fhv.itb.ss19.busmaster.application;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import at.fhv.itb.ss19.busmaster.domain.Available;
import at.fhv.itb.ss19.busmaster.domain.ChangeStatus;
import at.fhv.itb.ss19.busmaster.domain.ConflictingOperation;
import at.fhv.itb.ss19.busmaster.domain.Operation;
import at.fhv.itb.ss19.busmaster.domain.security.IBus;
import at.fhv.itb.ss19.busmaster.domain.security.IOperation;
import at.fhv.itb.ss19.busmaster.persistence.DbFacade;
import at.fhv.itb.ss19.busmaster.persistence.entities.BusEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.SuspendedEntity;

public class AssignBusToOperation {

	private DbFacade _fac;
	private List<Operation> _operations;
	private Map<Long, IOperation> _groupedOperations;
	private boolean _assignmentChanged;
	private List<ConflictingOperation> _conflicts;

	public AssignBusToOperation() {
		_fac = DbFacade.getInstance();
		_conflicts = new LinkedList<>();
	}

	public List<BusEntity> getAllBusses() {
		return _fac.getAllBusses();
	}

	public List<? extends IOperation> getOperationsByDate(LocalDate date) {
		_operations = _fac.getOperationsByDate(Date.valueOf(date));
		if (_operations != null && !_operations.isEmpty()) {
			return _operations;
		}

		return new ArrayList<IOperation>();
	}

	public List<? extends IOperation> reloadOperations(boolean monthGrouping) {
		if (monthGrouping) {
			return _groupedOperations.values().stream().collect(Collectors.toList());
		}
		return _operations;
	}

	/**
	 * Operationen werden mittels der Checksum gruppiert um alle Operations welche
	 * die selben RouteRides besitzen auszugeben und den Bus über den Monat zu
	 * setzen
	 * 
	 * @param month	Für welchen Monat gruppiert werden soll
	 * @return	List mit den gruppierten Operationen
	 */
	public List<IOperation> getGroupedOperationsOfMonth(int month) {
		_operations = _fac.getOperationsByMonth(month);
		_groupedOperations = new HashMap<>();

		for (Operation oper : _operations) {
			_groupedOperations.put(oper.getCheckSum(), oper);
		}

		if (!_groupedOperations.isEmpty()) {
			return _groupedOperations.values().stream().collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

	public IBus getAssignedBus(IOperation selectedItem) {
		return (IBus) selectedItem.getBus();
	}

	public List<BusEntity> getAssignedBusesInOperations() {
		HashMap<Integer, BusEntity> buses = new HashMap<>();
		_operations.forEach(oper -> {
			if (oper.getBus() != null) {
				buses.putIfAbsent(oper.getBus().hashCode(), oper.getBus());
			}
		});

		return buses.values().stream().collect(Collectors.toList());
	}

	public boolean assignBusToOperation(BusEntity selectedBus, IOperation selectedOperation, boolean planningUnitDay) {
		return busToOperation(selectedBus, (Operation) selectedOperation, planningUnitDay, true);
	}

	private boolean busToOperation(BusEntity selectedBus, Operation selectedOperation, boolean planningUnitDay,
			boolean assign) {
		// TODO eventuell im bus überprüfen!
		if ((selectedBus != null || !assign) && selectedOperation != null) {
			_assignmentChanged = true;

			List<Operation> assignedOperations = _fac.getOperationsByBus(selectedBus);
			List<SuspendedEntity> suspendeds = _fac.getSuspendedEntriesByBusAndDate(selectedBus,
					selectedOperation.getDate());

			HashMap<LocalDate, Available> alreadyAssigned = new HashMap<>();

			// Ride oder Suspended in HashMap für Konflikt überprüfung eintragen, nur 1
			// Eintrag per Datum notwending
			assignedOperations.forEach(oper -> {
				alreadyAssigned.putIfAbsent(oper.getDate(), oper);
			});
			suspendeds.forEach(suspended -> {
				LocalDate temp = suspended.getDateFrom().toLocalDate();
				temp.datesUntil(suspended.getDateTo().toLocalDate().plusDays(1))
						.forEach(date -> alreadyAssigned.putIfAbsent(date, suspended));
			});

			// Unterscheidung ob Planungseinheit Tag oder Monat ist -> bei Monat Iteration
			// durch alle gleichen Operations um Bus zuzuweisen
			if (planningUnitDay) {
				return assignBusToSingleOperation(selectedBus, selectedOperation, alreadyAssigned);
			} else {
				boolean noConflict = true;
				for (IOperation oper : _operations) {
					if (oper.getCheckSum() == selectedOperation.getCheckSum()) {
						boolean noConflictSingle = assignBusToSingleOperation(selectedBus, (Operation) oper,
								alreadyAssigned);

						if (!noConflictSingle && noConflict) {
							noConflict = false;
						}
					}
				}

				return noConflict;
			}
		}

		return false;
	}

	private boolean assignBusToSingleOperation(BusEntity selectedBus, Operation selectedOperation,
			HashMap<LocalDate, Available> alreadyAssigned) {
		selectedOperation.setBus(selectedBus);
		((Operation) _operations.get(_operations.indexOf(selectedOperation))).setStatus(ChangeStatus.CHANGED);
		for (Operation oper : _operations) {
			if (oper.getOperationId() == selectedOperation.getOperationId()) {
				if (!alreadyAssigned.containsKey(oper.getDate())) {
					oper.setBus(selectedBus);
					return true;
				}

				_conflicts.add(
						new ConflictingOperation(selectedOperation, (Available) alreadyAssigned.get(oper.getDate())));
			}
		}
		return false;
	}

	public void unassignBusFromOperation(IOperation selectedOperation, boolean planningUnitDay) {
		busToOperation(null, (Operation) selectedOperation, planningUnitDay, false);
	}

	public void saveAssignments() {
		_fac.saveOperations(_operations);
		_conflicts.clear();
		_assignmentChanged = false;
		_operations.forEach(oper -> ((Operation) oper).setStatus(ChangeStatus.OK));
	}

	public void cancelAssignments() {
		_assignmentChanged = false;
		_conflicts.clear();
		_operations.forEach(oper -> ((Operation) oper).setStatus(ChangeStatus.OK));
	}

	public boolean isAssignmentChanged() {
		return _assignmentChanged;
	}

	public List<ConflictingOperation> getConflicts() {
		return _conflicts;
	}
}
