package at.fhv.itb.ss19.busmaster;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Read file fxml and draw interface.
			Parent root = FXMLLoader.load(getClass().getResource("ui/scenes/MainScene.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add("ui/css/dark.css");
			primaryStage.setTitle("BusCoordinator3000");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("ui/resources/minibus_grey.png")));
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        Platform.exit();
			        System.exit(0);
			    }
			});
		} catch (Exception e) {
			e.printStackTrace();

			Alert allmightyError = new Alert(AlertType.ERROR);
			allmightyError.setTitle("Exception Dialog");
			allmightyError.setHeaderText(e.getClass().getName());
			allmightyError.setContentText(e.getCause().getClass().getName());

			// Create expandable Exception.
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String exceptionText = sw.toString();

			Label label = new Label("The exception stacktrace was:");

			TextArea textArea = new TextArea(exceptionText);
			textArea.setEditable(false);
			textArea.setWrapText(true);

			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);

			GridPane expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(label, 0, 0);
			expContent.add(textArea, 0, 1);

			// Set expandable Exception into the dialog pane.
			allmightyError.getDialogPane().setExpandableContent(expContent);

			allmightyError.showAndWait();
		}
	}
}
