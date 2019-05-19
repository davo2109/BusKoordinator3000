package at.fhv.itb.ss19.busmaster.domain;

import at.fhv.itb.ss19.busmaster.domain.security.IRoute;
import at.fhv.itb.ss19.busmaster.domain.security.IRouteRide;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteEntity;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Route implements IRoute {
    private RouteEntity _routeEntity;

    public Route(RouteEntity routeEntity) {
        _routeEntity = routeEntity;
    }

    public RouteEntity get_routeEntity(){
        return _routeEntity;
    }

    public RouteEntity getCapsulatedRouteEntity(){
        return _routeEntity;
    }

    @Override
    public int getRouteId(){return _routeEntity.getRouteId();}

    @Override
    public int getRouteNumber(){return _routeEntity.getRouteNumber();}

    @Override
    public Date getValidFrom() {return _routeEntity.getValidFrom();}

    @Override
    public Date getValidTo() {return _routeEntity.getValidTo();}

    @Override
    public String getVariation() {return _routeEntity.getVariation();}

    @Override
    public List<IRouteRide> getRouteRides() {
        return _routeEntity.getRouteRides().stream().map(RouteRide::new).collect(Collectors.toList());
    }

    @Override
    public int getOpenRidesNumber() {
        //return _routeEntity.getRouteRides().stream().filter(i -> i.getOperation() == null).toArray().length;
    	return 0;
    }

    @Override
    public List<IRouteRide> getFreeRouteRides() {
        //return _routeEntity.getRouteRides().stream().filter(i -> i.getOperation() == null).map(RouteRide::new).collect(Collectors.toList());
    	return null;
    }

}
