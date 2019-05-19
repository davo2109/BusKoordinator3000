package at.fhv.itb.ss19.busmaster.ui.controller;

import java.util.List;

import at.fhv.itb.ss19.busmaster.application.ActionButtonTableCell;
import at.fhv.itb.ss19.busmaster.domain.ConflictingOperation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ConflictSceneController {
	@FXML
	private TableView<ConflictingOperation> conflictTable = new TableView<>();

	private ObservableList<ConflictingOperation> _conflicts = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		conflictTable.setItems(_conflicts);
		TableColumn<ConflictingOperation, String> dateCol = new TableColumn<>("date");
		TableColumn<ConflictingOperation, String> operIdCol = new TableColumn<>("tour");
		TableColumn<ConflictingOperation, String> causeCol = new TableColumn<>("cause");
		TableColumn<ConflictingOperation, Button> removeCol = new TableColumn<>("");

		dateCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDate())));
		operIdCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOperationId())));
		causeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCause()));

		removeCol.setCellFactory(
				ActionButtonTableCell.<ConflictingOperation>forTableColumn("Remove", (ConflictingOperation oper) -> {
					conflictTable.getItems().remove(oper);
					return oper;
				}));

		conflictTable.getColumns().add(operIdCol);
		conflictTable.getColumns().add(dateCol);
		conflictTable.getColumns().add(causeCol);
		conflictTable.getColumns().add(removeCol);
	}

	public void setConflicts(List<ConflictingOperation> conflicts) {
		_conflicts.clear();
		_conflicts.addAll(conflicts);
	}
}
