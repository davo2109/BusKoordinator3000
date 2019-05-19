package at.fhv.itb.ss19.busmaster.domain;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

import at.fhv.itb.ss19.busmaster.persistence.entities.StartTimeEntity;

public class StartTime {
	private StartTimeEntity _startTime;
	
    private int startTimeId;
    private int requiredCapacity;
    private LocalTime startTime;
    private Integer startTimeType;
    private Set<RouteRide> routeRides;
    private Path path;
    
    public StartTime (StartTimeEntity startTime) {
    	_startTime = startTime;
    }

    public int getStartTimeId() {
    	if(startTimeId == 0) {
    		startTimeId = _startTime.getStartTimeId();
    	}
    	
        return startTimeId;
    }

    public void setStartTimeId(int startTimeId) {
        this.startTimeId = startTimeId;
    }
    
    public int getRequiredCapacity() {
    	if(requiredCapacity == 0) {
    		requiredCapacity = _startTime.getRequiredCapacity();
    	}
        return requiredCapacity;
    }

    public void setRequiredCapacity(int requiredCapacity) {
        this.requiredCapacity = requiredCapacity;
    }

    public LocalTime getStartTime() {
    	if(startTime == null) {
    		startTime = _startTime.getStartTime().toLocalTime();
    	}
    	
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Integer getStartTimeType() {
        return _startTime.getStartTimeType();
    }

    public void setStartTimeType(Integer startTimeType) {
        this.startTimeType = startTimeType;
    }
    
    public Set<RouteRide> getRouteRides() {
    	if(routeRides == null) {
    		routeRides = _startTime.getRouteRides().stream().map(RouteRide::new).collect(Collectors.toSet());
    	}
        return routeRides;
    }

    public void setRouteRides(Set<RouteRide> routeRides) {
        this.routeRides = routeRides;
    }

    public Path getPath() {
    	if(path == null) {
    		path = new Path(_startTime.getPath());
    	}
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
