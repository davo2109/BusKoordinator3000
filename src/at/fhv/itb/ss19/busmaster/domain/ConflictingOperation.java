package at.fhv.itb.ss19.busmaster.domain;

import java.time.LocalDate;

import at.fhv.itb.ss19.busmaster.domain.security.IOperation;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteRideEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.SuspendedEntity;

public class ConflictingOperation {
	private IOperation _operation;
	private Available _cause;
	
	public ConflictingOperation(Operation operation, Available cause) {
		_operation = (IOperation) operation;
		_cause = cause;
	}
	
	public String getOperationId () {
		return String.valueOf(_operation.getOperationId());
	}
	
	public LocalDate getDate() {
		return _operation.getDate();
	}
	
	public String getCause() {
		if(_cause instanceof RouteRideEntity) {
			return "Already assigned tour on this day";
		}
		else if(_cause instanceof SuspendedEntity) {
			SuspendedEntity suspended = (SuspendedEntity)_cause;
			return suspended.getCause().equals("") ? "conflicting suspention" : "conflicting suspention:" + suspended.getCause();
		}
		
		return "conflicting";
	}
}
