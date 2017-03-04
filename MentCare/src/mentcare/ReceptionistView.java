package mentcare;

import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.text.*;



public class ReceptionistView extends Application {
	
	static Stage window;
	static Button logoutButton = new Button("Log Out");
	
	static Stage exitwindow;
	static Scene mainmenu;
	static Scene addpatient;
	Label welcome;
	static Label todaysappointmentsl = new Label("Today's appointments:");
	static Label patientidl = new Label("What is the patient's id?");
	static Label firstnamel = new Label("First Name:");
	static Label lastnamel = new Label("Last name:");
	static Label birthdatel = new Label("Birthdate:");
	static Label homeaddressl = new Label("Home Address");
	static Label genderl = new Label("Gender:");
	static Label phonenumberl = new Label("Phone Number:");
	static Label diagnosisl = new Label("Diagnosis:");
	static Label ssnl = new Label("SSN: ");
	static Label lastvisitl = new Label("Last Visit Was: ");
	static Label exitconfirmationlabel;
	static Button createappointmentbutton = new Button("Create appointment");
	static Button patientrecordsbutton = new Button ("View/Edit Patient Information");
	static Button patientperscriptionsbutton = new Button ("Patients perscriptions");
	static Button patientsheldbutton = new Button("Institutionalized Patients");
	static Button addpatientbutton = new Button("Click to add a new patient");
	static Button logoutbutton = new Button("Log out");
	static Button diagnosishistorybutton = new Button("Diagnosis History");
	static Button editrecordbutton = new Button("Edit Record");
	//static Button searchbutton = new Button(patientsearch);
	static Button yesbutton = new Button("yes");
	static Button nobutton = new Button("no");
	static Button backbutton = new Button("Back");
	static Button okbutton = new Button("ok");
	static Button cancelbutton = new Button("cancel");
	static Button updatebutton = new Button("Update");
	static Button viewappointments = new Button("View Appointments");
	static String exitconfirmation = "Are you sure you wanted to exit?";

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		window = primaryStage;
		window.setTitle("Receptionist View");
		welcome = new Label("Welcome, Receptionist");
		window.setOnCloseRequest(e -> {
			e.consume();
			confirmExit();
		});
		
		logoutButton.setOnAction(e -> {
			viewMenu vm = new viewMenu();
			try {
				vm.start(window);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(logoutButton, 0, 3);
		grid.add(welcome, 2, 0);
		grid.add(createappointmentbutton, 2, 1);
		grid.add(addpatientbutton, 2, 2);
		grid.add(patientrecordsbutton, 3, 1);
		grid.add(viewappointments, 3, 2);
		
		
		Scene scene1 = new Scene(grid, 500, 500);
		window.setScene(scene1);
		window.show();
	}
	
	private static void confirmExit() {
		exitwindow = new Stage();
		
		exitwindow.initModality(Modality.APPLICATION_MODAL);
		exitwindow.setTitle(exitconfirmation);
		
		exitconfirmationlabel = new Label();
		exitconfirmationlabel.setText(exitconfirmation);
		
		yesbutton.setOnAction(e ->  {
			exitwindow.close();
			exitProgram();
		});
		nobutton.setOnAction(e -> exitwindow.close());
		BorderPane layout = new BorderPane();
		exitconfirmationlabel.setPadding(new Insets(0, 0, 10, 0));
		yesbutton.setPadding(new Insets(5, 30, 5, 30)); nobutton.setPadding(new Insets(5, 30, 5, 30));
		layout.setPadding(new Insets(0, 10, 10, 10));
		layout.setTop(exitconfirmationlabel);
		layout.setLeft(yesbutton);
		layout.setRight(nobutton);
		Scene exit = new Scene(layout, 250, 70);
		exitwindow.setScene(exit);
		exitwindow.show();
	}
	/**
	 * User confirms they want to close the program
	 */
	private static void exitProgram() {	
		window.close();
	}
	
	
}