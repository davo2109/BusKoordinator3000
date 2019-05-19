package at.fhv.itb.ss19.busmaster.domain;

import at.fhv.itb.ss19.busmaster.domain.security.IOperation;
import at.fhv.itb.ss19.busmaster.domain.security.IRoute;
import at.fhv.itb.ss19.busmaster.domain.security.IRouteRide;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteEntity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Route implements IRoute {
	private RouteEntity _routeEntity;
	private int _openRides;

	public Route(RouteEntity routeEntity) {
		_routeEntity = routeEntity;
	}

	@Override
	public int getRouteId() {
		return _routeEntity.getRouteId();
	}

	@Override
	public int getRouteNumber() {
		return _routeEntity.getRouteNumber();
	}

	@Override
	public LocalDate getValidFrom() {
		return _routeEntity.getValidFrom().toLocalDate();
	}

	@Override
	public LocalDate getValidTo() {
		return _routeEntity.getValidTo().toLocalDate();
	}

	@Override
	public String getVariation() {
		return _routeEntity.getVariation();
	}

	@Override
	public List<IRouteRide> getRouteRides() {
		return _routeEntity.getRouteRides().stream().map(RouteRide::new).collect(Collectors.toList());
	}

    @Override
    public List<Path> getPaths() {
        return _routeEntity.getPaths().stream().map(Path::new).collect(Collectors.toList());
    }

	@Override
	public List<? extends IRouteRide> getOpenRouteRides(List<? extends IOperation> operations, DayType daytype) {
		List<IRouteRide> rides = new LinkedList<>(getRouteRides());
        for (IRouteRide ride : getRouteRides()) {
			if (ride.getStartTime().getDaytype() == daytype) {
				for (IOperation operation : operations) {
					if (operation.getRouteRides().contains(ride)) {
                        rides.remove(ride);
					}
				}
			}
			else {
                rides.remove(ride);
			}
		}
        _openRides = rides.size();
        return rides;
	}

	@Override
	public int getNumberOfRidesPerDayType(DayType dayType) {
		int rideCount = 0;
		for (IRouteRide ride : this.getRouteRides()) {
			if (ride.getStartTime().getDaytype() == dayType) {
				rideCount++;
			}
		}
		return rideCount;
	}
}
