package at.fhv.itb.ss19.busmaster.domain;

import at.fhv.itb.ss19.busmaster.domain.security.IRoute;
import at.fhv.itb.ss19.busmaster.domain.security.IRouteRide;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteRideEntity;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class RouteRide implements IRouteRide {
    private RouteRideEntity _routeRideEntity;
    
    private StartTime _startTime;
    private Station _startStation;
    private Station _endStation;
    private LocalTime _startingTime;
    private LocalTime _endingTime;

    public RouteRide(RouteRideEntity routeRideEntity) {
        _routeRideEntity = routeRideEntity;
    }
    
    public RouteRideEntity getCapsuledEntity() {
    	return _routeRideEntity;
    }

    @Override
    public int getRouteRideId() {
        return _routeRideEntity.getRouteRideId();
    }

    @Override
    public List<Operation> getOperations() {
        if (_routeRideEntity.getOperations() == null) {
            return null;
        }
        return _routeRideEntity.getOperations().stream().map(Operation::new).collect(Collectors.toList());
    }

    @Override
    public IRoute getRoute() {
        return new Route(_routeRideEntity.getRoute());
    }

    @Override
    public StartTime getStartTime() {
    	if(_startTime == null) {
    		_startTime = new StartTime(_routeRideEntity.getStartTime());
    	}
        return _startTime;
    }
    
    public LocalTime getStartingTime() {
    	if (_startingTime == null) {
    		setNeededAttributes();
    	}
    	
    	return _startingTime;
    }

    public LocalTime getEndingTime() {
    	if (_endingTime == null) {
    		setNeededAttributes();
    	}
    	
    	return _endingTime;
    }
    
    public String getStartStationName() {
    	if(_startStation == null) {
    		setNeededAttributes();
    	}
    	
    	return _startStation == null ? "" : _startStation.getStationName();
    }
    
    public String getEndStationName() {
    	if(_endStation == null) {
    		setNeededAttributes();
    	}
    	
    	return _endStation == null ? "" : _endStation.getStationName();
    }
    
    private void setNeededAttributes() {
    	for(PathStation station : getStartTime().getPath().getPathStations()) {
			if(station.getPositionOnPath() == 1) {
				_startStation = station.getStation();
			}
			if(station.getPositionOnPath() == _routeRideEntity.getStartTime().getPath().getPathStations().size()) {
				_endStation = station.getStation();
			}
		}
    	
    	// wenn eine Fahrt hin und zurück geht, würde das unten eventuell funktionieren
//    	for (Path path : getRoute().getPaths()) {
//    	    if(!path.isRetour()){
//                for(PathStation station : getStartTime().getPath().getPathStations()) {
//                    if(station.getPositionOnPath() == 1) {
//                        _startStation = station.getStation();
//                    }
//                    if (station.getPositionOnPath() == path.getPathStations().size()) {
//                        _endStation = station.getStation();
//                    }
//                    duration += station.getTimeFromPrevious();
//                }
//            } else {
//                List<PathStation> pathStations = path.getPathStations();
//                for (PathStation station : pathStations) {
//                    if (station.getPositionOnPath() == pathStations.size()) {
//                        _endStation = station.getStation();
//                    }
//                    duration += station.getTimeFromPrevious();
//                }
//            }
//        }
    	
    	_startingTime = _routeRideEntity.getStartTime().getStartTime().toLocalTime();
    	_endingTime = _startingTime.plusMinutes(getStartTime().getPath().getDuration());
    }

    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof RouteRide) {
    		RouteRide ride = (RouteRide)obj;
    		return _routeRideEntity.equals(ride.getCapsuledEntity());
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
    	return _routeRideEntity.hashCode();
    }
}
