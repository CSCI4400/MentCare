package application;
import java.sql.Connection;

import controller.mainViewController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.DBConnection;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class MainFXApp extends Application {


	private static Scene scene;
	public static Connection con;
	
	public static Scene getScene(){
		return scene;
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//CURRENTLY SET TO BIPASS LOGIN
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
			//AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));

			Scene scene = new Scene(root,610,420);
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


	public static void main(String[] args) {
		DBConnection idb = new DBConnection();
		Thread t = new Thread(idb);
		t.start();
		launch(args);

	}
}
