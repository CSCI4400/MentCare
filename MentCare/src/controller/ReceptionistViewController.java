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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.DBConnection;
import model.Patient;
import model.TimeoutTimer;
import javafx.scene.control.*;
import javafx.scene.text.*;



public class ReceptionistViewController extends Application {
	
	static Stage window;
	static Button logoutButton = new Button("Log Out");
	static String patientsearchl = "Search";
	static Patient a = new Patient();
	static String pid;
	
	static Stage exitwindow;
	static Scene mainmenu;
	static Scene addpatient;
	public static Label welcome = new Label("Welcome, " + "ReceptionistName");
	public static Text todaysappointmentsl = new Text("Today's appointments:");
	static Text patientidl = new Text("What is the patient's id?");
	static Text firstnamel = new Text("First Name:");
	static Text lastnamel = new Text("Last name:");
	static Text birthdatel = new Text("Birthdate:");
	static Text homeaddressl = new Text("Home Address");
	static Text genderl = new Text("Gender:");
	static Text phonenumberl = new Text("Phone Number:");
	static Text lastvisitl = new Text("Last Visit Was: ");
	static Label exitconfirmationlabel;
	static Button createappointmentbutton = new Button("Create Appointment");
	static Button patientrecordsbutton = new Button ("View/Edit Patient Information");
	static Button patientperscriptionsbutton = new Button ("Patients Prescriptions");
	static Button patientsheldbutton = new Button("Institutionalized Patients");
	static Button addpatientbutton = new Button("Add Patient");
	static Button logoutbutton = new Button("Log Out");
	static Button editrecordbutton = new Button("Update Personal Information");
	static Button searchbutton = new Button(patientsearchl);
	static Button yesbutton = new Button("Yes");
	static Button nobutton = new Button("No");
	static Button backbutton = new Button("Back");
	static Button okbutton = new Button("OK");
	static Button cancelbutton = new Button("Cancel");
	static Button updatebutton = new Button("Update");
	static Button viewappointments = new Button("View Appointments");
	static String exitconfirmation = "Are you sure you want to exit?";
	
	//Time out variables
		Point2D prevMousePos = new Point2D(0,0);
		int timeOutDelay = 5;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		window = primaryStage;
		window.setTitle("Receptionist View");
		welcome.setPadding(new Insets(10, 10, 10, 150));
		welcome.setStyle("-fx-font-weight: bold");
		window.setOnCloseRequest(e -> {
			e.consume();
			confirmExit();
		});
		
		logoutButton.setOnAction(e -> {
			ViewMenuController vm = new ViewMenuController();
			try {
				vm.start(window);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		//Configure button actions
		addpatientbutton.setOnAction(e -> addPatient());
		patientrecordsbutton.setOnAction(e-> patientsearch());
		
		createappointmentbutton.setOnAction(e -> {
			//Put code here for going to appointment view
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
		
		
		mainmenu = new Scene(grid, 640, 480);
		window.setScene(mainmenu);
		window.show();
		
		//Set page to time out after 10 seconds
		TimeoutTimer timeout = new TimeoutTimer(grid, window, 10); //This method is overloaded; if you only use two arguments the time defaults
		timeout.start();                                           //to 120 seconds. I'm using 10 seconds to make it easier to demo.
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
	
	public static void patientsearch() {
		VBox layout2 = new VBox(20);
		TextField patientidinput = new TextField();
		backbutton.setOnAction(e-> window.setScene(mainmenu));
		layout2.getChildren().addAll(patientidl, patientidinput, searchbutton, backbutton);
		searchbutton.setOnAction(e -> {
			pid = patientidinput.getText();
			a = PatientDAO.getPatientInfo(Integer.parseInt(pid), 1);
			a = new Patient();
			a.setPatientnum(Integer.parseInt(pid));
			//These strings represents the prepared statements that will be executed to retrieve the patient info from the database
			//Feeds the results obtained from the database to the 'patientrecords' menu
			PatientRecordsController.ViewPatientRecordsRecep(a, window);
		});
		window.setTitle(patientsearchl);
		Scene patientsearch= new Scene(layout2, 640, 640);
		
		window.setScene(patientsearch);
	}
	
	/*private static void patientrecords(String patientid, String firstnamestr, String lastnamestr, String birthdatestr, String homeaddressstr, String genderstr, String phonenumberstr, String lastvisitstring) {
		VBox layout2 = new VBox(10);
		Label firstname = new Label(firstnamestr); Label lastname = new Label(lastnamestr); Label birthdate = new Label(birthdatestr);
		Label homeaddress = new Label(homeaddressstr); Label gender = new Label(genderstr); Label phonenumber = new Label(phonenumberstr);
		Label lastvisit = new Label(lastvisitstring);
		//Bolding labels for patient information
		firstnamel.setStyle("-fx-font-weight: bold");
		lastnamel.setStyle("-fx-font-weight: bold");
		birthdatel.setStyle("-fx-font-weight: bold");
		homeaddressl.setStyle("-fx-font-weight: bold");
		genderl.setStyle("-fx-font-weight: bold");
		phonenumberl.setStyle("-fx-font-weight: bold");
		lastvisitl.setStyle("-fx-font-weight: bold");
		backbutton.setOnAction(e->patientsearch());
		editrecordbutton.setOnAction(e-> recordedit(patientid, firstnamestr, lastnamestr, birthdatestr, homeaddressstr, genderstr, phonenumberstr, lastvisitstring));
		layout2.getChildren().addAll(firstnamel, firstname, lastnamel, lastname, birthdatel, birthdate, homeaddressl, homeaddress, genderl, gender, phonenumberl, phonenumber, lastvisitl, lastvisit, editrecordbutton, backbutton);
		Scene patientrecords = new Scene(layout2, 640, 640);
		window.setScene(patientrecords);
	}*/

	static void recordeditor(Patient a) {
		VBox layout3 = new VBox(10);
		Date BirthDate;
		
		String updatePersonalInfo = "UPDATE mentcare2.Personal_Info SET Fname = ? , Lname = ?, BDate = ?, Address = ?, Sex = ?, Phone_Number = ? WHERE PNumber = ? ";
		String updateMedicalInfo = "UPDATE mentcare2.Medical_Info SET Ssn = ?, Last_Visit = ? WHERE PNum = ?";
		String updateDiagnosis = "UPDATE mentcare2.Medical_Info SET Diagnosis = ? WHERE PNum = ?";
		String insertIntoDiagHistory = "INSERT INTO mentcare2.Diagnosis_History VALUES ( ? , ?, ?, ? )";
		String selectCurrentDiag = "SELECT mentcare2.Medical_Info.Diagnosis FROM mentcare2.Medical_Info WHERE ? = PNum";
		
		TextField fname = new TextField(a.getFirstname()); TextField lname = new TextField(a.getLastname()); 
		TextField birthdate = new TextField((a.getBirthdate()).toString()); TextField addr = new TextField(a.getAddress()); 
		TextField sex = new TextField(a.getGender()); TextField phonenum = new TextField(a.getPhoneNumber());
		TextField social = new TextField(a.getSsn()); TextField lastapt = new TextField((a.getLastVisit()).toString());
		TextField diago = new TextField(a.getDiagnosis());
		
		
		updatebutton.setOnAction( e -> {
			/* if(tempDiagnosis.isSelected()){ //Add code here for handling status of Diagnosis in database
			 * ifSelected will be true when checkbox is selected (Diagnosis is temporary) and false when 
			 * checkbos is not selected (Diagnosis is not temporary)
			}*/
			a.updateRecord(fname.getText(), lname.getText(), LocalDate.parse(birthdate.getText()), addr.getText(), sex.getText(), phonenum.getText(), social.getText(), LocalDate.parse(lastapt.getText()), diago.getText(), a.getPatientnum());
			//new Thread(a).start();
			
			PatientDAO.updatePatientInfo(a);
			PatientRecordsController.ViewPatientRecordsRecep(a, window);
	});
		
		
		backbutton.setOnAction(e-> {
			PatientDAO.updatePatientInfo(a);
			PatientRecordsController.ViewPatientRecordsRecep(a, window);
		});
		
		layout3.getChildren().addAll(firstnamel, fname, lastnamel, lname, birthdatel, birthdate, homeaddressl, addr, genderl, sex, phonenumberl, phonenum, lastvisitl, lastapt, updatebutton, backbutton);
		
		
		Scene recordeditor = new Scene(layout3, 680, 680);
		window.setScene(recordeditor);
	}
	
	
	
}