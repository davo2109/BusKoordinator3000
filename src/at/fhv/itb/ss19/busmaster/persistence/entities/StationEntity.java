package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "station", schema = "public", catalog = "busdb")
public class StationEntity {
    private int stationId;
    private String stationName;
    private String shortName;
    private Set<PathStationEntity> pathStations;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "station_id")
    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "station_name")
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Basic
    @Column(name = "short_name")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationEntity that = (StationEntity) o;
        return stationId == that.stationId &&
                Objects.equals(stationName, that.stationName) &&
                Objects.equals(shortName, that.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationId, stationName, shortName);
    }

    @OneToMany(mappedBy = "station")
    public Set<PathStationEntity> getPathStations() {
        return pathStations;
    }

    public void setPathStations(Set<PathStationEntity> pathStations) {
        this.pathStations = pathStations;
    }
}
