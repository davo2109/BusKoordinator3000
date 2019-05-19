package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "operation", schema = "public", catalog = "busdb")
public class OperationEntity {


    @Id
    @SequenceGenerator(name = "id_Sequence", sequenceName = "operation_operation_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @Column(name = "operation_id", unique = true, nullable = false)
    private int operationId;

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    @Basic
    @Column(name = "operation_date")

    private Date operationDate;
    public Date getDate() {
        return operationDate;
    }

    public void setDate(Date date) {
        this.operationDate = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationEntity that = (OperationEntity) o;
        return operationId == that.operationId &&
                Objects.equals(operationDate, that.operationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationId, operationDate);
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "operation_ride",
            joinColumns = { @JoinColumn(name = "operation_id") },
            inverseJoinColumns = {@JoinColumn(name = "route_ride_id") })

    private Set<RouteRideEntity> routeRides;
    public Set<RouteRideEntity> getRouteRides() {
        return routeRides;
    }

    public void setRouteRides(Set<RouteRideEntity> routeRides) {
        this.routeRides= routeRides;
    }
    
    @ManyToOne
    @JoinColumn(name="bus_id", referencedColumnName="bus_id")

    private BusEntity bus;
    public BusEntity getBus() {
    	return bus;
    }
    
    public void setBus(BusEntity bus) {
    	this.bus = bus;
    }
}
