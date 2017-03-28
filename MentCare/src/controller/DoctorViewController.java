package controller;

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

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Patient;
import model.TimeoutTimer;
import view.DiagnosisHistoryView;
import javafx.scene.control.*;
import javafx.scene.text.*;

/**
 * Stage is the entire application window
 * Scene is the area inside stage that has buttons, etc
 *
 */
public class DoctorViewController extends Application{

	String placeholder = "Placeholder for: ";

	static Patient a = new Patient();
	static boolean patientupdate= false;
	static String pid; //used to store the ID# of the patient whose record is being looked at

	static Label welcome = new Label("Welcome Doctor, " + "xyz");
	static String exitconfirmation = "Are you sure you want to exit?";

	static String patientsearch = "Search";

	static Stage window;
	static Stage exitwindow;
	static Scene mainmenu;
	static Scene addpatient;
	static Label todaysappointmentsl = new Label("Today's Appointments:");
	static Label exitconfirmationlabel;
	static Button createappointmentbutton = new Button("Create Appointment");
	static Button patientrecordsbutton = new Button ("View/Edit Patient Records");
	static Button patientperscriptionsbutton = new Button ("Patients Prescriptions");
	static Button patientsheldbutton = new Button("Institutionalized Patients");
	static Button addpatientbutton = new Button("Add Patient");
	static Button logoutbutton = new Button("Log Out");
	
	static Button searchbutton = new Button(patientsearch);
	static Button yesbutton = new Button("Yes");
	static Button nobutton = new Button("No");
	static Button backbutton = new Button("Back");
	static Button okbutton = new Button("OK");
	static Button cancelbutton = new Button("Cancel");

	static Label deathlabel = new Label("Is the patient deceased?");
	static RadioButton yesdead = new RadioButton("Yes");
	static RadioButton nodead = new RadioButton("No");
	static CheckBox tempDiagnosis = new CheckBox("Diagnosis is temporary");


	//Time out variables
		Point2D prevMousePos = new Point2D(0,0);
		int timeOutDelay = 5;

	public void start(Stage primaryStage) throws Exception {
		//Adds buttons and labels
		window = primaryStage;
		window.setTitle("Doctor View");
		window.setOnCloseRequest(e -> {
			e.consume();
			confirmExit();
		});


		//Configure button actions
		addpatientbutton.setOnAction(e -> addPatient());
		patientrecordsbutton.setOnAction(e-> SearchPatientController.searchPatientDoc(window));

		createappointmentbutton.setOnAction(e -> {
			//Put code here for going to appointment view
		});

		patientsheldbutton.setOnAction(e-> {
			//Put code here for going to institutionalized patients view
		});
		logoutbutton.setOnAction(e -> {
			logout();
			//return to main menu interface
		});


		//Configures layout
		VBox todaysappointmentslayout = new VBox(20);
		todaysappointmentsl.setPadding(new Insets(0, 10, 10, 10));
		todaysappointmentslayout.getChildren().addAll(todaysappointmentsl);

		GridPane mainarea = new GridPane();
		mainarea.setVgap(10); mainarea.setHgap(10);
		mainarea.add(createappointmentbutton, 0, 0);
		mainarea.add(addpatientbutton, 1, 0);
		mainarea.add(patientrecordsbutton, 0, 1);
		mainarea.add(patientsheldbutton, 1, 1);
		mainarea.add(logoutbutton, 0, 2);
		BorderPane layout = new BorderPane();
		welcome.setPadding(new Insets(10, 10, 10, 220));
		welcome.setStyle("-fx-font-weight: bold");
		layout.setPadding(new Insets(15, 15, 15, 15));
		layout.setTop(welcome);
		layout.setLeft(todaysappointmentslayout);
		layout.setCenter(mainarea);

		//Display stage
		mainmenu = new Scene(layout, 640, 480);
		window.setScene(mainmenu);
		window.show();

		//Set page to time out after 10 seconds
		TimeoutTimer timeout = new TimeoutTimer(mainarea, window, 10); //This method is overloaded; if you only use two arguments the time defaults
		timeout.start();                                               //to 120 seconds. I'm using 10 seconds to make it easier to demo.

	}
	/**
	 * Called when user wants to add a patient to the system, creates ok and cancel button
	 */
	private static void addPatient() {
		BorderPane addpatientlayout = new BorderPane();
		okbutton.setOnAction(e -> {
			window.setScene(mainmenu); //return to mainmenu
		});

		cancelbutton.setOnAction(e -> window.setScene(mainmenu));
		HBox layout2 = new HBox(20);
		layout2.getChildren().addAll(okbutton, cancelbutton);
		addpatientlayout.setBottom(layout2);
		addpatient = new Scene(addpatientlayout, 640, 480);
		window.setScene(addpatient);
	}
	/**
	 * Called when the user clicks the logout button
	 */
	private static void logout() {
		//log out of system here

		//Currently switches back to the viewMenu, this is only temporary
		try {
			ViewMenuController vMenu = new ViewMenuController();
			vMenu.start(window);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Called when user has clicked upper right x button & confirms
	 */
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
