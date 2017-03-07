package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;


public class Main extends Application {
	private BusinessManager businessManager;
	
	private Stage primaryStage;
	private Scene scene;
	private VBox primaryLayout;
	
	@FXML
	private TextField firstField;
	@FXML
	private TextField lastField;
	@FXML
	private Button addPatientButton;
	@FXML
	private Label statusLabel;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("CSCI 4200 project");
		loadMainWindow();
	}

	private void loadMainWindow() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/application/AddPatients.fxml"));
		try {
			primaryLayout = loader.load();
			scene = new Scene(primaryLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	public void onAddPatient()
	{
		if(businessManager == null)
		{
			businessManager = new BusinessManager("Example Manager");
			try
			{

				businessManager.addDoctor(new Doctor("Aaron", "Griffin", "Everything"));

			}//end try

			catch (SQLException e)
			{

				e.printStackTrace();

			}//end catch

		}//end if statement
	}//end onAddPatient




//		Patient p = new Patient(firstField.getText(), lastField.getText());
//		System.out.println(businessManager.toString());
//		System.out.println(p.toString());
//		businessManager.addPatient(p);
//		statusLabel.setText(firstField.getText() + " " + lastField.getText() + " was added.\nPatients: " + businessManager.countPatients());
	}

