package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "route", schema = "public", catalog = "busdb")
public class RouteEntity {

    @Id
    @SequenceGenerator(name = "id_Sequence", sequenceName = "route_route_id_seq", allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @Column(name = "route_id", unique = true, nullable = false)
    private int routeId;

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    @Basic
    @Column(name = "route_number")
    private int routeNumber;

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    @Basic
    @Column(name = "valid_from")
    private Date validFrom;
    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    @Basic
    @Column(name = "valid_to")
    private Date validTo;
    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Basic
    @Column(name = "variation")
    private String variation;
    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteEntity that = (RouteEntity) o;
        return routeId == that.routeId &&
                routeNumber == that.routeNumber &&
                Objects.equals(validFrom, that.validFrom) &&
                Objects.equals(validTo, that.validTo) &&
                Objects.equals(variation, that.variation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, routeNumber, validFrom, validTo, variation);
    }

    @OneToMany(mappedBy = "route", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<PathEntity> paths;
    public Set<PathEntity> getPaths() {
        return paths;
    }

    public void setPaths(Set<PathEntity> paths) {
        this.paths = paths;
    }

    @OneToMany(mappedBy = "route")
    private Set<RouteRideEntity> routeRides;
    public Set<RouteRideEntity> getRouteRides() {
        return routeRides;
    }

    public void setRouteRides(Set<RouteRideEntity> routeRides) {
        this.routeRides = routeRides;
    }

    @Override
    public String toString() {
        return "RouteEntity{" +
                "routeId=" + routeId +
                ", routeNumber=" + routeNumber +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", variation='" + variation + '\'' +
                ", paths=" + paths +
                ", routeRides=" + routeRides +
                '}';
    }
}
