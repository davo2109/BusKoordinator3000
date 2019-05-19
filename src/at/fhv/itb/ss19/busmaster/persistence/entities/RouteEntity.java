package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "route", schema = "public", catalog = "busdb")
public class RouteEntity {
    private int routeId;
    private int routeNumber;
    private Date validFrom;
    private Date validTo;
    private String variation;
    private Set<PathEntity> paths;
    private Set<RouteRideEntity> routeRides;

    @Id
    @Column(name = "route_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "route_route_id_seq", allocationSize = 10)
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    @Basic
    @Column(name = "route_number")
    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    @Basic
    @Column(name = "valid_from")
    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    @Basic
    @Column(name = "valid_to")
    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Basic
    @Column(name = "variation")
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
    public Set<PathEntity> getPaths() {
        return paths;
    }

    public void setPaths(Set<PathEntity> paths) {
        this.paths = paths;
    }

    @OneToMany(mappedBy = "route")
    public Set<RouteRideEntity> getRouteRides() {
        return routeRides;
    }

    public void setRouteRides(Set<RouteRideEntity> routeRides) {
        this.routeRides = routeRides;
    }
}
