package at.fhv.itb.ss19.busmaster.persistence.dao;

import at.fhv.itb.ss19.busmaster.persistence.entities.PathEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.PathStationEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PathStationDao {
	public List<PathStationEntity> getAll(Session activeSession) {
		return activeSession.createQuery("FROM PathStationEntity ").list();
	}
	
	public List<PathStationEntity> getAllByPathId(Session activeSession, PathEntity path) {
		Query<PathStationEntity> query = activeSession.createQuery("FROM PathStationEntity p WHERE p.path = :path");
		query.setParameter("path", path);
		return query.list();
	}
	
	public void addPathStation(Session activeSession, PathStationEntity pathStation) {
			activeSession.save(pathStation);
	}
	
	public void deletePathStations(Session activeSession, PathStationEntity pathStation)
	{
		activeSession.delete(pathStation);
	}
	
	public void changePreviousDistance(Session activeSession, PathStationEntity pathStation, int newDistanceFromPrevious)
	{
		pathStation.setDistanceFromPrevious(newDistanceFromPrevious);
		activeSession.merge(pathStation);
	}
	
	public void changePreviousTime(Session activeSession, PathStationEntity pathStation, int newTimeFromPrevious)
	{
		pathStation.setTimeFromPrevious(newTimeFromPrevious);
		activeSession.merge(pathStation);
	}
	
	public void changePositionOnPath(Session activeSession, PathStationEntity pathStation, int positionOnPath)
	{
		pathStation.setPositionOnPath(positionOnPath);
		activeSession.merge(pathStation);
	}
}
