package at.fhv.itb.ss19.busmaster.persistence;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import at.fhv.itb.ss19.busmaster.domain.Operation;
import at.fhv.itb.ss19.busmaster.domain.security.IRoute;

public class CreateOperationFacadeImpl implements CreateOperationFacade{
	private static final DbFacade _facade = DbFacade.getInstance();

	@Override
	public List<? extends IRoute> getValidRoutesByMonth(int month) {
		return _facade.getValidRoutesByMonth(month);
	}

	@Override
	public List<? extends IRoute> getValidRoutesByDay(LocalDate date) {
		return _facade.getValidRoutesByDay(date);
	}

	@Override
	public List<Operation> getOperationsByDate(Date valueOf) {
		return _facade.getOperationsByDate(valueOf);
	}

	@Override
	public List<Operation> getOperationsByMonth(int month) {
		return _facade.getOperationsByMonth(month);
	}

	@Override
	public void saveOperations(List<Operation> newOperations) {
		_facade.saveOperations(newOperations);
	}

	@Override
	public void deleteOperation(Operation oper) {
		_facade.deleteOperation(oper);
	}

	@Override
	public void deleteOperations(List<Operation> deleteOperation) {
		_facade.deleteOperations(deleteOperation);
	}

	@Override
	public void saveOperation(Operation oper) {
		_facade.saveOperation(oper);
	}

}
