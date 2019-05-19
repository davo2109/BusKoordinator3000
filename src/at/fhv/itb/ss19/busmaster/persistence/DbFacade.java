package at.fhv.itb.ss19.busmaster.persistence;

import at.fhv.itb.ss19.busmaster.domain.Operation;
import at.fhv.itb.ss19.busmaster.domain.Route;
import at.fhv.itb.ss19.busmaster.domain.RouteRide;
import at.fhv.itb.ss19.busmaster.domain.security.IOperation;
import at.fhv.itb.ss19.busmaster.domain.security.IRoute;
import at.fhv.itb.ss19.busmaster.persistence.dao.BusDao;
import at.fhv.itb.ss19.busmaster.persistence.dao.OperationDao;
import at.fhv.itb.ss19.busmaster.persistence.dao.PathDao;
import at.fhv.itb.ss19.busmaster.persistence.dao.PathStationDao;
import at.fhv.itb.ss19.busmaster.persistence.dao.RouteDao;
import at.fhv.itb.ss19.busmaster.persistence.dao.RouteRideDao;
import at.fhv.itb.ss19.busmaster.persistence.dao.StationDao;
import at.fhv.itb.ss19.busmaster.persistence.dao.SuspendedDao;
import at.fhv.itb.ss19.busmaster.persistence.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DbFacade {
	private static final String EXTERNAL_HIBERNATE_CONFIG_FILE = "./config/hibernate.cfg.xml";
	private static final String INTERNAL_HIBERNATE_CONFIG_FILE = "hibernate.cfg.xml";
	private static DbFacade _facade = null;

	private Configuration _conf;
	private SessionFactory _factory;

	private BusDao _busDao;
	private OperationDao _operationDao;
	private RouteRideDao _routeRideDao;
	private PathStationDao _pathStationDao;
	private SuspendedDao _suspendedDao;
	private RouteDao _routeDao;
	private StationDao _stationDao;
	private PathDao _pathDao;

	public static DbFacade getInstance() {
		if (_facade == null) {
			_facade = new DbFacade();
		}

		return _facade;
	}

	private DbFacade() {
		File configFile = new File(EXTERNAL_HIBERNATE_CONFIG_FILE);

		_conf = new Configuration();
		_conf.addAnnotatedClass(BusEntity.class);
		_conf.addAnnotatedClass(DriverEntity.class);
		_conf.addAnnotatedClass(OperationEntity.class);
		_conf.addAnnotatedClass(PathEntity.class);
		_conf.addAnnotatedClass(PathStationEntity.class);
		_conf.addAnnotatedClass(RouteEntity.class);
		_conf.addAnnotatedClass(RouteRideEntity.class);
		_conf.addAnnotatedClass(ShiftEntity.class);
		_conf.addAnnotatedClass(StandbyRideEntity.class);
		_conf.addAnnotatedClass(StartTimeEntity.class);
		_conf.addAnnotatedClass(StationEntity.class);
		_conf.addAnnotatedClass(SuspendedEntity.class);

		StandardServiceRegistryBuilder regBuilder = new StandardServiceRegistryBuilder();

		if (configFile.exists()) {
			regBuilder.configure(configFile);
		} else {
			regBuilder.configure(INTERNAL_HIBERNATE_CONFIG_FILE);
		}

		regBuilder.applySettings(_conf.getProperties());

		_factory = _conf.buildSessionFactory(regBuilder.build());

		_busDao = new BusDao();
		_operationDao = new OperationDao();
		_routeRideDao = new RouteRideDao();
		_pathStationDao = new PathStationDao();
		_suspendedDao = new SuspendedDao();
		_routeDao = new RouteDao();
		_stationDao = new StationDao();
		_pathDao = new PathDao();
	}

	public void closeSessionFactory(){
	    _factory.close();
    }

	public List<BusEntity> getAllBusses() {
		Session activeSession = _factory.openSession();
		List<BusEntity> buses = _busDao.getAllBuses(activeSession);
		activeSession.close();
		return buses;
	}

	public BusEntity getBusById(int busId) {
		Session activeSession = _factory.openSession();
		BusEntity bus = _busDao.getBusById(activeSession, busId);
		activeSession.close();
		return bus;
	}

	public List<OperationEntity> getAllOperations() {
		Session activeSession = _factory.openSession();
		List<OperationEntity> operations = _operationDao.getAllOperations(activeSession);
		activeSession.close();
		return operations;
	}

	public List<IOperation> getOperationsByDate(Date date) {
		Session activeSession = _factory.openSession();
		List<IOperation> operations =
				_operationDao.getOperationsByDate(activeSession, date)
					.stream()
						.map(oper -> new Operation(oper))
						.collect(Collectors.toList());
		activeSession.close();
		return operations;
	}

	public List<IOperation> getOperationsByMonth(int month) {
		Session activeSession = _factory.openSession();
		List<IOperation> operations =
				_operationDao.getOperationsByMonth(activeSession, month)
					.stream()
					.map(oper -> new Operation(oper))
					.collect(Collectors.toList());
		activeSession.close();
		return operations;
	}

	public OperationEntity getOperationById(int id) {
		Session activeSession = _factory.openSession();
		OperationEntity operation = _operationDao.getOperationById(activeSession, id);
		activeSession.close();
		return operation;
	}

    public RouteEntity getRouteById(int id) {
        Session activeSession = _factory.openSession();
        RouteEntity route = _routeDao.getRouteById(activeSession, id);
        activeSession.close();
        return route;
    }
	
	public List<Operation> getOperationsByBus(BusEntity selectedBus) {
		Session activeSession = _factory.openSession();
		List<Operation> operations = 
				_operationDao.getOperationByBus(activeSession, selectedBus)
					.stream()
					.map(Operation::new)
					.collect(Collectors.toList());
		activeSession.close();
		return operations;
	}

	public List<PathStationEntity> getPathStationsByPath(PathEntity path) {
		Session activeSession = _factory.openSession();
		List<PathStationEntity> stations = _pathStationDao.getAllByPathId(activeSession, path);
		activeSession.close();
		return stations;
	}

	public void saveOperations(List<IOperation> operations) {
		Session activeSession = null;
		Transaction tx = null;

		try {
			activeSession = _factory.openSession();
			tx = activeSession.beginTransaction();
			for(IOperation oper : operations) {
				_operationDao.persistOperation(activeSession, oper.getCapsuledEntity());
			}
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (activeSession != null) {
				activeSession.close();
			}
		}
	}

	public List<SuspendedEntity> getSuspendedEntriesByBusAndDate(BusEntity bus, LocalDate date) {
		Session activeSession = _factory.openSession();
		List<SuspendedEntity> suspendeds = _suspendedDao.getEntriesByBusAndDate(activeSession, bus, Date.valueOf(date));
		activeSession.close();
		return suspendeds;
	}

	public List<RouteEntity> getAllRoutes() {
		Session activeSession = _factory.openSession();
		List<RouteEntity> routes = _routeDao.getAllRoutes(activeSession);
		activeSession.close();
		return routes;
	}

	public String saveNewRoute(int routeNumber, Date startDate, Date endDate) {
		Session activeSession = null;
		Transaction tx = null;
		boolean noConflict = false;
		try {
			activeSession = _factory.openSession();
			tx = activeSession.beginTransaction();
			noConflict = true;
			List<RouteEntity> allRoutes = _facade.getAllRoutes();
			for (RouteEntity r : allRoutes) {
				if (r.getRouteNumber() == routeNumber) {
					if (startDate.after(r.getValidFrom()) && startDate.before(r.getValidTo())) {
						noConflict = false;
					}
					if (endDate.after(r.getValidFrom()) && endDate.before(r.getValidTo())) {
						noConflict = false;
					}
					if (endDate.equals(r.getValidFrom()) || endDate.equals(r.getValidTo())
							|| startDate.equals(r.getValidFrom()) || startDate.equals(r.getValidTo())) {
						noConflict = false;
					}
				}
			}
			if (noConflict) {
				RouteEntity route = new RouteEntity();
				route.setRouteNumber(routeNumber);
				route.setValidFrom(startDate);
				route.setValidTo(endDate);
				_routeDao.persistRoute(activeSession, route);
				PathEntity pathEntity = new PathEntity();
				pathEntity.setRetour(false);
				pathEntity.setRoute(route);
				_pathDao.persistPath(activeSession, pathEntity);
				tx.commit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (activeSession != null) {
				activeSession.close();
			}
			if (noConflict) {
				return "Saved!";
			} else {
				return "Conflict!";
			}
		}
	}

	public List<RouteEntity> getValidRoutesByMonth(int month) {
		Session activeSession = _factory.openSession();
		List<RouteEntity> routes = _routeDao.getValidRoutesByMonth(activeSession, month);
		activeSession.close();
		return routes;
	}

	public List<RouteEntity> getValidRoutesByDay(Date date) {
		Session activeSession = _factory.openSession();
		List<RouteEntity> routes = _routeDao.getValidRoutesByDay(activeSession, date);
		activeSession.close();
		return routes;
	}

	public List<RouteRide> getRouteRidesByRoute(IRoute route){
        Session activeSession = _factory.openSession();
        List<RouteRide> routeRides =
                _routeRideDao.getAllRouteRidesByRoute(activeSession, route.getCapsulatedRouteEntity())
                        .stream()
                        .map(RouteRide::new)
                        .collect(Collectors.toList());
        activeSession.close();
        return routeRides;
    }

    public RouteRide getRouteRideById(int id){
        Session activeSession = _factory.openSession();
        RouteRide routeRide = new RouteRide(_routeRideDao.getRouteRideById(activeSession, id));
        activeSession.close();
        return routeRide;
    }

	public String editRoute(RouteEntity route, Date startDate, Date endDate) {
		Session activeSession = null;
		Transaction tx = null;
		try {
			activeSession = _factory.openSession();
			tx = activeSession.beginTransaction();
			List<RouteEntity> routeEntities = _routeDao.getAllRoutes(activeSession);

			for (RouteEntity r : routeEntities) {
				if ((route.getRouteNumber() == r.getRouteNumber() && route.getRouteId() != r.getRouteId())) {
					if ((startDate.after(r.getValidFrom()) && startDate.before(r.getValidTo()))
							|| startDate.equals(r.getValidFrom()) || startDate.equals(r.getValidTo())) {
						activeSession.close();
						return "Start Date Conflict!";
					}
					if ((endDate.after(r.getValidFrom()) && endDate.before(r.getValidTo()))
							|| endDate.equals(r.getValidFrom()) || endDate.equals(r.getValidTo())) {
						activeSession.close();
						return "End Date Conflict!";
					}
				}
			}
			
			route.setValidFrom(startDate);
			route.setValidTo(endDate);
			activeSession.clear();
			_routeDao.updateRoute(activeSession, route);
			tx.commit();
			return "Saved";

		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (activeSession != null) {
				activeSession.close();
			}
		}
		return "Not Saved";
	}

	public void persistRoute(RouteEntity route) {
		Session activeSession = null;
		Transaction tx = null;
		try {
			activeSession = _factory.openSession();
			tx = activeSession.beginTransaction();
			_routeDao.persistRoute(activeSession, route);
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (activeSession != null) {
				activeSession.close();
			}
		}
	}

	public List<PathEntity> getAllPaths() {
		Session activeSession = _factory.openSession();
		List<PathEntity> paths = _pathDao.getAllPaths(activeSession);
		activeSession.close();
		return paths;
	}

	public void savePath(PathEntity path) {
		Session activeSession = null;
		Transaction tx = null;
		try {
			activeSession = _factory.openSession();
			tx = activeSession.beginTransaction();
			_pathDao.persistPath(activeSession, path);
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (activeSession != null) {
				activeSession.close();
			}
		}
	}

	public List<StationEntity> getAllStations() {
		Session activeSession = _factory.openSession();
		List<StationEntity> stations = _stationDao.getAllStations(activeSession);
		activeSession.close();
		return stations;
	}

	public void addPathStation(PathStationEntity pathStation) {
		Session activeSession = null;
		Transaction tx = null;

		try {
			activeSession = _factory.openSession();
			tx = activeSession.beginTransaction();
			_pathStationDao.addPathStation(activeSession, pathStation);
			// activeSession.save(pathStation);
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (activeSession != null) {
				activeSession.close();
			}
		}
	}

	public void changePreviousDistance(PathStationEntity pathStation, int newDistanceFromPrevious) {
		Session activeSession = null;
		Transaction tx = null;
		try {
			activeSession = _factory.openSession();
			tx = activeSession.beginTransaction();
			_pathStationDao.changePreviousDistance(activeSession, pathStation, newDistanceFromPrevious);
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (activeSession != null) {
				activeSession.close();
			}
		}
	}

	public void changePreviousTime(PathStationEntity pathStation, int newTimeFromPrevious) {
		Session activeSession = null;
		Transaction tx = null;
		try {
			activeSession = _factory.openSession();
			tx = activeSession.beginTransaction();
			_pathStationDao.changePreviousTime(activeSession, pathStation, newTimeFromPrevious);
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (activeSession != null) {
				activeSession.close();
			}
		}
	}

	public void changePathDescription(PathEntity path, String pathDescription) {
		Session activeSession = null;
		Transaction tx = null;
		try {
			activeSession = _factory.openSession();
			tx = activeSession.beginTransaction();
			_pathDao.changePathDescription(activeSession, path, pathDescription);
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (activeSession != null) {
				activeSession.close();
			}
		}
	}

	public void changePositionOnPath(PathStationEntity pathStation, int positionOnPath) {
		Session activeSession = null;
		Transaction tx = null;
		try {
			activeSession = _factory.openSession();
			tx = activeSession.beginTransaction();
			_pathStationDao.changePositionOnPath(activeSession, pathStation, positionOnPath);
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (activeSession != null) {
				activeSession.close();
			}
		}
	}

	public void deletePathStation(PathStationEntity pathStation) {
		Session activeSession = null;
		Transaction tx = null;
		try {
			activeSession = _factory.openSession();
			tx = activeSession.beginTransaction();
			_pathStationDao.deletePathStations(activeSession, pathStation);
			activeSession.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (activeSession != null) {
				activeSession.close();
			}
		}
	}

    public void saveRouteRides(Set<RouteRide> routeRides) {
        Session activeSession = null;
        Transaction tx = null;

        try {
            activeSession = _factory.openSession();
            tx = activeSession.beginTransaction();
            for(RouteRide routeRide : routeRides) {
                _routeRideDao.persistRouteRide(activeSession, routeRide.getCapsulatedRouteRideEntity());
            }
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (activeSession != null) {
                activeSession.close();
            }
        }
    }
}
