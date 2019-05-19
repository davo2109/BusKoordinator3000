package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "path", schema = "public", catalog = "busdb")
public class PathEntity {
    private int pathId;
    private String pathDescription;
    private RouteEntity route;
    private Set<PathStationEntity> pathStations;
    private Set<StartTimeEntity> startTimes;
    private boolean isRetour;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "path_id")
    public int getPathId() {
        return pathId;
    }

    public void setPathId(int pathId) {
        this.pathId = pathId;
    }

    
    @Column(name = "path_description")
    public String getPathDescription() {
        return pathDescription;
    }

    public void setPathDescription(String pathDescription) {
        this.pathDescription = pathDescription;
    }

  
    @Column(name = "isretour")
    public boolean isRetour() {
        return isRetour;
    }

    public void setRetour(boolean retour) {
        isRetour = retour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathEntity that = (PathEntity) o;
        return pathId == that.pathId &&
                Objects.equals(pathDescription, that.pathDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathId, pathDescription);
    }

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    public RouteEntity getRoute() {
        return route;
    }

    public void setRoute(RouteEntity route) {
        this.route = route;
    }

    @OneToMany(mappedBy = "path")
    public Set<PathStationEntity> getPathStations() {
        return pathStations;
    }

    public void setPathStations(Set<PathStationEntity> pathStations) {
        this.pathStations = pathStations;
    }

    @OneToMany(mappedBy = "path")
    public Set<StartTimeEntity> getStartTimes() {
        return startTimes;
    }

    public void setStartTimes(Set<StartTimeEntity> startTimes) {
        this.startTimes = startTimes;
    }
}
