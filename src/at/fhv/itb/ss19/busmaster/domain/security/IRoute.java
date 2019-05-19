package at.fhv.itb.ss19.busmaster.domain.security;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import at.fhv.itb.ss19.busmaster.domain.DayType;
import at.fhv.itb.ss19.busmaster.domain.Path;

public interface IRoute {
    public int getRouteId();

    public int getRouteNumber();

    public LocalDate getValidFrom();

    public LocalDate getValidTo();

    public String getVariation();

    public List<IRouteRide> getRouteRides();

    public List<? extends IRouteRide> getOpenRouteRides(List<? extends IOperation> oper, DayType daytype);

	public List<Path> getPaths();

    public int getNumberOfRidesPerDayType(DayType dayType);

}
