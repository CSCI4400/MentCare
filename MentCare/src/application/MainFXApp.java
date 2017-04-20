package application;

import java.sql.Connection;
import javafx.application.Application;
import javafx.stage.Stage;
import model.DBConnection;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
/**
 * Main Entrance point to our application, also contains db connection in variable con.
 */

import javafx.fxml.FXMLLoader;


public class MainFXApp extends Application {

	public static Connection con;
	
	/**
	 * Creates the initial main page then calls mainViewController to create tabs.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));
			Scene scene = new Scene(root,610,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Connects to database and then launches this class's start function. Uses Model.DBConnection.
	 */
	public static void main(String[] args) {
		DBConnection idb = new DBConnection();
		Thread t = new Thread(idb);
		t.start();
		launch(args);

	}
}