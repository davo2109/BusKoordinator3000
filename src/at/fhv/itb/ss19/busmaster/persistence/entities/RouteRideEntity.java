package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "route_ride", schema = "public", catalog = "busdb")
public class RouteRideEntity {

    @Id
    @SequenceGenerator(name = "id_Sequence", sequenceName = "route_ride_route_ride_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @Column(name = "route_ride_id", unique = true, nullable = false)

    private int routeRideId;


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

    @ManyToMany(mappedBy = "routeRides")
    private List<OperationEntity> operations;
    public List<OperationEntity> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationEntity> operations) {
        this.operations = operations;
    }

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "route_id", nullable = false)
    private RouteEntity route;

    public RouteEntity getRoute() {
        return route;
    }

    public void setRoute(RouteEntity route) {
        this.route = route;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "start_time_id", referencedColumnName = "start_time_id", nullable = false)
    private StartTimeEntity startTime;

    public StartTimeEntity getStartTime() {
        return startTime;
    }

    public void setStartTime(StartTimeEntity startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "RouteRideEntity{" +
                "routeRideId=" + routeRideId +
                ", route=" + route +
                ", startTime=" + startTime +
                '}';
    }
}
