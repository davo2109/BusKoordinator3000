package at.fhv.itb.ss19.busmaster.domain;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import at.fhv.itb.ss19.busmaster.domain.security.IOperation;
import at.fhv.itb.ss19.busmaster.persistence.entities.BusEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.OperationEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteRideEntity;

public class Operation implements IOperation, Available{
	private OperationEntity _operationEntity;
	private List<RouteRide> _routeRideList;
	private DayType _dayType;
	private long _rideCheckSum;
	private ChangeStatus _state;
	
	public Operation () {
		_operationEntity = new OperationEntity();
		_dayType = DayType.WORKDAY;
		_routeRideList = new LinkedList<>();
	}

	public Operation(OperationEntity operationEntity) {
		_operationEntity = operationEntity;

		/*List<RouteRideEntity> routeRides = _operationEntity.getRouteRides().stream().collect(Collectors.toList());

		if (!routeRides.isEmpty()) {
			RouteRideEntity ride = routeRides.get(0);
			if (ride != null) {
				_dayType = DayType.values()[ride.getStartTime().getStartTimeType()];
			}
		}

		_state = ChangeStatus.OK;

		for(RouteRideEntity routeRideEntity : routeRides){
		    _routeRideList.add(new RouteRide(routeRideEntity));
        }*/
	}

    public List<RouteRide> get_routeRideList() {
        return _routeRideList;
    }

    public void set_routeRideList(List<RouteRide> routeRideList) {
        _routeRideList = routeRideList;
        Set<RouteRideEntity> routeRideEntitySet = new HashSet<>();
        for(RouteRide routeRide : routeRideList){
            routeRideEntitySet.add(routeRide.getCapsulatedRouteRideEntity());
        }
        _operationEntity.setRouteRides(routeRideEntitySet);
    }

    public OperationEntity getCapsuledEntity() {
		return _operationEntity;
	}

	public int getOperationId() {
		return _operationEntity.getOperationId();
	}

	public DayType getDayType() {
		return _dayType;
	}

	public String getName() {
		return "Tour " + getOperationId();
	}

	public List<RouteRide> getRouteRides() {
		return _operationEntity.getRouteRides().stream().map(RouteRide::new).collect(Collectors.toList());
	}

	public long getRideCheckSum() {
		if (_rideCheckSum == 0) {
			for (RouteRideEntity ride : _operationEntity.getRouteRides()) {
				_rideCheckSum += ride.getStartTime().hashCode();
			}
		}

		return _rideCheckSum;
	}

	public LocalDate getDate() {
		return _operationEntity.getDate().toLocalDate();
	}

	public void setBus(BusEntity bus) {
		_operationEntity.setBus(bus);
	}

	public BusEntity getBus() {
		return _operationEntity.getBus();
	}

	public String getBusLicence() {
		return _operationEntity.getBus() != null ? _operationEntity.getBus().getLicenceNumber() : "";
	}

	public ChangeStatus getStatus() {
		return _state;
	}

	public void setStatus(ChangeStatus state) {
		_state = state;
	}
}
