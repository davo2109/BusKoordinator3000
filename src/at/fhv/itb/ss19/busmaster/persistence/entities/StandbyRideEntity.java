package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "standby_ride", schema = "public", catalog = "busdb")
public class StandbyRideEntity {
    private int standbyRideId;
    private int busId;
    private int operationId;
    private Timestamp startDateTime;
    private Timestamp endDateTime;

    @Id
    @Column(name = "standby_ride_id")
    public int getStandbyRideId() {
        return standbyRideId;
    }

    public void setStandbyRideId(int standbyRideId) {
        this.standbyRideId = standbyRideId;
    }

    @Basic
    @Column(name = "bus_id")
    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    @Basic
    @Column(name = "operation_id")
    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    @Basic
    @Column(name = "start_date_time")
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Basic
    @Column(name = "end_date_time")
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StandbyRideEntity that = (StandbyRideEntity) o;

        if (standbyRideId != that.standbyRideId) return false;
        if (busId != that.busId) return false;
        if (operationId != that.operationId) return false;
        if (startDateTime != null ? !startDateTime.equals(that.startDateTime) : that.startDateTime != null)
            return false;
        if (endDateTime != null ? !endDateTime.equals(that.endDateTime) : that.endDateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = standbyRideId;
        result = 31 * result + busId;
        result = 31 * result + operationId;
        result = 31 * result + (startDateTime != null ? startDateTime.hashCode() : 0);
        result = 31 * result + (endDateTime != null ? endDateTime.hashCode() : 0);
        return result;
    }
}
