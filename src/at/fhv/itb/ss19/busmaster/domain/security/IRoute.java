package at.fhv.itb.ss19.busmaster.domain.security;

import at.fhv.itb.ss19.busmaster.persistence.entities.RouteEntity;

import java.sql.Date;
import java.util.List;

public interface IRoute {
    public int getRouteId();

    public int getRouteNumber();

    public Date getValidFrom();

    public Date getValidTo();

    public String getVariation();

    public List<IRouteRide> getRouteRides();

    public int getOpenRidesNumber();

    public List<IRouteRide> getFreeRouteRides();

    public RouteEntity getCapsulatedRouteEntity();

}
