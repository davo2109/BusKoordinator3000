package at.fhv.itb.ss19.busmaster.ui.controller;

import at.fhv.itb.ss19.busmaster.application.CreateOperations;
import at.fhv.itb.ss19.busmaster.domain.MonthEnum;
import at.fhv.itb.ss19.busmaster.domain.Operation;
import at.fhv.itb.ss19.busmaster.domain.security.IRoute;
import at.fhv.itb.ss19.busmaster.domain.security.IRouteRide;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class CreateOperationsController {
    @FXML
    private TableView<Operation> operationTable;
    @FXML
    private TableView<IRoute> routeTable;
    @FXML
    private TableColumn<IRoute, String> routeCol;
    @FXML
    private TableColumn<IRoute, String> openRidesCol;
    @FXML
    private TableColumn<IRoute, String> variationCol;
    @FXML
    private TableView<IRouteRide> routeRideTable;
    @FXML
    private TableColumn<IRouteRide, String> rideCol;
    @FXML
    private TableColumn<IRouteRide, String> startTimeCol;
    @FXML
    private TableColumn<IRouteRide, String> startStationCol;
    @FXML
    private TableColumn<IRouteRide, String> endTimeCol;
    @FXML
    private TableColumn<IRouteRide, String> endStationCol;
    @FXML
    private ChoiceBox<MonthEnum> monthChooser;
    @FXML
    private CheckBox planningUnitDay;
    @FXML
    private DatePicker dayPicker;
    @FXML
    private Button rideViewButton;
    @FXML
    private Label rideLabel;

    private CreateOperations _logic = new CreateOperations();
    private ObservableList<IRoute> _routes = FXCollections.observableArrayList();
    private ObservableList<Operation> _operations = FXCollections.observableArrayList();
    private ObservableList<IRouteRide> _routeRides = FXCollections.observableArrayList();

    private TableColumn<IRoute, String> routeTableSortColumn;

    @FXML
    private void initialize() {
        planningUnitDay.setSelected(false);
        dayPicker.setDisable(!planningUnitDay.isSelected());

        monthChooser.getItems().setAll(MonthEnum.values());
        monthChooser.getSelectionModel().select(LocalDate.now().getMonthValue());

        routeTable.setItems(_routes);
        routeRideTable.setItems(_routeRides);

        routeCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRouteNumber())));
        openRidesCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOpenRidesNumber())));
        variationCol.setCellValueFactory(cellData -> new SimpleStringProperty((cellData.getValue().getVariation() == null) ? "Default" : cellData.getValue().getVariation()));

        rideCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRouteRideId())));
        startTimeCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStartTime().getStartTime())));
        startStationCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStartTime().getStartTime())));
        endTimeCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStartTime().getStartTime())));
        endStationCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStartTime().getStartTime())));


        routeTableSortColumn = routeCol;
        routeTable.getSortOrder().add(routeTableSortColumn);

        _routes.addAll(_logic.getValidRoutesByMonth(monthChooser.getSelectionModel().getSelectedIndex() + 1));
        _routeRides.addAll(new ArrayList<>());

        initSelectionListenersAndActionHandlers();
    }

    private void initSelectionListenersAndActionHandlers() {
        planningUnitDay.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                observable.removeListener(this);
                changePlanningUnit(oldValue);
                observable.addListener(this);
            }
        });

        monthChooser.valueProperty().addListener(new ChangeListener<MonthEnum>() {
            @Override
            public void changed(ObservableValue<? extends MonthEnum> observable, MonthEnum oldValue, MonthEnum newValue) {
                observable.removeListener(this);
                loadRoutesPerMonth();
                observable.addListener(this);
            }
        });

        dayPicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                observable.removeListener(this);
                loadRoutesPerDay();
                observable.addListener(this);
            }
        });

        routeTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IRoute>() {
            @Override
            public void changed(ObservableValue<? extends IRoute> observable, IRoute oldValue, IRoute newValue) {
                routeSelectionChanged(newValue);
            }
        });

        rideViewButton.setOnAction(event -> {
            if (routeTable.getSelectionModel().getSelectedItem() != null) {
                if (rideViewButton.getText().equals("show all Rides")) {

                    _routeRides.clear();
                    _routeRides.addAll(routeTable.getSelectionModel().getSelectedItem().getRouteRides());
                    routeRideTable.getSortOrder().clear();
                    routeRideTable.getSortOrder().add(startTimeCol);
                    rideViewButton.setText("show open Rides");
                    rideLabel.setText("All Rides");

                } else {
                    _routeRides.clear();
                    _routeRides.addAll(routeTable.getSelectionModel().getSelectedItem().getFreeRouteRides());
                    routeRideTable.getSortOrder().clear();
                    routeRideTable.getSortOrder().add(startTimeCol);
                    rideViewButton.setText("show all Rides");
                    rideLabel.setText("Open Rides");
                }
            } else {
                if (rideViewButton.getText().equals("show all Rides")) {
                    rideViewButton.setText("show open Rides");
                    rideLabel.setText("All Rides");

                } else {
                    rideViewButton.setText("show all Rides");
                    rideLabel.setText("Open Rides");
                }
            }
        });
    }

    private void changePlanningUnit(boolean oldValue){
        monthChooser.setDisable(planningUnitDay.isSelected());
        dayPicker.setDisable(!planningUnitDay.isSelected());
        if(planningUnitDay.isSelected()){
            loadRoutesPerDay();
        }else{
            loadRoutesPerMonth();
        }
    }

    private void loadRoutesPerMonth(){
        _routes.clear();
        _routes.addAll(_logic.getValidRoutesByMonth(monthChooser.getSelectionModel().getSelectedIndex() + 1));
        routeTable.getSortOrder().clear();
        routeTable.getSortOrder().add(routeTableSortColumn);
    }

    private void loadRoutesPerDay() {
        _routes.clear();
        if (dayPicker.getValue() != null) {
            _routes.addAll(_logic.getValidRoutesPerDay((Date.valueOf(dayPicker.getValue()))));
            routeTable.getSortOrder().clear();
            routeTable.getSortOrder().add(routeTableSortColumn);
        }
    }

    private void routeSelectionChanged(IRoute newValue) {
        if (newValue != null) {
            if (rideViewButton.getText().equals("show all Rides")) {
                _routeRides.clear();
                _routeRides.addAll(newValue.getFreeRouteRides());
                routeRideTable.getSortOrder().clear();
                routeRideTable.getSortOrder().add(startTimeCol);
            } else {
                _routeRides.clear();
                _routeRides.addAll(newValue.getRouteRides());
                routeRideTable.getSortOrder().clear();
                routeRideTable.getSortOrder().add(startTimeCol);
            }
        } else {
            _routeRides.clear();
        }
    }
}
