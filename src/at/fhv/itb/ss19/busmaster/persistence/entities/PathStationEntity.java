package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "path_station", schema = "public", catalog = "busdb")
public class PathStationEntity implements Serializable, Comparable {
	private static final long serialVersionUID = -3909555730471607768L;
	private int positionOnPath;
    private int distanceFromPrevious;
    private int timeFromPrevious;
    private StationEntity station;
    private PathEntity path;

     public PathStationEntity() {
    	 //Should not be null
    	 this.setDistanceFromPrevious(0);
    	 this.setTimeFromPrevious(0);
	}
    
    @Basic
    @Column(name = "position_on_path")
    public int getPositionOnPath() {
        return positionOnPath;
    }

    public void setPositionOnPath(int positionOnPath) {
        this.positionOnPath = positionOnPath;
    }

    @Basic
    @Column(name = "distance_from_previous")
    public int getDistanceFromPrevious() {
        return distanceFromPrevious;
    }

    public void setDistanceFromPrevious(int distanceFromPrevious) {
        this.distanceFromPrevious = distanceFromPrevious;
    }

    @Basic
    @Column(name = "time_from_previous")
    public int getTimeFromPrevious() {
        return timeFromPrevious;
    }

    public void setTimeFromPrevious(int timeFromPrevious) {
        this.timeFromPrevious = timeFromPrevious;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathStationEntity that = (PathStationEntity) o;
        return positionOnPath == that.positionOnPath &&
                distanceFromPrevious == that.distanceFromPrevious &&
                timeFromPrevious == that.timeFromPrevious;
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionOnPath, distanceFromPrevious, timeFromPrevious);
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "station_id", nullable = false)
    public StationEntity getStation() {
        return station;
    }

    public void setStation(StationEntity station) {
        this.station = station;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "path_id", referencedColumnName = "path_id", nullable = false)
    public PathEntity getPath() {
        return path;
    }

    public void setPath(PathEntity path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "PathStationEntity{" +
                "positionOnPath=" + positionOnPath +
                ", distanceFromPrevious=" + distanceFromPrevious +
                ", timeFromPrevious=" + timeFromPrevious +
                ", station=" + station +
                ", path=" + path +
                '}';
    }

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		PathStationEntity otherPathStation = (PathStationEntity)o;
		return this.positionOnPath - otherPathStation.positionOnPath;
	}
}
