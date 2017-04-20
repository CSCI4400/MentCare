package application;

import java.sql.Connection;
import controller.mainViewController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
/**
 * Main Entrance point to our application, also contains db connection in variable con.
 */

import javafx.fxml.FXMLLoader;


public class MainFXApp extends Application {


	private static Scene scene;
	public static Connection con = null;
	
	public static Scene getScene(){
		return scene;
	}
	
	/**
	 * Creates the initial main page then calls mainViewController to create tabs.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			con = DBConfig.getConnection();
			
			//CURRENTLY SET TO BYPASS LOGIN
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
			//AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));

			Scene scene = new Scene(root,610,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			// we need to give the controller access to the Main app.
			mainViewController controller = new mainViewController();
			controller.setMain(this);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Connects to database and then launches this class's start function. Uses Model.DBConnection.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}