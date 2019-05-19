package at.fhv.itb.ss19.busmaster.domain;

import at.fhv.itb.ss19.busmaster.domain.security.IOperation;
import at.fhv.itb.ss19.busmaster.persistence.entities.BusEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.OperationEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteRideEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Operation implements IOperation, Available {
	private OperationEntity _operationEntity;
	private DayType _dayType;
	private long _checkSum;
	private boolean _checksumValid;
	private ChangeStatus _state;

	public Operation() {
		_operationEntity = new OperationEntity();
		_dayType = DayType.WORKDAY;
		_checksumValid = true;
	}

	public Operation(OperationEntity operationEntity) {
		_operationEntity = operationEntity;
		_dayType = DayType.getDayTypeOfDate(getDate());
		_state = ChangeStatus.OK;
	}

	public OperationEntity getCapsuledEntity() {
		return _operationEntity;
	}

	public int getOperationId() {
		return _operationEntity.getOperationId();
	}

	public DayType getDayType() {
		return _dayType;
	}

	public void setDayType(DayType dayType) {
		_dayType = dayType;
	}

	public String getName() {
		return "Tour " + getOperationId();
	}

	public List<RouteRide> getRouteRides() {
		return _operationEntity.getRouteRides().stream().map(RouteRide::new).collect(Collectors.toList());
	}

	public long getCheckSum() {
		if (_checkSum == 0 || !_checksumValid) {

            if (_dayType != null) {
				_checkSum += _dayType.hashCode();
			}
			for (RouteRideEntity ride : _operationEntity.getRouteRides()) {
				_checkSum += ride.getStartTime().hashCode();
			}
			_checksumValid = true;
		}

		return _checkSum;
	}

	public void invalidateChecksum() {
		_checksumValid = false;
	}

	public LocalDate getDate() {
		return _operationEntity.getDate().toLocalDate();
	}

	public void setDate(LocalDate date) {
		_operationEntity.setDate(Date.valueOf(date));
	}

	public void setBus(BusEntity bus) {
		_operationEntity.setBus(bus);
	}

	public BusEntity getBus() {
		return _operationEntity.getBus();
	}

	public String getBusLicence() {
		return _operationEntity.getBus() != null ? _operationEntity.getBus().getLicenceNumber() : "";
	}

	public ChangeStatus getStatus() {
		return _state;
	}

	public void setStatus(ChangeStatus state) {
		_state = state;
	}

	@Override
	public String toString() {
		return "Operation [_operationId=" + getOperationId() + ", _dayType=" + _dayType + ", _checkSum=" + _checkSum
				+ "]";
	}
}
