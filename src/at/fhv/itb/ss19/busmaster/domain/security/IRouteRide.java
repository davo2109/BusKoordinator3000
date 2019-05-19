package at.fhv.itb.ss19.busmaster.domain.security;

import at.fhv.itb.ss19.busmaster.domain.Operation;
import at.fhv.itb.ss19.busmaster.domain.StartTime;

import java.time.LocalTime;
import java.util.List;

public interface IRouteRide {
    public int getRouteRideId();

    public List<Operation> getOperations();

    public IRoute getRoute();

    public StartTime getStartTime();
    
    public LocalTime getStartingTime();

    public LocalTime getEndingTime();

    public String getStartStationName();

    public String getEndStationName();
}
