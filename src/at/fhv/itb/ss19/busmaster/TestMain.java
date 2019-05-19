package at.fhv.itb.ss19.busmaster;

import at.fhv.itb.ss19.busmaster.application.CreateOperations;
import at.fhv.itb.ss19.busmaster.application.CreateRouteRides;
import at.fhv.itb.ss19.busmaster.domain.Path;
import at.fhv.itb.ss19.busmaster.domain.RouteRide;
import at.fhv.itb.ss19.busmaster.persistence.DbFacade;
import at.fhv.itb.ss19.busmaster.persistence.entities.PathEntity;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class TestMain {
    public static void main(String[] args) {
        DbFacade fc = DbFacade.getInstance();

        /*List<PathEntity> pathList = fc.getAllPaths();
        List<Path> paths = new LinkedList<>();
        for (PathEntity path : pathList) {
            if (path.getPathId() == 1 || path.getPathId() == 250) {
                paths.add(new Path(path));
            }
        }


        CreateRouteRides createRouteRides = new CreateRouteRides();

        createRouteRides.createRidesWithTakt(paths.get(0), LocalTime.of(2, 0), LocalTime.of(6, 0), 20, 0, 72);
        createRouteRides.createRidesWithTakt(paths.get(1), LocalTime.of(2, 30), LocalTime.of(6, 30), 20, 0, 72);
        createRouteRides.saveRouteRides();
        fc.closeSessionFactory();*/

        List<RouteRide> routeRides = new ArrayList<>();
        routeRides.add(fc.getRouteRideById(357));
        routeRides.add(fc.getRouteRideById(363));

        List<Integer> breaks = new ArrayList<>();
        breaks.add(0);
        breaks.add(0);

        LocalTime localTime = LocalTime.of(19, 0);
        LocalDate localDate = LocalDate.of(2019, 05, 25);

        CreateOperations co = new CreateOperations();
        co.createOperationSingleDay(routeRides, breaks, localTime, localDate);

        //co.saveOperations();
        fc.closeSessionFactory();


    }
}
