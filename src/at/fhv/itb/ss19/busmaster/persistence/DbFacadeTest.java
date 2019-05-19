package at.fhv.itb.ss19.busmaster.persistence;

import at.fhv.itb.ss19.busmaster.domain.Operation;
import at.fhv.itb.ss19.busmaster.domain.RouteRide;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;
import java.util.Set;

class DbFacadeTest {
//    DbFacade facade = DbFacade.getInstance();
//    @Test
//    void getAllOperations() {
//        List<OperationEntity> operations = facade.getAllOperations();
//        for (OperationEntity o : operations) {
//            System.out.println(o.getOperationId());
//            Set<RouteRideEntity> routeRides = o.getRouteRides();
//            for (RouteRideEntity r : routeRides) {
//                System.out.println(r.getOperation().getDate());
//            }
//        }
//    }
//
//    @Test
//    void getOperationsById() {
//        OperationEntity operation = facade.getOperationById(2);
//        System.out.println(operation.getOperationId());
//        Set<RouteRide> routeRides = operation.getRouteRides();
//        for (RouteRide r : routeRides) {
//            System.out.println(r.getOperation().getDate());
//        }
//
//    }
//
//    @Test
//    void getOperationByDate() {
//        DbFacade facade = DbFacade.getInstance();
//        List<Operation> operations = facade.getOperationsByDate(Date.valueOf("2019-07-09"));
//        for (Operation o : operations) {
//            System.out.println(o.getOperationId());
//            List<RouteRide> routeRides = o.getRouteRides();
//            for (RouteRide r : routeRides) {
//                System.out.println(r.getOperation().getDate());
//            }
//        }
//    }
}