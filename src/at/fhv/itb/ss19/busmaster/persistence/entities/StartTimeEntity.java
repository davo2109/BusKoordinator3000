package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "start_time", schema = "public", catalog = "busdb")
public class StartTimeEntity {
    private int startTimeId;
    private int requiredCapacity;
    private Time startTime;
    private Integer startTimeType;
    private Set<RouteRideEntity> routeRides;
    private PathEntity path;

    @Id
    @Column(name = "start_time_id")
    public int getStartTimeId() {
        return startTimeId;
    }

    public void setStartTimeId(int startTimeId) {
        this.startTimeId = startTimeId;
    }

    @Basic
    @Column(name = "required_capacity")
    public int getRequiredCapacity() {
        return requiredCapacity;
    }

    public void setRequiredCapacity(int requiredCapacity) {
        this.requiredCapacity = requiredCapacity;
    }

    @Basic
    @Column(name = "start_time")
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "start_time_type")
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

    @OneToMany(mappedBy = "startTime")
    public Set<RouteRideEntity> getRouteRides() {
        return routeRides;
    }

    public void setRouteRides(Set<RouteRideEntity> routeRides) {
        this.routeRides = routeRides;
    }

    @ManyToOne
    @JoinColumn(name = "path_id", referencedColumnName = "path_id", nullable = false)
    public PathEntity getPath() {
        return path;
    }

    public void setPath(PathEntity path) {
        this.path = path;
    }
}
