package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "driving_licenses", schema = "public", catalog = "busdb")
public class DrivingLicensesEntity {
    private int driverId;
    private String drivingLicenses;

    @Id
    @Column(name = "driver_id")
    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    @Basic
    @Column(name = "driving_licenses")
    public String getDrivingLicenses() {
        return drivingLicenses;
    }

    public void setDrivingLicenses(String drivingLicenses) {
        this.drivingLicenses = drivingLicenses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrivingLicensesEntity that = (DrivingLicensesEntity) o;
        return driverId == that.driverId &&
                Objects.equals(drivingLicenses, that.drivingLicenses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId, drivingLicenses);
    }
}
