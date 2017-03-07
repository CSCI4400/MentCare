package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;



public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		

			Parent root = FXMLLoader.load(getClass().getResource("/view/Search.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			

		}

	public static void main(String[] args) {
		launch(args);

	}
}