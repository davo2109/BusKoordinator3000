package at.fhv.itb.ss19.busmaster.domain;

import java.util.Set;
import java.util.stream.Collectors;

import at.fhv.itb.ss19.busmaster.persistence.entities.StationEntity;

public class Station {
	private StationEntity _station;
    
    public Station(StationEntity station) {
    	_station = station;
    }

    public int getStationId() {
        return _station.getStationId();
    }

    public void setStationId(int stationId) {
        _station.setStationId(stationId);
    }

    public String getStationName() {
        return _station.getStationName();
    }

    public void setStationName(String stationName) {
        _station.setStationName(stationName);
    }

    public String getShortName() {
        return _station.getShortName();
    }

    public void setShortName(String shortName) {
        _station.setShortName(shortName);
    }

    public Set<PathStation> getPathStations() {
        return _station.getPathStations().stream().map(PathStation::new).collect(Collectors.toSet());
    }
}
