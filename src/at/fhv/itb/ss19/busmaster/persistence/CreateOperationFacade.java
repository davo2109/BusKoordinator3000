package at.fhv.itb.ss19.busmaster.persistence;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import at.fhv.itb.ss19.busmaster.domain.Operation;
import at.fhv.itb.ss19.busmaster.domain.security.IRoute;

public interface CreateOperationFacade {

	List<? extends IRoute> getValidRoutesByMonth(int month);

	List<? extends IRoute> getValidRoutesByDay(LocalDate date);

	List<Operation> getOperationsByDate(Date valueOf);

	List<Operation> getOperationsByMonth(int month);

	void saveOperations(List<Operation> newOperations);

	void deleteOperation(Operation oper);

	void deleteOperations(List<Operation> deleteOperation);

	void saveOperation(Operation oper);

}
