package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;

import at.fhv.itb.ss19.busmaster.domain.Available;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "suspended", schema = "public", catalog = "busdb")
public class SuspendedEntity implements Available{
    private int suspendedId;
    private Date dateFrom;
    private Date dateTo;
    private String cause;
    private BusEntity bus;

    @Id
    @Column(name = "suspended_id")
    public int getSuspendedId() {
        return suspendedId;
    }

    public void setSuspendedId(int suspendedId) {
        this.suspendedId = suspendedId;
    }

    @Basic
    @Column(name = "date_from")
    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Basic
    @Column(name = "date_to")
    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Basic
    @Column(name = "cause")
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuspendedEntity that = (SuspendedEntity) o;
        return suspendedId == that.suspendedId &&
                Objects.equals(dateFrom, that.dateFrom) &&
                Objects.equals(dateTo, that.dateTo) &&
                Objects.equals(cause, that.cause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suspendedId, dateFrom, dateTo, cause);
    }

    @ManyToOne
    @JoinColumn(name = "bus_id", referencedColumnName = "bus_id", nullable = false)
    public BusEntity getBus() {
        return bus;
    }

    public void setBus(BusEntity bus) {
        this.bus = bus;
    }
}
