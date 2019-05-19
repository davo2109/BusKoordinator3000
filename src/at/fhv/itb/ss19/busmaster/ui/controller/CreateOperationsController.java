package at.fhv.itb.ss19.busmaster.ui.controller;

import at.fhv.itb.ss19.busmaster.application.CreateOperations;
import at.fhv.itb.ss19.busmaster.domain.DayType;
import at.fhv.itb.ss19.busmaster.domain.MonthEnum;
import at.fhv.itb.ss19.busmaster.domain.security.IOperation;
import at.fhv.itb.ss19.busmaster.domain.security.IRoute;
import at.fhv.itb.ss19.busmaster.domain.security.IRouteRide;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class CreateOperationsController {
	@FXML
	private TableView<IOperation> operationTable;
	@FXML
	private TableColumn<IOperation, String> nameCol;
	@FXML
	private TableColumn<IOperation, String> daytypeCol;
	@FXML
	private TableColumn<IOperation, Button> deleteOperationCol;
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
	private TableColumn<IRouteRide, Button> addRouteRideCol;
	@FXML
	private TableView<IRouteRide> selectedRouteRideTable;
	@FXML
	private TableColumn<IRouteRide, String> selRouteCol;
	@FXML
	private TableColumn<IRouteRide, String> selStartTimeCol;
	@FXML
	private TableColumn<IRouteRide, String> selStartStationCol;
	@FXML
	private TableColumn<IRouteRide, String> selEndTimeCol;
	@FXML
	private TableColumn<IRouteRide, String> selEndStationCol;
	@FXML
	private TableColumn<IRouteRide, Button> removeRideCol;
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
	@FXML
	private ChoiceBox<DayType> daytypeChooser;
	@FXML
	private Button operationCreate;

	private CreateOperations _logic = new CreateOperations();
	private ObservableList<IRoute> _routes = FXCollections.observableArrayList();
	private ObservableList<IOperation> _operations = FXCollections.observableArrayList();
	private ObservableList<IRouteRide> _routeRides = FXCollections.observableArrayList();
	private ObservableList<IRouteRide> _selectedRouteRides = FXCollections.observableArrayList();

	private TableColumn<IOperation, String> operationTableSortColumn;
	private TableColumn<IRoute, String> routeTableSortColumn;
	private TableColumn<IRouteRide, String> routeRideTableSortColumn;
	private TableColumn<IRouteRide, String> selectedRouteRideTableSortColumn;

	private boolean _allRides;
	private static final String BUTTON_TEXT_ALL = "show all journeys";
	private static final String BUTTON_TEXT_OPEN = "show open journeys";
	private static final String LABEL_TEXT_ALL = "All Journeys";
	private static final String LABEL_TEXT_OPEN = "Open Journeys";

	@FXML
	private void initialize() {
		planningUnitDay.setSelected(false);
		dayPicker.disableProperty().bind(planningUnitDay.selectedProperty().not());

		monthChooser.getItems().setAll(MonthEnum.values());
		monthChooser.getSelectionModel().select(LocalDate.now().getMonthValue());
		monthChooser.disableProperty().bind(planningUnitDay.selectedProperty());

		daytypeChooser.getItems().addAll(DayType.values());
		daytypeChooser.getSelectionModel().select(DayType.WORKDAY);
		daytypeChooser.disableProperty().bind(planningUnitDay.selectedProperty());

		routeTable.setItems(_routes);
		routeRideTable.setItems(_routeRides);
		operationTable.setItems(_operations);
		selectedRouteRideTable.setItems(_selectedRouteRides);

		routeCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRouteNumber())));
		openRidesCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(_logic.getOpenRouteRideCount(cellData.getValue(), planningUnitDay.isSelected(), monthChooser.getValue()))));
		variationCol.setCellValueFactory(cellData -> new SimpleStringProperty(
				(cellData.getValue().getVariation() == null) ? "Default" : cellData.getValue().getVariation()));

		rideCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRouteRideId())));
		startTimeCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStartingTime())));
		startStationCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStartStationName())));
		endTimeCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEndingTime())));
		endStationCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEndStationName())));
		addRouteRideCol
				.setCellFactory(ActionButtonTableCell.<IRouteRide>forTableColumn("add", (IRouteRide routeRide) -> {
					addRideToOperation(routeRide);
					displayRidesOfOperation(operationTable.getSelectionModel().getSelectedItem());
					return routeRide;
				}));

		selRouteCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRoute().getRouteNumber())));
		selStartTimeCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStartingTime())));
		selStartStationCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStartStationName())));
		selEndTimeCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEndingTime())));
		selEndStationCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEndStationName())));
		removeRideCol
				.setCellFactory(ActionButtonTableCell.<IRouteRide>forTableColumn("remove", (IRouteRide routeRide) -> {
					removeRideFromOperation(routeRide);
					displayRidesOfOperation(operationTable.getSelectionModel().getSelectedItem());
					return routeRide;
				}));

		nameCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOperationId())));
		daytypeCol
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDayType().toString()));
		deleteOperationCol.setCellFactory(ActionButtonTableCell.<IOperation>forTableColumn("del", (IOperation oper) -> {
			deleteOperation(oper);
			loadOperations();
			return oper;
		}));

		_routes.addAll(_logic.getValidRoutesOfMonth(monthChooser.getSelectionModel().getSelectedIndex() + 1));
		_routeRides.addAll(new ArrayList<>());
		_selectedRouteRides.addAll(new ArrayList<>());
		_operations.addAll(_logic.getGroupedOperationsOfMonth(monthChooser.getSelectionModel().getSelectedIndex() + 1));

		operationTableSortColumn = nameCol;
		operationTable.getSortOrder().add(operationTableSortColumn);

		routeTableSortColumn = routeCol;
		routeTable.getSortOrder().add(routeTableSortColumn);

		routeRideTableSortColumn = startTimeCol;
		routeRideTable.getSortOrder().add(routeRideTableSortColumn);

		selectedRouteRideTableSortColumn = selStartTimeCol;
		selectedRouteRideTable.getSortOrder().add(selectedRouteRideTableSortColumn);

		initSelectionListenersAndActionHandlers();
	}

	private void initSelectionListenersAndActionHandlers() {
		planningUnitDay.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				changePlanningUnit(oldValue);
			}
		});

		monthChooser.valueProperty().addListener(new ChangeListener<MonthEnum>() {
			@Override
			public void changed(ObservableValue<? extends MonthEnum> observable, MonthEnum oldValue,
					MonthEnum newValue) {
				_selectedRouteRides.clear();
				loadRoutes();
				loadOperations();
			}
		});

		dayPicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				_selectedRouteRides.clear();
				_logic.setPlanningUnitDay(planningUnitDay.isSelected());
				daytypeChooser.getSelectionModel().select(_logic.getDayTypeOfDate(dayPicker.getValue()));
				loadRoutes();
				loadOperations();
			}
		});

		operationTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IOperation>() {
			@Override
			public void changed(ObservableValue<? extends IOperation> observable, IOperation oldValue,
					IOperation newValue) {
				if (newValue != null) {
					displayRidesOfOperation(newValue);
				}
			}
		});

		routeTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IRoute>() {
			@Override
			public void changed(ObservableValue<? extends IRoute> observable, IRoute oldValue, IRoute newValue) {
				routeSelectionChanged(newValue);
			}
		});

		rideViewButton.setOnAction(event -> {
			_allRides = !_allRides;

			reloadRouteRides();
		});

		operationCreate.setOnAction(event -> {
			createOperations();
		});
	}

	private void changePlanningUnit(boolean oldValue) {
		_logic.setPlanningUnitDay(planningUnitDay.isSelected());
		if (dayPicker.getValue() != null) {
			loadRoutes();
			loadOperations();
		}
	}

	private void loadRoutes() {
		_routes.clear();

		if (planningUnitDay.isSelected()) {
			_routes.addAll(_logic.getValidRoutesOfDay(dayPicker.getValue()));
		} else {
			_routes.addAll(_logic.getValidRoutesOfMonth(monthChooser.getSelectionModel().getSelectedIndex() + 1));
		}

		routeTable.getSortOrder().clear();
		routeTable.getSortOrder().add(routeTableSortColumn);
	}

	private void reloadRouteRides() {
		// change button and label text
		if (_allRides) {
			rideViewButton.setText(BUTTON_TEXT_OPEN);
			rideLabel.setText(LABEL_TEXT_ALL);
		} else {
			rideViewButton.setText(BUTTON_TEXT_ALL);
			rideLabel.setText(LABEL_TEXT_OPEN);
		}

		_routeRides.clear();

		if (routeTable.getSelectionModel().getSelectedItem() != null
				&& operationTable.getSelectionModel().getSelectedItem() != null) {
			IRoute selectedRoute = routeTable.getSelectionModel().getSelectedItem();
			DayType daytype = operationTable.getSelectionModel().getSelectedItem().getDayType();
			if (_allRides) {
				_routeRides.addAll(selectedRoute.getRouteRides());
			} else {
//				LocalDate date;
//				if (planningUnitDay.isSelected()) {
//					date = dayPicker.getValue();
//				} else {
//					date = LocalDate.of(LocalDate.now().getYear(),
//							monthChooser.getSelectionModel().getSelectedIndex() + 1, 1);
//				}
//				_routeRides.addAll(_logic.getOpenRides(selectedRoute, date, daytype));

				_routeRides.addAll(_logic.getOpenRides(selectedRoute, daytype));
			}

			routeRideTable.getSortOrder().clear();
			routeRideTable.getSortOrder().add(startTimeCol);
		}
	}

	private void loadOperations() {
		_operations.clear();

		if (planningUnitDay.isSelected()) {
			_operations.addAll(_logic.getOperationsByDate(dayPicker.getValue()));
		} else {
			_operations.addAll(
					_logic.getGroupedOperationsOfMonth(monthChooser.getSelectionModel().getSelectedIndex() + 1));
		}

		operationTable.getSortOrder().clear();
		operationTable.getSortOrder().add(operationTableSortColumn);
	}

	private void routeSelectionChanged(IRoute newValue) {
		reloadRouteRides();
	}

	private void displayRidesOfOperation(IOperation selectedOperation) {
		_selectedRouteRides.clear();
		if (selectedOperation != null) {
			_selectedRouteRides.addAll(selectedOperation.getRouteRides());

			selectedRouteRideTable.getSortOrder().clear();
			selectedRouteRideTable.getSortOrder().add(selectedRouteRideTableSortColumn);
			
			reloadRouteRides();
		}
	}

	private void createOperations() {
		if (planningUnitDay.isSelected()) {
			_logic.createOperationForDay(dayPicker.getValue());
		} else {
			_logic.createOperationsForMonth(daytypeChooser.getSelectionModel().getSelectedItem(),
					monthChooser.getSelectionModel().getSelectedIndex() + 1);
		}

		loadOperations();
	}

	private void addRideToOperation(IRouteRide ride) {
		IRouteRide selectedRide = ride;
		IOperation selectedOperation = operationTable.getSelectionModel().getSelectedItem();

		if (selectedRide == null) {
			selectedRide = routeRideTable.getSelectionModel().getSelectedItem();
		}

		_logic.addRideToOperation(selectedRide, selectedOperation);
	}

	private void removeRideFromOperation(IRouteRide ride) {
		IRouteRide selectedRide = ride;
		IOperation selectedOperation = operationTable.getSelectionModel().getSelectedItem();

		_logic.removeRideFromOperation(selectedRide, selectedOperation);
	}

	private void deleteOperation(IOperation oper) {
		_logic.deleteOperation(oper);
	}
}
