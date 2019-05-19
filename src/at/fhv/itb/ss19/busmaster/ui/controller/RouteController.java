package at.fhv.itb.ss19.busmaster.ui.controller;

import at.fhv.itb.ss19.busmaster.application.CreateRoute;
import at.fhv.itb.ss19.busmaster.application.ActionButtonTableCell;
import at.fhv.itb.ss19.busmaster.application.CreateRouteRides;
import at.fhv.itb.ss19.busmaster.domain.RouteRide;
import at.fhv.itb.ss19.busmaster.persistence.entities.PathEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.PathStationEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.StationEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Alert.AlertType;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RouteController {
    
    @FXML
    public TableView routeRideList;
    public ComboBox comboBoxPath;
    public CheckBox checkBoxSingleRide;
    public TextField startTimeHour;
    public TextField startTimeMinute;
    public TextField endTimeHour;
    public TextField endTimeMinute;
    public TextField takt;
    public ComboBox comboBoxDayType;
    public TextField capacity;
    public Button buttonCreate;
    public Button buttonSave;
    @FXML
	private TableView<RouteEntity> routeList = new TableView<>();
	@FXML
	private Button routeAdd = new Button();
	@FXML
	private DatePicker startDate = new DatePicker();
	@FXML
	private DatePicker endDate = new DatePicker();
	@FXML
	private TextField routeNumber = new TextField();
	@FXML
	private TableView<StationEntity> stationList = new TableView<>();
	@FXML
	private TableView<PathStationEntity> pathTableView = new TableView<>();
	@FXML
	private TableView<PathStationEntity> retourpathTableView = new TableView<>();
	@FXML
	private TextField searchBar = new TextField();
	@FXML
	private Button buttonStationAdd = new Button();
	@FXML
	private Button buttonRetourStationAdd = new Button();
	@FXML
	private Button buttonInvertToRetour = new Button();
	@FXML
	private Button buttonPathUndo = new Button();
	@FXML
	private Button buttonRetourPathUndo = new Button();
	@FXML
	private Button buttonDeleteRetourPath = new Button();
	@FXML
	private Button buttonPathDown = new Button();
	@FXML
	private Button buttonPathUp = new Button();
	@FXML
	private Button buttonRetourPathDown = new Button();
	@FXML
	private Button buttonRetourPathUp = new Button();
	@FXML
	public Label routeAlert;
	@FXML
	private TextField txtFieldPathName = new TextField();
	@FXML
	private TextField txtFieldRetourPathName = new TextField();
	@FXML
	public Button editRouteButton = new Button();
	@FXML
	public TitledPane editRoutePane = new TitledPane();
	@FXML
	public Button editRouteSaveButton = new Button();
	@FXML
	public DatePicker editRouteStartDate = new DatePicker();
	@FXML
	public DatePicker editRouteEndDate = new DatePicker();
	@FXML
	public Label editRouteNumber = new Label();
	@FXML
	public Label editRouteAlert = new Label();
	@FXML
    public Label editRoutePaneAlert = new Label();

	private ObservableList<RouteEntity> _routes = FXCollections.observableArrayList();
	private ObservableList<StationEntity> _stations = FXCollections.observableArrayList();
	private ObservableList<StationEntity> _sortedStations = FXCollections.observableArrayList();
	private ObservableList<PathStationEntity> _pathStations = FXCollections.observableArrayList();
	private ObservableList<PathStationEntity> _retourpathStations = FXCollections.observableArrayList();
	private ObservableList<RouteRide> _routeRides = FXCollections.observableArrayList();

	private ConcurrentHashMap<PathEntity, List<PathStationEntity>> _unDoPathStationMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap<PathEntity, List<PathStationEntity>> _unDoRetourPathStationMap = new ConcurrentHashMap<>();

	private PathStationEntity selectedPathStationEntity = new PathStationEntity();
	private PathEntity path = new PathEntity();
	private PathEntity retourPath = new PathEntity();
	private RouteEntity selectedRouteEntity;

	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {
		// DbFacade facade = DbFacade.getInstance();
		CreateRoute _logic = new CreateRoute();
        CreateRouteRides createRouteRides = new CreateRouteRides();
		routeList.setItems(_routes);
		stationList.setItems(_stations);
		pathTableView.setItems(_pathStations);
		retourpathTableView.setItems(_retourpathStations);
		pathTableView.setEditable(true);
		retourpathTableView.setEditable(true);
		routeRideList.setItems(_routeRides);

		path = null;
		retourPath = null;

		// labelPathName.

		TableColumn<RouteEntity, String> routeNrCol = new TableColumn<RouteEntity, String>("Route Nr.");
		TableColumn<RouteEntity, String> validFromCol = new TableColumn<RouteEntity, String>("valid from");
		TableColumn<RouteEntity, String> validToCol = new TableColumn<RouteEntity, String>("valid to");

		TableColumn<RouteRide, String>  pathCol = new TableColumn<>("Path");
        TableColumn<RouteRide, String>  startTimeCol = new TableColumn<>("Start Time");
        TableColumn<RouteRide, String>  endTimeCol = new TableColumn<>("End Time");
        TableColumn<RouteRide, String>  dayTypeCol = new TableColumn<>("Day Type");
        TableColumn<RouteRide, String>  capacityCol = new TableColumn<>("Capacity");

		TableColumn<StationEntity, String> stationNameCol = new TableColumn<>("Name");
		TableColumn<StationEntity, String> stationShortNameCol = new TableColumn<>("Contraction");

		TableColumn<PathStationEntity, String> pathStationPreviousDistanceCol = new TableColumn<>(
				"Previous Distance");
		TableColumn<PathStationEntity, String> pathStationNameCol = new TableColumn<>("Name");
		TableColumn<PathStationEntity, String> pathStationPreviousTimeCol = new TableColumn<>(
				"Previous Time");
		TableColumn<PathStationEntity, Button> pathStationRemove = new TableColumn<>();

		TableColumn<PathStationEntity, String> retourpathStationPreviousDistance = new TableColumn<PathStationEntity, String>(
				"Previous Distance");
		TableColumn<PathStationEntity, String> retourpathStationName = new TableColumn<PathStationEntity, String>(
				"Name");
		TableColumn<PathStationEntity, String> retourpathStationPreviousTimeCol = new TableColumn<PathStationEntity, String>(
				"Previous Time");
		TableColumn<PathStationEntity, Button> retourPathStationRemove = new TableColumn<>();

		// SettingValues for Columns
		routeNrCol.setCellValueFactory(new PropertyValueFactory<RouteEntity, String>("routeNumber"));
		validFromCol.setCellValueFactory(new PropertyValueFactory<RouteEntity, String>("validFrom"));
		validToCol.setCellValueFactory(new PropertyValueFactory<RouteEntity, String>("validTo"));

		stationNameCol.setCellValueFactory(new PropertyValueFactory<StationEntity, String>("stationName"));
		stationShortNameCol.setCellValueFactory(new PropertyValueFactory<StationEntity, String>("shortName"));

		pathCol.setCellValueFactory(new PropertyValueFactory<>("_path"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("_startingTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("_endingTime"));
        dayTypeCol.setCellValueFactory(new PropertyValueFactory<>("dayType"));
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));

		pathStationPreviousDistanceCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDistanceFromPrevious())));
		pathStationPreviousTimeCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTimeFromPrevious())));
		pathStationNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getStation().getStationName())));

		// Part of editable TableCells, so that the onEdit function on the cells will
		// get called
		pathStationPreviousDistanceCol.setCellFactory(TextFieldTableCell.forTableColumn());
		pathStationPreviousTimeCol.setCellFactory(TextFieldTableCell.forTableColumn());

		// Setting the function for the Remove Button on each row
		pathStationRemove.setCellFactory(
				ActionButtonTableCell.<PathStationEntity>forTableColumn("Remove", (PathStationEntity pathSE) -> {
					// If there is no Key in the Map with the current path, create one:
					if (!_unDoPathStationMap.containsKey(path)) {
						List<PathStationEntity> list = new ArrayList<PathStationEntity>();
						_unDoPathStationMap.put(path, list);
					}
					// Add the removed PathStation to the List which is the Value of the Key
					_unDoPathStationMap.get(path).add(pathSE);
					_logic.deletePathStations(pathSE);
					pathTableView.getItems().remove(pathSE);
					Collections.sort(_pathStations);
					return pathSE;
				}));

		// SettingValues for Columns
		retourpathStationPreviousDistance.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDistanceFromPrevious())));
		retourpathStationName.setCellValueFactory(cellData -> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getStation().getStationName())));
		retourpathStationPreviousTimeCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTimeFromPrevious())));

		// Part of editable TableCells, so that the onEdit function on the cells will
		// get called
		retourpathStationPreviousDistance.setCellFactory(TextFieldTableCell.forTableColumn());
		retourpathStationPreviousTimeCol.setCellFactory(TextFieldTableCell.forTableColumn());

		retourPathStationRemove.setCellFactory(
				ActionButtonTableCell.<PathStationEntity>forTableColumn("Remove", (PathStationEntity pathSE) -> {
					// Add all removed PathStationEntities to a temp List. Until Save is confirmed.
					// Then Delete the removed ones.
					if (!_unDoRetourPathStationMap.containsKey(retourPath)) {
						List<PathStationEntity> list = new ArrayList<PathStationEntity>();
						_unDoRetourPathStationMap.put(retourPath, list);
					}
					_unDoRetourPathStationMap.get(retourPath).add(pathSE);
					_logic.deletePathStations(pathSE);
					retourpathTableView.getItems().remove(pathSE);
					Collections.sort(_retourpathStations);
					return pathSE;
				}));

		// Setting up TableViews
		routeList.getColumns().add(routeNrCol);
		routeList.getColumns().add(validFromCol);
		routeList.getColumns().add(validToCol);

		stationList.getColumns().add(stationNameCol);
		stationList.getColumns().add(stationShortNameCol);

		pathTableView.getColumns().add(pathStationNameCol);
		pathTableView.getColumns().add(pathStationPreviousDistanceCol);
		pathTableView.getColumns().add(pathStationPreviousTimeCol);
		pathTableView.getColumns().add(pathStationRemove);

		retourpathTableView.getColumns().add(retourpathStationName);
		retourpathTableView.getColumns().add(retourpathStationPreviousDistance);
		retourpathTableView.getColumns().add(retourpathStationPreviousTimeCol);
		retourpathTableView.getColumns().add(retourPathStationRemove);

		_routes.addAll(_logic.getAllRoutes());
		_stations.addAll(_logic.getAllStations());

		// Button für das aufklappbare Menü
		editRouteButton.setOnAction(event -> {
			if (editRoutePane.isVisible()) {
				editRoutePane.setVisible(false);
				editRoutePane.setExpanded(false);
				routeList.setDisable(false);
				editRouteButton.setText("Edit Route");
			} else {
				if (routeList.getSelectionModel().isEmpty()) {
					editRoutePaneAlert.setText("No Route selected!");
				} else {
					selectedRouteEntity = routeList.getSelectionModel().getSelectedItem();
					editRouteNumber.setText(String.valueOf(selectedRouteEntity.getRouteNumber()));
					editRouteStartDate.setValue(selectedRouteEntity.getValidFrom().toLocalDate());
					editRouteEndDate.setValue(selectedRouteEntity.getValidTo().toLocalDate());
					editRoutePane.setVisible(true);
					editRoutePane.setExpanded(true);
					routeList.setDisable(true);
					editRouteButton.setText("Cancel");
					editRoutePaneAlert.setText("");
					editRoutePane.setOnMouseClicked(event1 -> {
						editRoutePane.setExpanded(true);
					});
				}

			}
		});

		// Button um Route zu spiechern
		editRouteSaveButton.setOnAction(event -> {
			if (editRouteStartDate.getValue().isAfter(editRouteEndDate.getValue())) {
				editRouteAlert.setText("Start date should not be after end date!");
			} else {
				editRouteAlert.setText(_logic.editRoute(selectedRouteEntity,
						Date.valueOf(editRouteStartDate.getValue()), Date.valueOf(editRouteEndDate.getValue())));
				if (editRouteAlert.getText().equals("Saved")) {
					editRoutePane.setVisible(false);
					editRouteButton.setText("Edit Route");
					editRoutePaneAlert.setText("Saved!");
					routeList.setDisable(false);
					_routes.clear();
					_routes.addAll(_logic.getAllRoutes());
				}

			}
		});

		// Button um neue Route zu speichern
		routeAdd.setOnAction(event -> {
			if (startDate.getValue().isAfter(endDate.getValue())) {
				routeAlert.setText("Start date should not be after end date!");
			} else if (startDate.getValue().isBefore(LocalDate.now())) {
				routeAlert.setText("Start date have to be after current date!");
			} else if (!routeNumber.getText().matches("([0-9]+)")) {
				routeAlert.setText("Only numbers in RouteNumber!");
			} else {
				if (_logic.saveNewRoute(Integer.parseInt(routeNumber.getText()), Date.valueOf(startDate.getValue()),
						Date.valueOf(endDate.getValue())).equals("Saved!")) {
					routeAlert.setText("Saved!");
					_routes.clear();
					_routes.addAll(_logic.getAllRoutes());
				} else {
					routeAlert.setText("Conflict! Not saved!");
				}
			}
		});

		// This will be called after clicking a Route in TableView
		routeList.getSelectionModel().selectedItemProperty().addListener(newSelection -> {
			if (newSelection != null) {
				// Clear the UI Table Entries in the Paths
				_pathStations.clear();
				_retourpathStations.clear();
				RouteEntity selectedRoute = routeList.getSelectionModel().getSelectedItem();
				Set<PathEntity> pathSet = selectedRoute.getPaths();
				path = null;
				retourPath = null;
				Iterator<PathEntity> iter = pathSet.iterator();
				// Get the paths from the Set of the RouteEntity
				// and set them on the correct Table (pathTable / retourPathTable)
				while (iter.hasNext()) {
					PathEntity pathIter = (PathEntity) iter.next();
					if (pathIter.isRetour()) {
						retourPath = pathIter;
					} else {
						path = pathIter;
					}
				}
				// Write the Pathdescription in the TextField
				txtFieldPathName.setText(path.getPathDescription());
				if (retourPath != null) {
					txtFieldRetourPathName.setText(retourPath.getPathDescription());
				}
				_pathStations.addAll(_logic.getPathStationsByPath(path));
				_retourpathStations.addAll(_logic.getPathStationsByPath(retourPath));
				Collections.sort(_pathStations);
				Collections.sort(_retourpathStations);
			} else {
				_pathStations.clear();
				_retourpathStations.clear();
			}
		});

		// Searching for Stations inside the Stations-TableView
		routeList.getSelectionModel().selectedItemProperty().addListener(newSelection -> {
			if (newSelection != null) {
				editRoutePaneAlert.setText("");
				_pathStations.clear();
				_retourpathStations.clear();
				RouteEntity selectedRoute = routeList.getSelectionModel().getSelectedItem();
				if(selectedRoute == null)return;
				Set<PathEntity> pathSet = selectedRoute.getPaths();
				path = null;
				retourPath = null;
				Iterator<PathEntity> iter = pathSet.iterator();
				while (iter.hasNext()) {
					PathEntity pathIter = (PathEntity) iter.next();
					if (pathIter.isRetour()) {
						retourPath = pathIter;
					} else {
						path = pathIter;
					}
				}

				_pathStations.addAll(_logic.getPathStationsByPath(path));
				_retourpathStations.addAll(_logic.getPathStationsByPath(retourPath));
				Collections.sort(_pathStations);
				Collections.sort(_retourpathStations);
			} else {
				_pathStations.clear();
				_retourpathStations.clear();
			}
		});

		searchBar.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				_sortedStations.clear();
				if (newValue.equals("")) {
					stationList.setItems(_stations);
				} else {
					for (StationEntity station : _stations) {
						if (station.getStationName().toLowerCase().startsWith(newValue.toLowerCase())) {
                            _sortedStations.add(station);
                        }
                        else if (station.getShortName().toLowerCase().startsWith(newValue.toLowerCase())) {
                            _sortedStations.add(station);
                        }
						stationList.setItems(_sortedStations);
					}
				}
			}
		});

		// Add the Station to the Path
		buttonStationAdd.setOnAction(event -> {
			// To add the chosen Station to the selected Path
			if (checkInput()) {
				selectedPathStationEntity.setPath(path);
				selectedPathStationEntity.setStation(stationList.getSelectionModel().getSelectedItem());
				selectedPathStationEntity.setPositionOnPath(_pathStations.size() + 1);
				_pathStations.clear();
				_logic.addPathStation(selectedPathStationEntity);
				_pathStations.addAll(_logic.getPathStationsByPath(path));
				Collections.sort(_pathStations);
			}
		});

		// Add the Station to the RetourPath
		buttonRetourStationAdd.setOnAction(event -> {
			if (checkInput()) {
				// Check if there is a retourPath existing, if not create one
				if (retourPath == null) {
					PathEntity newRetourPath = new PathEntity();
					newRetourPath.setRetour(true);
					newRetourPath.setRoute(routeList.getSelectionModel().getSelectedItem());
					_logic.savePath(newRetourPath);
					retourPath = newRetourPath;
				}

				PathStationEntity newSelectedPathStationEntity = new PathStationEntity();
				newSelectedPathStationEntity.setPath(retourPath);
				newSelectedPathStationEntity.setStation(stationList.getSelectionModel().getSelectedItem());
				newSelectedPathStationEntity.setPositionOnPath(_retourpathStations.size() + 1);
				_retourpathStations.clear();
				selectedPathStationEntity = newSelectedPathStationEntity;
				_logic.addPathStation(selectedPathStationEntity);
				_retourpathStations.addAll(_logic.getPathStationsByPath(retourPath));

				RouteEntity selectedRoute = routeList.getSelectionModel().getSelectedItem();
				selectedRoute.getPaths().add(retourPath);
				Collections.sort(_retourpathStations);
			}
		});

		// Button to change the Position of Station inside the Path
		buttonPathDown.setOnAction(event -> {
			selectedPathStationEntity = pathTableView.getSelectionModel().getSelectedItem();
			int index = pathTableView.getSelectionModel().getSelectedIndex();
			if (index < _pathStations.size() - 1) {
				if (_pathStations.get(index + 1) != null && (index + 1) < _pathStations.size()) {
					int temp = _pathStations.get(index + 1).getPositionOnPath();
					_logic.changePositionOnPath(_pathStations.get(index + 1),
							selectedPathStationEntity.getPositionOnPath());
					_logic.changePositionOnPath(selectedPathStationEntity, temp);
					Collections.sort(_pathStations);
				}
			}
		});

		buttonPathUp.setOnAction(event -> {
			selectedPathStationEntity = pathTableView.getSelectionModel().getSelectedItem();
			int index = pathTableView.getSelectionModel().getSelectedIndex();
			if (index > 0) {
				if (_pathStations.get(index - 1) != null && (index - 1) < _pathStations.size()) {
					int temp = _pathStations.get(index - 1).getPositionOnPath();
					_logic.changePositionOnPath(_pathStations.get(index - 1),
							selectedPathStationEntity.getPositionOnPath());
					_logic.changePositionOnPath(selectedPathStationEntity, temp);
					Collections.sort(_pathStations);
				}
			}
		});

		buttonRetourPathDown.setOnAction(event -> {
			selectedPathStationEntity = retourpathTableView.getSelectionModel().getSelectedItem();
			int index = retourpathTableView.getSelectionModel().getSelectedIndex();
			if (index < _retourpathStations.size() - 1) {
				if (_retourpathStations.get(index + 1) != null && (index + 1) < _retourpathStations.size()) {
					int temp = _retourpathStations.get(index + 1).getPositionOnPath();
					_logic.changePositionOnPath(_retourpathStations.get(index + 1),
							selectedPathStationEntity.getPositionOnPath());
					_logic.changePositionOnPath(selectedPathStationEntity, temp);
					Collections.sort(_retourpathStations);
				}
			}
		});

		buttonRetourPathUp.setOnAction(event -> {
			selectedPathStationEntity = retourpathTableView.getSelectionModel().getSelectedItem();
			int index = retourpathTableView.getSelectionModel().getSelectedIndex();
			if (index > 0) {
				if (_retourpathStations.get(index - 1) != null && (index - 1) < _retourpathStations.size()) {
					int temp = _retourpathStations.get(index - 1).getPositionOnPath();
					_logic.changePositionOnPath(_retourpathStations.get(index - 1),
							selectedPathStationEntity.getPositionOnPath());
					_logic.changePositionOnPath(selectedPathStationEntity, temp);
					Collections.sort(_retourpathStations);
				}
			}
		});

		// Get the Stations from the path and add them mirrored to the retourpath
		buttonInvertToRetour.setOnAction(event -> {
			Collections.sort(_pathStations);
			if (retourPath == null) {
				PathEntity newRetourPath = new PathEntity();
				newRetourPath.setRetour(true);
				newRetourPath.setRoute(routeList.getSelectionModel().getSelectedItem());
				_logic.savePath(newRetourPath);
				retourPath = newRetourPath;
			}
			
			for (PathStationEntity pathStation : _retourpathStations) {
				_logic.deletePathStations(pathStation);
			}
			
			_retourpathStations.clear();
			int i = _pathStations.size();
			
			

			for (PathStationEntity pathStationEntity : _pathStations) {
				PathStationEntity invertedPathStation = pathStationEntity;
				invertedPathStation.setPath(retourPath);
				invertedPathStation.setPositionOnPath(i);
				_logic.addPathStation(invertedPathStation);
				RouteEntity selectedRoute = routeList.getSelectionModel().getSelectedItem();
				selectedRoute.getPaths().add(retourPath);
				i--;
			}
			_retourpathStations.addAll(_logic.getPathStationsByPath(retourPath));
			Collections.sort(_retourpathStations);
		});

		// Undo the remove Operations which the User did during the Session
		buttonPathUndo.setOnAction(event -> {
			if (_unDoPathStationMap.get(path) != null) {
				int i = _unDoPathStationMap.get(path).size();
				if (i > 0) {
					_logic.addPathStation(_unDoPathStationMap.get(path).get(i - 1));
					_pathStations.add(_unDoPathStationMap.get(path).get(i - 1));
					_unDoPathStationMap.get(path).remove(i - 1);
					Collections.sort(_pathStations);
				}
			}
		});

		buttonRetourPathUndo.setOnAction(event -> {
			if (_unDoRetourPathStationMap.get(retourPath) != null) {
				int i = _unDoRetourPathStationMap.get(retourPath).size();
				if (i > 0) {
					_logic.addPathStation(_unDoRetourPathStationMap.get(retourPath).get(i - 1));
					_retourpathStations.add(_unDoRetourPathStationMap.get(retourPath).get(i - 1));
					_unDoRetourPathStationMap.get(retourPath).remove(i - 1);
					Collections.sort(_retourpathStations);
				}
			}
		});

		// Function to save the entried Data into Columns of the Tables:
		pathStationPreviousDistanceCol.setOnEditCommit(event -> {
			_logic.changePreviousDistance(pathTableView.getSelectionModel().getSelectedItem(),
					Integer.valueOf(event.getNewValue()));

		});

		pathStationPreviousTimeCol.setOnEditCommit(event -> {
			_logic.changePreviousTime(pathTableView.getSelectionModel().getSelectedItem(),
					Integer.valueOf(event.getNewValue()));

		});

		retourpathStationPreviousDistance.setOnEditCommit(event -> {
			_logic.changePreviousDistance(retourpathTableView.getSelectionModel().getSelectedItem(),
					Integer.valueOf(event.getNewValue()));

		});

		retourpathStationPreviousTimeCol.setOnEditCommit(event -> {
			_logic.changePreviousTime(retourpathTableView.getSelectionModel().getSelectedItem(),
					Integer.valueOf(event.getNewValue()));

		});

		// Delete All Stations from RetourPath
		buttonDeleteRetourPath.setOnAction(event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.headerTextProperty().set("Delete Retour Path ?");
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				for (PathStationEntity pathStation : _retourpathStations) {
					if (!_unDoRetourPathStationMap.containsKey(retourPath)) {
						List<PathStationEntity> list = new ArrayList<PathStationEntity>();
						_unDoRetourPathStationMap.put(retourPath, list);
					}
					_unDoRetourPathStationMap.get(retourPath).add(pathStation);
					_logic.deletePathStations(pathStation);
				}
				_retourpathStations.clear();
			}

			if (alert.getResult() == ButtonType.NO || alert.getResult() == ButtonType.CANCEL) {
				return;
			}
		});

		// Edit the PathDescription/PathName of a path by writing into the TextField
		txtFieldPathName.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean outOfFocus, Boolean onFocus) {
				if (outOfFocus) {
					_logic.changePathDescription(path, txtFieldPathName.getText());
				}
			}
		});

		txtFieldRetourPathName.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean outOfFocus, Boolean onFocus) {
				if (outOfFocus) {
					_logic.changePathDescription(retourPath, txtFieldRetourPathName.getText());
				}
			}
		});
	}

	// Check if a Route and/or a Station is selected
	public Boolean checkInput() {
		if (path == null) {
			Alert alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
			alert.headerTextProperty().set("Please select a Route");
			alert.showAndWait();
			return false;
		} else if (stationList.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
			alert.headerTextProperty().set("Please select a Station");
			alert.showAndWait();
			return false;
		}

		return true;
	}
}
