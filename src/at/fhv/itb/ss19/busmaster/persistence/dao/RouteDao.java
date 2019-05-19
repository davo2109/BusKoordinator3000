package at.fhv.itb.ss19.busmaster.persistence.dao;

import at.fhv.itb.ss19.busmaster.persistence.entities.BusEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.util.List;

public class RouteDao {
    public List<RouteEntity> getAllRoutes(Session activeSession) {
        return activeSession.createQuery("FROM RouteEntity", RouteEntity.class).list();
    }

    public void persistRoute(Session activeSession, RouteEntity route) {
        activeSession.save(route);
    }

    public void updateRoute(Session activeSession, RouteEntity route) {
        activeSession.update(route);
    }

    public List<RouteEntity> getValidRoutesByMonth(Session activeSession, int month){
        Query<RouteEntity> query = activeSession.createQuery("FROM RouteEntity R WHERE month(R.validFrom) <= :month AND month(R.validTo) >= :month", RouteEntity.class);
        query.setParameter("month", month);
        return query.list();
    }

    public List<RouteEntity> getValidRoutesByDay(Session activeSession, Date date) {
        Query<RouteEntity> query = activeSession.createQuery("FROM RouteEntity R WHERE R.validFrom <= :date AND R.validTo >= :date", RouteEntity.class);
        query.setParameter("date", date);
        return query.list();
    }

    public RouteEntity getRouteById(Session activeSession, int id){
        Query<RouteEntity> query = activeSession.createQuery("FROM RouteEntity b WHERE b.id = :id");
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException n){
            return null;
        }
    }
}
