package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "start_time", schema = "public", catalog = "busdb")
public class StartTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "start_time_id")
    private int startTimeId;

    public int getStartTimeId() {
        return startTimeId;
    }

    public void setStartTimeId(int startTimeId) {
        this.startTimeId = startTimeId;
    }

    @Basic
    @Column(name = "required_capacity")
    private int requiredCapacity;
    public int getRequiredCapacity() {
        return requiredCapacity;
    }

    public void setRequiredCapacity(int requiredCapacity) {
        this.requiredCapacity = requiredCapacity;
    }

    @Basic
    @Column(name = "start_time")
    private Time startTime;

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "start_time_type")
    private Integer startTimeType;
    public Integer getStartTimeType() {
        return startTimeType;
    }

    public void setStartTimeType(Integer startTimeType) {
        this.startTimeType = startTimeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartTimeEntity that = (StartTimeEntity) o;
        return startTimeId == that.startTimeId &&
                requiredCapacity == that.requiredCapacity &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(startTimeType, that.startTimeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTimeId, requiredCapacity, startTime, startTimeType);
    }

    @OneToMany(mappedBy = "startTime", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RouteRideEntity> routeRides;
    public Set<RouteRideEntity> getRouteRides() {
        return routeRides;
    }

    public void setRouteRides(Set<RouteRideEntity> routeRides) {
        this.routeRides = routeRides;
    }

    @ManyToOne
    @JoinColumn(name = "path_id", referencedColumnName = "path_id", nullable = false)
    private PathEntity path;
    public PathEntity getPath() {
        return path;
    }

    public void setPath(PathEntity path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "StartTimeEntity{" +
                "startTimeId=" + startTimeId +
                ", requiredCapacity=" + requiredCapacity +
                ", startTime=" + startTime +
                ", startTimeType=" + startTimeType +
                ", routeRides=" + routeRides +
                ", path=" + path +
                '}';
    }
}
