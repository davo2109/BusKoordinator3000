package at.fhv.itb.ss19.busmaster.domain;

import at.fhv.itb.ss19.busmaster.domain.security.IRoute;
import at.fhv.itb.ss19.busmaster.domain.security.IRouteRide;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteRideEntity;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class RouteRide implements IRouteRide {
    private RouteRideEntity _routeRideEntity;
    private Path _path;
    private StartTime _startTime;
    private Station _startStation;
    private Station _endStation;
    private LocalTime _startingTime;
    private LocalTime _endingTime;
    private Integer _dayType;
    private int _capacity;

    public RouteRide(RouteRideEntity routeRideEntity) {
        _routeRideEntity = routeRideEntity;
    }


    public RouteRideEntity getCapsulatedRouteRideEntity(){
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

    public Path get_path() {
        return _path;
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

    public Integer get_dayType() {
        if(_dayType == null) {
            setNeededAttributes();
        }
        return _dayType;
    }

    public int get_capacity() {
        return _capacity;
    }

    private void setNeededAttributes() {
    	for(PathStation station : this.getStartTime().getPath().getPathStations()) {
			if(station.getPositionOnPath() == 1) {
				_startStation = station.getStation();
			}
			if(station.getPositionOnPath() == _routeRideEntity.getStartTime().getPath().getPathStations().size()) {
				_endStation = station.getStation();
			}
		}
    	_path = new Path(_routeRideEntity.getStartTime().getPath());
    	
    	_startingTime = _routeRideEntity.getStartTime().getStartTime().toLocalTime();
    	_endingTime = _startingTime.plusMinutes(_path.get_pathTime());
    	_dayType = _startTime.getStartTimeType();
    	_capacity = _startTime.getRequiredCapacity();

    }

    @Override
    public String toString() {
        return "RouteRide{" +
                " _path=" + _path +
                ", _startingTime=" + _startingTime +
                ", _endingTime=" + _endingTime +
                ", _dayType=" + _dayType +
                ", _capacity=" + _capacity +
                '}';
    }
}
