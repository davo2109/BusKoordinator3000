package at.fhv.itb.ss19.busmaster.application;

import at.fhv.itb.ss19.busmaster.persistence.DbFacade;
import at.fhv.itb.ss19.busmaster.persistence.entities.*;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;

public class CreateRoute {

    private DbFacade _facade;
    
    public CreateRoute() {
        _facade = DbFacade.getInstance();
    }

    public List<RouteEntity> getAllRoutes() {
        return _facade.getAllRoutes();
    }

    public List<StationEntity> getAllStations(){
    	return _facade.getAllStations();
    }
    
    public List<PathStationEntity> getPathStationsByPath(PathEntity path)
    {
    	return _facade.getPathStationsByPath(path);
    }
    
    
    public void addPathStation(PathStationEntity pathStation)
    {
    	 _facade.addPathStation(pathStation);
    }
    
    public void savePath(PathEntity path)
    {
    	_facade.savePath(path);
    }
    
    public void changePreviousDistance(PathStationEntity pathStation, int newPreviousDistance)
    {
    	_facade.changePreviousDistance(pathStation,newPreviousDistance);
    }
    
    public void changePreviousTime(PathStationEntity pathStation, int newPreviousTime)
    {
    	_facade.changePreviousTime(pathStation, newPreviousTime);
    }
    
    public void changePathDescription(PathEntity path, String pathDescription)
    {
    	_facade.changePathDescription(path, pathDescription);
    }
    
    public void changePositionOnPath(PathStationEntity pathStation, int positionOnPath)
    {
    	_facade.changePositionOnPath(pathStation, positionOnPath);
    }

    public String saveNewRoute(int routeNumber, Date startDate, Date endDate) {
        return _facade.saveNewRoute(routeNumber, startDate, endDate);
    }

    public String editRoute(RouteEntity route, Date startDate, Date endDate) {
        return _facade.editRoute(route, startDate, endDate);
    }
    
    public void deletePathStations(PathStationEntity pathStation)
    {
    	_facade.deletePathStation(pathStation);
    }
}
