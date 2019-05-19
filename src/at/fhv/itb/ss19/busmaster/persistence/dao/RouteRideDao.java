package at.fhv.itb.ss19.busmaster.persistence.dao;

import at.fhv.itb.ss19.busmaster.persistence.entities.OperationEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteRideEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class RouteRideDao {

    public List<RouteRideEntity> getAllBuses(Session activeSession) {
        return activeSession.createQuery("FROM RouteRideEntity", RouteRideEntity.class).list();
    }

    public List<RouteRideEntity> getAllRouteRidesByRoute(Session activeSession, RouteEntity route) {
        Query<RouteRideEntity> query = activeSession.createQuery("FROM RouteRideEntity r WHERE r.route = :route", RouteRideEntity.class);
        query.setParameter("route", route);
        return query.list();
    }
    
    public List<RouteRideEntity> getRidesByDayTypeInMonth (Session activeSession, int dayType, int month) {
    	Query<RouteRideEntity> query = activeSession.createQuery("FROM RouteRideEntity r WHERE r.startTime.startTimeType = :daytype AND MONTH(r.operations) = :givenMonth", RouteRideEntity.class);
    	query.setParameter("daytype", dayType);
    	query.setParameter("givenMonth", month);
    	return query.list();
    }

    public RouteRideEntity getRouteRideById (Session activeSession, int id){
        Query<RouteRideEntity> query = activeSession.createQuery("FROM RouteRideEntity r WHERE r.id = :id", RouteRideEntity.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException n){
            return null;
        }
    }



    public void persistRouteRide(Session activeSession, RouteRideEntity routeRide) {
            activeSession.saveOrUpdate(routeRide);
    }
}
