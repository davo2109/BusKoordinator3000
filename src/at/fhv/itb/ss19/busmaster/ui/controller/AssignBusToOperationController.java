package at.fhv.itb.ss19.busmaster.ui.controller;

import at.fhv.itb.ss19.busmaster.application.AssignBusToOperation;
import at.fhv.itb.ss19.busmaster.domain.MonthEnum;
import at.fhv.itb.ss19.busmaster.domain.security.IOperation;
import at.fhv.itb.ss19.busmaster.domain.security.IRouteRide;
import at.fhv.itb.ss19.busmaster.persistence.entities.BusEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class AssignBusToOperationController {
	@FXML
	private TableView<IOperation> operationTable = new TableView<>();
	@FXML
	private TableView<IRouteRide> operationDetailTable = new TableView<>();
	@FXML
	private TableView<BusEntity> busTable = new TableView<>();
	@FXML
	private DatePicker dayPicker = new DatePicker();
	@FXML
	private ChoiceBox<MonthEnum> monthChooser = new ChoiceBox<>();
	@FXML
	private Label assignedBusLabel = new Label();
	@FXML
	private Button unassignBusFromOperation = new Button();
	@FXML
	private Button assignBusToOperation = new Button();
	@FXML
	private CheckBox planningUnitDay = new CheckBox();
	@FXML
	private Button saveChanges = new Button();
	@FXML
	private Button cancelChanges = new Button();
	@FXML
	Button conflictButton = new Button();

	private TableColumn<IOperation, String> operationTableSortColumn;
	private TableColumn<IRouteRide, String> operationDetailTableSortColumn;
	private TableViewSelectionModel<IRouteRide> operationDetailTableSelectionModel;

	private TableColumn<IOperation, String> busCol = new TableColumn<>("assigned bus");

	private AssignBusToOperation _logic = new AssignBusToOperation();
	private ObservableList<BusEntity> _busses = FXCollections.observableArrayList();
	private ObservableList<IOperation> _operations = FXCollections.observableArrayList();
	private ObservableList<IRouteRide> _routeRides = FXCollections.observableArrayList();

	private static final String NOBUSASSIGNED = "no bus assigned";

	@FXML
	private void initialize() {
		assignedBusLabel.setText(NOBUSASSIGNED);

		saveChanges.setDisable(!_logic.isAssignmentChanged());
		cancelChanges.setDisable(!_logic.isAssignmentChanged());

		conflictButton.managedProperty().bind(conflictButton.visibleProperty());
		conflictButton.setVisible(false);

		planningUnitDay.setSelected(false);
		dayPicker.disableProperty().bind(planningUnitDay.selectedProperty().not());
		monthChooser.disableProperty().bind(planningUnitDay.selectedProperty());

		monthChooser.getItems().setAll(MonthEnum.values());
		monthChooser.getSelectionModel().select(LocalDate.now().getMonthValue());

		busTable.setItems(_busses);
		operationTable.setItems(_operations);
		operationDetailTable.setItems(_routeRides);

		TableColumn<BusEntity, String> makeCol = new TableColumn<>("Make");
		TableColumn<BusEntity, String> modelCol = new TableColumn<>("model");
		TableColumn<BusEntity, String> licenceCol = new TableColumn<>("license plate");
		TableColumn<BusEntity, String> capStandingCol = new TableColumn<>("standing cap");
		TableColumn<BusEntity, String> capSeatsCol = new TableColumn<>("seat cap");
		TableColumn<BusEntity, String> capSumCol = new TableColumn<>("full cap");

		makeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMake()));
		modelCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel()));
		licenceCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLicenceNumber()));
		capStandingCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStandPlaces())));
		capSeatsCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSeatPlaces())));
		capSumCol.setCellValueFactory(cellData -> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getStandPlaces() + cellData.getValue().getSeatPlaces())));

		busTable.getColumns().add(makeCol);
		busTable.getColumns().add(modelCol);
		busTable.getColumns().add(licenceCol);
		busTable.getColumns().add(capStandingCol);
		busTable.getColumns().add(capSeatsCol);
		busTable.getColumns().add(capSumCol);

		_busses.addAll(_logic.getAllBusses());

		TableColumn<IOperation, String> typeCol = new TableColumn<>("Type of day");
		TableColumn<IOperation, String> nameCol = new TableColumn<>("Name");
		TableColumn<IOperation, String> stateCol = new TableColumn<>("State");

		typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDayType().toString()));
		nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		busCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBusLicence()));
		stateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

		operationTable.getColumns().add(nameCol);
		operationTable.getColumns().add(typeCol);
		operationTable.getColumns().add(stateCol);
		operationTableSortColumn = nameCol;
		operationTable.getSortOrder().add(operationTableSortColumn);

		TableColumn<IRouteRide, String> routeCol = new TableColumn<>("route");
		TableColumn<IRouteRide, String> startCol = new TableColumn<>("starttime");
		TableColumn<IRouteRide, String> neededCapCol = new TableColumn<>("needed cap");

		routeCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRoute().getRouteNumber())));
		startCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getStartTime().getStartTime().toString()));
		neededCapCol.setCellValueFactory(cellData -> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getStartTime().getRequiredCapacity())));

		operationDetailTable.getColumns().add(routeCol);
		operationDetailTable.getColumns().add(startCol);
		operationDetailTable.getColumns().add(neededCapCol);
		operationDetailTableSortColumn = startCol;
		operationDetailTable.getSortOrder().add(operationDetailTableSortColumn);
		operationDetailTableSelectionModel = operationDetailTable.getSelectionModel();
		operationDetailTable.setSelectionModel(null);

		initSelectionListenersAndActionHandlers();
	}

	private void initSelectionListenersAndActionHandlers() {

		dayPicker.valueProperty().addListener(event -> {
			pickDay();
		});

		monthChooser.valueProperty().addListener(new ChangeListener<MonthEnum>() {
			@Override
			public void changed(ObservableValue<? extends MonthEnum> selectedProperty, MonthEnum oldValue,
					MonthEnum newValue) {
				monthChooser.valueProperty().removeListener(this);
				changeMonth(oldValue);
				monthChooser.valueProperty().addListener(this);
			}
		});

		operationTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IOperation>() {

			@Override
			public void changed(ObservableValue<? extends IOperation> selectedProperty, IOperation oldValue,
					IOperation newValue) {
				operationSelectionChanged(newValue);
			}
		});

		_operations.addListener(new ListChangeListener<IOperation>() {
			@Override
			public void onChanged(Change<? extends IOperation> c) {
				busTable.getSelectionModel().clearSelection();
				_operations.forEach(oper -> {
					if (oper.getBus() != null) {
						busTable.getSelectionModel().select(oper.getBus());
					}
				});
			}
		});

		planningUnitDay.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> selectedProperty, Boolean oldValue,
					Boolean newValue) {
				selectedProperty.removeListener(this);
				changePlanningUnit(oldValue);
				selectedProperty.addListener(this);
			}
		});

		assignBusToOperation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				assignBusToOperation();
			}
		});

		unassignBusFromOperation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				unassignBusFromOperation();
			}
		});

		saveChanges.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveChanges();
			}
		});

		cancelChanges.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				cancelAssignments();
			}
		});

		conflictButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				showConflicts();
			}
		});
	}

	private void assignBusToOperation() {
		BusEntity bus = busTable.getSelectionModel().getSelectedItem();
		if (bus != null) {
			if (!_logic.assignBusToOperation(bus, operationTable.getSelectionModel().getSelectedItem(),
					planningUnitDay.isSelected())) {
				conflictButton.setVisible(true);
			} else {
				conflictButton.setVisible(false);
			}
			assignedBusLabel.setText(busTable.getSelectionModel().getSelectedItem().getLicenceNumber());
			setSaveCancelButton();
			reloadOperations();
		}
	}

	private void unassignBusFromOperation() {
		_logic.unassignBusFromOperation(operationTable.getSelectionModel().getSelectedItem(),
				planningUnitDay.isSelected());

		assignedBusLabel.setText(NOBUSASSIGNED);
		setSaveCancelButton();
		reloadOperations();
	}

	private void showConflicts() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../scenes/ConflictScene.fxml"));

			ConflictSceneController controller = new ConflictSceneController();
			controller.setConflicts(_logic.getConflicts());
			fxmlLoader.setController(controller);
			Parent root = (Parent) fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cancelAssignments() {
		_logic.cancelAssignments();
		_operations.clear();
		if (planningUnitDay.isSelected()) {
			_operations.addAll(_logic.getOperationsByDate(dayPicker.getValue()));
		} else {
			loadOperationsPerMonth();
		}

		setSaveCancelButton();
		conflictButton.setVisible(false);
	}

	private void saveChanges() {
		_logic.saveAssignments();
		reloadOperations();
		setSaveCancelButton();
	}

	private void changePlanningUnit(Boolean oldValue) {
		if (_logic.isAssignmentChanged()) {
			String dialogText = "Changing the planning unit will result in a loss of unsaved assignment data. Do you want to continue?";
			Optional<ButtonType> result = showDecisionDialog(dialogText);

			if (result.get() == ButtonType.OK) {
				cancelAssignments();
			} else {
				planningUnitDay.setSelected(oldValue);
			}
		}
		if (!_logic.isAssignmentChanged()) {
			if (!planningUnitDay.isSelected()) {
				operationTable.getColumns().remove(busCol);
				loadOperationsPerMonth();
			}
		}
	}

	private void pickDay() {
		if (!operationTable.getColumns().contains(busCol)) {
			operationTable.getColumns().add(busCol);
		}
		_operations.clear();
		_operations.addAll(_logic.getOperationsByDate(dayPicker.getValue()));
	}

	private void changeMonth(MonthEnum oldValue) {
		if (_logic.isAssignmentChanged()) {
			String dialogText = "Changing the planning month will result in a loss of unsaved assignment data. Do you want to continue?";
			Optional<ButtonType> result = showDecisionDialog(dialogText);

			if (result.get() == ButtonType.OK) {
				cancelAssignments();

			} else {
				monthChooser.getSelectionModel().select(oldValue);
				return;
			}
		}

		loadOperationsPerMonth();
	}

	private void setSaveCancelButton() {
		saveChanges.setDisable(!_logic.isAssignmentChanged());
		cancelChanges.setDisable(!_logic.isAssignmentChanged());
	}

	private void loadOperationsPerMonth() {
		_operations.clear();
		_operations.addAll(_logic.getGroupedOperationsOfMonth(monthChooser.getSelectionModel().getSelectedIndex() + 1));
		operationTable.getSortOrder().add(operationTableSortColumn);
		operationTableSortColumn.setSortable(true);
	}

	private void reloadOperations() {
		IOperation selectedOperation = operationTable.getSelectionModel().getSelectedItem();
		_operations.clear();
		_operations.addAll(_logic.reloadOperations(!planningUnitDay.isSelected()));
		operationTable.getSelectionModel().select(selectedOperation);
		operationTable.getSortOrder().add(operationTableSortColumn);
		operationTableSortColumn.setSortable(true);
	}

	private Optional<ButtonType> showDecisionDialog(String text) {
		Alert warning = new Alert(AlertType.CONFIRMATION);
		warning.setTitle("Confirmation Dialog");
		warning.setHeaderText("assignment loss");
		warning.setContentText(text);

		return warning.showAndWait();
	}
	
	private void operationSelectionChanged(IOperation newValue) {
		if (newValue != null) {
			_routeRides.clear();
			BusEntity assignedBus = newValue.getBus();
			_routeRides.addAll(newValue.getRouteRides());

			assignedBusLabel.setText(assignedBus != null ? assignedBus.getLicenceNumber() : NOBUSASSIGNED);

			operationDetailTable.setSelectionModel(operationDetailTableSelectionModel);
			operationDetailTable.getSortOrder().add(operationDetailTableSortColumn);
			operationDetailTableSortColumn.setSortable(true);
			operationDetailTable.setSelectionModel(null);
		}
	}
}
