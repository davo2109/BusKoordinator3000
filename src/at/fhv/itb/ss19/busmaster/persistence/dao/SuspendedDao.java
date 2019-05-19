package at.fhv.itb.ss19.busmaster.persistence.dao;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import at.fhv.itb.ss19.busmaster.persistence.entities.BusEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.SuspendedEntity;

public class SuspendedDao {

	public List<SuspendedEntity> getEntriesByBusAndDate(Session activeSession, BusEntity bus, Date date) {
		Query<SuspendedEntity> query = activeSession
				.createQuery("FROM SuspendedEntity s WHERE s.bus = :bus AND :date BETWEEN s.dateFrom AND s.dateTo");
		query.setParameter("bus", bus);
		query.setParameter("date", date);
		return query.list();
	}
}
