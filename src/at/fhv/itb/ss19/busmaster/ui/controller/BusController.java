package at.fhv.itb.ss19.busmaster.ui.controller;

import at.fhv.itb.ss19.busmaster.persistence.entities.BusEntity;
import at.fhv.itb.ss19.busmaster.persistence.DbFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BusController {

    @FXML
    private TableView<BusEntity> busList = new TableView<>();
    @FXML
    private Button busAdd;
    
    private ObservableList<BusEntity> _busses = FXCollections.observableArrayList();
    
    @FXML
    private void initialize() {
    	DbFacade facade = DbFacade.getInstance();
    	busList.setItems(_busses);
    	
    	TableColumn<BusEntity, String> makeCol = new TableColumn<BusEntity, String>("Marke");
    	TableColumn<BusEntity, String> modelCol = new TableColumn<BusEntity, String>("Modell");
    	TableColumn<BusEntity, String> licenceCol = new TableColumn<BusEntity, String>("Kennzeichen");

		makeCol.setCellValueFactory(new PropertyValueFactory<BusEntity, String>("make"));
		modelCol.setCellValueFactory(new PropertyValueFactory<BusEntity, String>("model"));
		licenceCol.setCellValueFactory(new PropertyValueFactory<BusEntity, String>("licenceNumber"));
		
		busList.getColumns().add(makeCol);
		busList.getColumns().add(modelCol);
		busList.getColumns().add(licenceCol);
    	
    	_busses.addAll(facade.getAllBusses());
    }

}