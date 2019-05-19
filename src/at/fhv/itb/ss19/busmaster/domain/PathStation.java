package at.fhv.itb.ss19.busmaster.domain;

import at.fhv.itb.ss19.busmaster.persistence.entities.PathStationEntity;

public class PathStation {
	private PathStationEntity _pathStation;
	private int positionOnPath;
    private int distanceFromPrevious;
    private int timeFromPrevious;
    private Station station;
    private Path path;
    
    public PathStation(PathStationEntity pathStation) {
    	_pathStation = pathStation;
    }
    
    public PathStationEntity getCapsuledEntity() {
		return _pathStation;
	}
   
   public int getPositionOnPath() {
	   if(positionOnPath == 0) {
		   positionOnPath = _pathStation.getPositionOnPath();
	   }
       return positionOnPath;
   }

   public void setPositionOnPath(int positionOnPath) {
       this.positionOnPath = positionOnPath;
   }

   public int getDistanceFromPrevious() {
	   if(distanceFromPrevious == 0) {
		   distanceFromPrevious = _pathStation.getDistanceFromPrevious();
	   }
	   
       return distanceFromPrevious;
   }

   public void setDistanceFromPrevious(int distanceFromPrevious) {
       this.distanceFromPrevious = distanceFromPrevious;
   }

   public int getTimeFromPrevious() {
	   if(timeFromPrevious == 0) {
		   timeFromPrevious = _pathStation.getTimeFromPrevious();
	   }
       return timeFromPrevious;
   }

   public void setTimeFromPrevious(int timeFromPrevious) {
       this.timeFromPrevious = timeFromPrevious;
   }

   public Station getStation() {
	   if(station == null) {
		   station = new Station(_pathStation.getStation());
	   }
       return station;
   }

   public void setStation(Station station) {
       this.station = station;
   }

   public Path getPath() {
	   if(path == null) {
		   path = new Path(_pathStation.getPath());
	   }
       return path;
   }

   public void setPath(Path path) {
       this.path = path;
   }
}
