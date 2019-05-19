package at.fhv.itb.ss19.busmaster.domain.security;

import java.sql.Date;
import java.util.Set;

import at.fhv.itb.ss19.busmaster.domain.Operation;
import at.fhv.itb.ss19.busmaster.persistence.entities.SuspendedEntity;

public interface IBus {
	public int getBusId();
	
    public int getMaintenanceKm();

    public String getLicenceNumber();

    public String getMake();

    public String getModel();
    
    public String getNote();

    public Date getRegistrationDate();

    public int getSeatPlaces();
    
    public int getStandPlaces();

    public Set<SuspendedEntity> getSuspendeds();
    
    public Set<Operation> getOperations();
}
