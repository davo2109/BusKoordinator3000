package at.fhv.itb.ss19.busmaster.domain;

import at.fhv.itb.ss19.busmaster.persistence.entities.PathStationEntity;

public class PathStation {
	private PathStationEntity _pathStation;
    private Station station;
    private Path path;
    
    public PathStation(PathStationEntity pathStation) {
    	_pathStation = pathStation;
    }
    
    public PathStationEntity getCapsuledEntity() {
		return _pathStation;
	}
   
   public int getPositionOnPath() {
       return _pathStation.getPositionOnPath();
   }

   public void setPositionOnPath(int positionOnPath) {
       _pathStation.setPositionOnPath(positionOnPath);
   }

   public int getDistanceFromPrevious() {   
       return _pathStation.getDistanceFromPrevious();
   }

   public void setDistanceFromPrevious(int distanceFromPrevious) {
       _pathStation.setDistanceFromPrevious(distanceFromPrevious);
   }

   public int getTimeFromPrevious() {
       return _pathStation.getTimeFromPrevious();
   }

   public void setTimeFromPrevious(int timeFromPrevious) {
	   _pathStation.setTimeFromPrevious(timeFromPrevious);
   }

   public Station getStation() {
       return new Station(_pathStation.getStation());
   }

   public void setStation(Station station) {
       this.station = station;
   }

   public Path getPath() {
       return new Path(_pathStation.getPath());
   }

   public void setPath(Path path) {
       this.path = path;
   }
}
