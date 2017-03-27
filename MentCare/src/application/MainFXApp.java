package application;
import controller.mainViewController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class MainFXApp extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {

			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));
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
		launch(args);

	}
}
