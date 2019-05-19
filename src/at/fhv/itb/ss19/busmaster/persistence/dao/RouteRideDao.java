package at.fhv.itb.ss19.busmaster.persistence.dao;

import at.fhv.itb.ss19.busmaster.persistence.entities.RouteRideEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RouteRideDao {

    public List<RouteRideEntity> getAllBuses(Session activeSession) {
        return activeSession.createQuery("FROM RouteRideEntity", RouteRideEntity.class).list();
    }
    
    public List<RouteRideEntity> getRidesByDayTypeInMonth (Session activeSession, int dayType, int month) {
    	Query<RouteRideEntity> query = activeSession.createQuery("FROM RouteRideEntity r WHERE r.startTime.startTimeType = :daytype AND MONTH(r.operation.date) = :givenMonth", RouteRideEntity.class);
    	query.setParameter("daytype", dayType);
    	query.setParameter("givenMonth", month);
    	return query.list();
    }
}
