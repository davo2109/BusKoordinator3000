package at.fhv.itb.ss19.busmaster.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import at.fhv.itb.ss19.busmaster.persistence.entities.StationEntity;


public class StationDao {

	 public List<StationEntity> getAllStations(Session activeSession) {
	        return activeSession.createQuery("FROM StationEntity ").list();
	    }

}
