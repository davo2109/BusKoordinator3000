package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "bus", schema = "public", catalog = "busdb")
public class BusEntity {
    private int busId;
    private int maintenanceKm;
    private String licenceNumber;
    private String make;
    private String model;
    private String note;
    private Date registrationDate;
    private int seatPlaces;
    private int standPlaces;
    private Set<SuspendedEntity> suspendeds;
    private Set<OperationEntity> operations;

    @Id
    @Column(name = "bus_id")
    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    @Basic
    @Column(name = "maintenance_km")
    public int getMaintenanceKm() {
        return maintenanceKm;
    }

    public void setMaintenanceKm(int maintenanceKm) {
        this.maintenanceKm = maintenanceKm;
    }

    @Basic
    @Column(name = "licence_number")
    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    @Basic
    @Column(name = "make")
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Basic
    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "registration_date")
    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Basic
    @Column(name = "seat_places")
    public int getSeatPlaces() {
        return seatPlaces;
    }

    public void setSeatPlaces(int seatPlaces) {
        this.seatPlaces = seatPlaces;
    }

    @Basic
    @Column(name = "stand_places")
    public int getStandPlaces() {
        return standPlaces;
    }

    public void setStandPlaces(int standPlaces) {
        this.standPlaces = standPlaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusEntity busEntity = (BusEntity) o;
        return busId == busEntity.busId &&
                maintenanceKm == busEntity.maintenanceKm &&
                seatPlaces == busEntity.seatPlaces &&
                standPlaces == busEntity.standPlaces &&
                Objects.equals(licenceNumber, busEntity.licenceNumber) &&
                Objects.equals(make, busEntity.make) &&
                Objects.equals(model, busEntity.model) &&
                Objects.equals(note, busEntity.note) &&
                Objects.equals(registrationDate, busEntity.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(busId, maintenanceKm, licenceNumber, make, model, note, registrationDate, seatPlaces, standPlaces);
    }

    @OneToMany(mappedBy = "bus")
    public Set<SuspendedEntity> getSuspendeds() {
        return suspendeds;
    }

    public void setSuspendeds(Set<SuspendedEntity> suspendeds) {
        this.suspendeds = suspendeds;
    }
    
    @OneToMany(mappedBy = "bus", fetch = FetchType.EAGER)
    public Set<OperationEntity> getOperations() {
    	return operations;
    }
    
    public void setOperations(Set<OperationEntity> operations) {
    	this.operations = operations;
    }
}