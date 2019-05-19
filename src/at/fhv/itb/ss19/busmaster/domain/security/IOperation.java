package at.fhv.itb.ss19.busmaster.domain.security;

import java.time.LocalDate;
import java.util.List;

import at.fhv.itb.ss19.busmaster.domain.ChangeStatus;
import at.fhv.itb.ss19.busmaster.domain.DayType;
import at.fhv.itb.ss19.busmaster.domain.RouteRide;
import at.fhv.itb.ss19.busmaster.persistence.entities.BusEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.OperationEntity;

public interface IOperation {

	public int getOperationId();

	public DayType getDayType();

	public String getName();

	public List<RouteRide> getRouteRides();

	public long getRideCheckSum();

	public LocalDate getDate();

	public BusEntity getBus();

	public String getBusLicence();

	public ChangeStatus getStatus();

    public List<RouteRide> get_routeRideList();

    public void set_routeRideList(List<RouteRide> _routeRideList);

    public void setBus(BusEntity bus);

    public OperationEntity getCapsuledEntity();

    public void setStatus(ChangeStatus state);
}
