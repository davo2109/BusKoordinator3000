package at.fhv.itb.ss19.busmaster.persistence.dao;

import at.fhv.itb.ss19.busmaster.persistence.entities.BusEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.util.List;

public class BusDao {

    public List<BusEntity> getAllBuses(Session activeSession){
        return activeSession.createQuery("FROM BusEntity").list();
    }

    public BusEntity getBusById(Session activeSession, int id){
        Query<BusEntity> query = activeSession.createQuery("FROM BusEntity b WHERE b.id = :id");
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException n){
            return null;
        }
    }

    public List<BusEntity> getBusesBetwRegDates(Session activeSession, Date startDate, Date endDate){
        Query<BusEntity> query = activeSession.createQuery("FROM BusEntity b WHERE b.registrationDate BETWEEN :date1 AND :date2");
        query.setParameter("date1", startDate);
        query.setParameter("date2", endDate);
        return query.list();
    }

    public List<BusEntity> getBusesByModel(Session activeSession, String model){
        Query<BusEntity> query = activeSession.createQuery("FROM BusEntity b WHERE b.model = :model");
        query.setParameter("model", model);
        return query.list();
    }

    public BusEntity getBusByLicenseNumber(Session activeSession, String licenseNr){
        Query<BusEntity> query = activeSession.createQuery("FROM BusEntity b WHERE b.licenceNumber = :licenseNr");
        query.setParameter("licenseNr", licenseNr);
        try {
            return query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }

    public List<BusEntity> getBusesToKm(Session activeSession, int km){
        Query<BusEntity> query = activeSession.createQuery("FROM BusEntity b WHERE b.maintenanceKm <= :km");
        query.setParameter("km", km);
        return query.list();
    }
}
