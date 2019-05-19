package at.fhv.itb.ss19.busmaster.domain.security;

import java.time.LocalDate;
import java.util.List;

import at.fhv.itb.ss19.busmaster.domain.ChangeStatus;
import at.fhv.itb.ss19.busmaster.domain.DayType;
import at.fhv.itb.ss19.busmaster.domain.RouteRide;
import at.fhv.itb.ss19.busmaster.persistence.entities.BusEntity;

public interface IOperation {

	public int getOperationId();

	public DayType getDayType();

	public String getName();

	public List<RouteRide> getRouteRides();

	public long getCheckSum();

	public LocalDate getDate();

	public BusEntity getBus();

	public String getBusLicence();

	public ChangeStatus getStatus();
}
