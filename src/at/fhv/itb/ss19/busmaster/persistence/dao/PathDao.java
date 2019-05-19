package at.fhv.itb.ss19.busmaster.persistence.dao;

import at.fhv.itb.ss19.busmaster.persistence.entities.PathEntity;
import org.hibernate.Session;

import java.util.List;

public class PathDao {
    public List<PathEntity> getAllPaths(Session activeSession) {
        return activeSession.createQuery("FROM PathEntity ").list();
    }

    public void persistPath(Session activeSession, PathEntity path) {
        activeSession.persist(path);
    }
    
    public void changePathDescription(Session activeSession, PathEntity path, String pathDescription)
    {
    	path.setPathDescription(pathDescription);
    	activeSession.merge(path);
    }
}
