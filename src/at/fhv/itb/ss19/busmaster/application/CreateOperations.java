package at.fhv.itb.ss19.busmaster.application;

import at.fhv.itb.ss19.busmaster.domain.Operation;
import at.fhv.itb.ss19.busmaster.domain.Route;
import at.fhv.itb.ss19.busmaster.domain.RouteRide;
import at.fhv.itb.ss19.busmaster.domain.security.IOperation;
import at.fhv.itb.ss19.busmaster.domain.security.IRoute;
import at.fhv.itb.ss19.busmaster.persistence.DbFacade;
import at.fhv.itb.ss19.busmaster.persistence.entities.OperationEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteRideEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class CreateOperations {
    private DbFacade _facade;
    private List<IRoute> _routes;
    private List<IOperation> _operations;

    public CreateOperations() {
        _facade = DbFacade.getInstance();
        _operations = new LinkedList<>();
    }

    public List<IRoute> getValidRoutesByMonth(int month) {
        List<RouteEntity> routeEntities = _facade.getValidRoutesByMonth(month);
        _routes = routeEntities.stream().map(Route::new).collect(Collectors.toList());
        return _routes;
    }

    public List<IRoute> getValidRoutesPerDay(Date date) {
        List<RouteEntity> routeEntities = _facade.getValidRoutesByDay(date);
        _routes = routeEntities.stream().map(Route::new).collect(Collectors.toList());
        return _routes;
    }

    //RouteRides auswählen. Kann auch von einem Pfad Hin und Retour gehört aber in Controller. In Liste die jeweilige Pause nach jeder Fahrt in Minuten auswählen. Standart ist 0.
    //Endzeit festlegen. Standart ist der ganze Tag. Datum festlegen.
    //Ausgewählte RouteRides werden überprüft und alle RouteRides mit der selben Route werden in Liste geschrieben, EndZeit und Anfagnszeit wird überprüft.
    //Liste wird nach Zeit geordnet.
    //Liste wird durchlaufen und mit aktueller Zeit verglichen (Pause wird oben drauf berechnet) und ob die nächste Linie mit der Auswahl übereinstimmt.
    //Zum Schluss wird Liste an Operation Object übegeben und wartet auf Speicherbefehl.


    public void createOperationSingleDay(List<RouteRide> routeRideList, List<Integer> breakInMinutes, LocalTime endTime, LocalDate date){
        Set<IRoute> routeSet = new HashSet<>();
        LinkedList<RouteRide> routeRides = new LinkedList<>();
        LocalTime lastTime = routeRideList.get(routeRideList.size() - 1).getEndingTime();
        Integer dayType = routeRideList.get(0).get_dayType();
        int initialSize = routeRideList.size();


        for(RouteRide routeRide : routeRideList){
            routeSet.add(routeRide.getRoute());
        }

        for(IRoute route : routeSet){
            List<RouteRide> routeRide = _facade.getRouteRidesByRoute(route);
            for(RouteRide routeRidesFilter : routeRide){
                if((routeRidesFilter.get_dayType().equals(dayType)) && (routeRidesFilter.getStartingTime().isBefore(endTime)) && (routeRidesFilter.getStartingTime().compareTo(lastTime) >= 0)){
                    routeRides.add(routeRidesFilter);
                }
            }
        }
        routeRides.sort(Comparator.comparing(RouteRide::getStartingTime));

        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setDate(java.sql.Date.valueOf(date));
        Operation op = new Operation(operationEntity);
        _operations.add(op);
        int counter = 0;
        for(RouteRide routeRide : routeRides){
            if((routeRide.get_path().getPathId() == routeRideList.get(counter).get_path().getPathId()) && (routeRide.getStartingTime().compareTo(lastTime) >= 0)){
                routeRideList.add(routeRide);
                lastTime = routeRide.getEndingTime().plusMinutes(breakInMinutes.get(counter));
                counter = (counter + 1) % initialSize;
            }
        }
        op.set_routeRideList(routeRideList);
        for (RouteRide routeRide : routeRideList){
            System.out.println(routeRide);
        }
    }

    public void saveOperations(){
        _facade.saveOperations(_operations);
    }



}
