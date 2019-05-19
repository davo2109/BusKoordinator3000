package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;

import at.fhv.itb.ss19.busmaster.domain.Available;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "route_ride", schema = "public", catalog = "busdb")
public class RouteRideEntity implements Available{
    private int routeRideId;
    private List<OperationEntity> operations;
    private RouteEntity route;
    private StartTimeEntity startTime;

    @Id
    @Column(name = "route_ride_id")
    public int getRouteRideId() {
        return routeRideId;
    }

    public void setRouteRideId(int routeRideId) {
        this.routeRideId = routeRideId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteRideEntity that = (RouteRideEntity) o;
        return routeRideId == that.routeRideId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeRideId);
    }

    @ManyToMany(mappedBy = "routeRides", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<OperationEntity> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationEntity> operations) {
        this.operations = operations;
    }

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "route_id", nullable = false)
    public RouteEntity getRoute() {
        return route;
    }

    public void setRoute(RouteEntity route) {
        this.route = route;
    }

    @ManyToOne
    @JoinColumn(name = "start_time_id", referencedColumnName = "start_time_id", nullable = false)
    public StartTimeEntity getStartTime() {
        return startTime;
    }

    public void setStartTime(StartTimeEntity startTime) {
        this.startTime = startTime;
    }
}
