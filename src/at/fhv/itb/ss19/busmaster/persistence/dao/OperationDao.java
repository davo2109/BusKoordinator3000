package at.fhv.itb.ss19.busmaster.persistence.dao;

import at.fhv.itb.ss19.busmaster.persistence.entities.BusEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.OperationEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import javax.persistence.NoResultException;
import java.sql.Date;
import java.util.List;

public class OperationDao {

	public List<OperationEntity> getAllOperations(Session activeSession) {
		return activeSession.createQuery("FROM OperationEntity ", OperationEntity.class).list();
	}

	public OperationEntity getOperationById(Session activeSession, int id) {
		Query<OperationEntity> query = activeSession.createQuery("FROM OperationEntity b WHERE b.id = :id",
				OperationEntity.class);
		query.setParameter("id", id);
		try {
			return query.getSingleResult();
		} catch (NoResultException n) {
			return null;
		}
	}

	public List<OperationEntity> getOperationsByDate(Session activeSession, Date date) {
		Query<OperationEntity> query = activeSession.createQuery("FROM OperationEntity b WHERE b.date = :date",
				OperationEntity.class);
		query.setParameter("date", date);
		return query.list();
	}

	public List<OperationEntity> getOperationsByMonth(Session activeSession, int month) {
		Query<OperationEntity> query = activeSession.createQuery("FROM OperationEntity b WHERE month(b.date) = :month",
				OperationEntity.class);
		query.setParameter("month", month);
		return query.list();
	}

	public void persistOperation(Session activeSession, OperationEntity operation) {
		activeSession.save(operation);
	}

	public List<OperationEntity> getOperationByBus(Session activeSession, BusEntity selectedBus) {
		Query<OperationEntity> query = activeSession.createQuery("FROM OperationEntity o WHERE o.bus = :bus", OperationEntity.class);
		query.setParameter("bus", selectedBus);
		return query.list();
	}
}
