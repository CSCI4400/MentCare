package application;
	
import java.sql.Connection;

import javafx.application.Application;
import javafx.stage.Stage;
import model.DBConnection;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	public static Connection con;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DBConnection idb = new DBConnection();
		Thread t = new Thread(idb);
		t.start();
		launch(args);
	}
}
