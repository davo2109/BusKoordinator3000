package at.fhv.itb.ss19.busmaster.application;

import at.fhv.itb.ss19.busmaster.domain.Path;
import at.fhv.itb.ss19.busmaster.domain.RouteRide;
import at.fhv.itb.ss19.busmaster.persistence.DbFacade;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteRideEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.StartTimeEntity;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CreateRouteRides {

    private Set<RouteRide> _routeRides;
    private DbFacade _fac;

    public CreateRouteRides(){
        _fac = DbFacade.getInstance();
        _routeRides = new HashSet<>();
    }



    public void createRidesWithTakt(Path path, LocalTime startTime, LocalTime endTime, int takt, Integer dayType, Integer capacity){
        while(startTime.compareTo(endTime) <= 0){
            createSingleRide(path, startTime, dayType, capacity);
            startTime = startTime.plusMinutes(takt);
        }

    }

    public void createSingleRide(Path path, LocalTime startTime, Integer dayType, Integer capacity){
        RouteRideEntity routeRideEntity = new RouteRideEntity();
        routeRideEntity.setRoute(path.getCapsuledEntity().getRoute());
        StartTimeEntity startTimeEntity = new StartTimeEntity();
        startTimeEntity.setPath(path.getCapsuledEntity());
        startTimeEntity.setRequiredCapacity(capacity);
        startTimeEntity.setStartTime(java.sql.Time.valueOf(startTime));
        startTimeEntity.setStartTimeType(dayType);
        routeRideEntity.setStartTime(startTimeEntity);
        RouteRide routeRide = new RouteRide(routeRideEntity);
        _routeRides.add(routeRide);
    }

    public List<RouteRide> getRoudeRidesByPath(Path path){
        List<RouteRide> routeRideList = new LinkedList<>();
        for(RouteRide routeRide : _routeRides){
            if(routeRide.getStartTime().getPath() == path){
                routeRideList.add(routeRide);
            }
        }
        return routeRideList;
    }

    public void saveRouteRides(){
        _fac.saveRouteRides(_routeRides);
    }


}
