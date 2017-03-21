package view;

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

import controller.PatientDAO;
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
import model.Patient;
import javafx.scene.control.*;
import javafx.scene.text.*;

/**
 * Stage is the entire application window
 * Scene is the area inside stage that has buttons, etc
 *
 */
public class doctorview extends Application{

	String placeholder = "Placeholder for: ";
	
	static Patient a = new Patient();
	static boolean patientupdate= false;
	static String pid; //used to store the ID# of the patient whose record is being looked at
	
	String welcomestring = "Welcome Doctor, " + "xyz";
	static String exitconfirmation = "Are you sure you wanted to exit?";
	
	static String patientsearch = "Search";
	
	static Stage window;
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
	static Button patientrecordsbutton = new Button ("View/Edit patient records");
	static Button patientperscriptionsbutton = new Button ("Patients perscriptions");
	static Button patientsheldbutton = new Button("Institutionalized Patients");
	static Button addpatientbutton = new Button("Click to add a patient");
	static Button logoutbutton = new Button("Log out");
	static Button diagnosishistorybutton = new Button("Diagnosis History");
	static Button editrecordbutton = new Button("Edit Record");
	static Button searchbutton = new Button(patientsearch);
	static Button yesbutton = new Button("yes");
	static Button nobutton = new Button("no");
	static Button backbutton = new Button("Back");
	static Button okbutton = new Button("ok");
	static Button cancelbutton = new Button("cancel");
	static Button updatebutton = new Button("Update");
	
	public void start(Stage primaryStage) throws Exception {
		//Adds buttons and labels
		window = primaryStage;
		welcome = new Label(welcomestring);
		window.setTitle("Doctor View");
		window.setOnCloseRequest(e -> {
			e.consume();
			confirmExit();
		});
		
		
		//Configure button actions
		addpatientbutton.setOnAction(e -> addPatient());
		patientrecordsbutton.setOnAction(e-> patientsearch());
		
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
		todaysappointmentslayout.getChildren().addAll(todaysappointmentsl);
		
		GridPane mainarea = new GridPane();
		mainarea.setVgap(10); mainarea.setHgap(10);
		mainarea.add(createappointmentbutton, 0, 0);
		mainarea.add(addpatientbutton, 1, 0);
		mainarea.add(patientrecordsbutton, 0, 1);
		mainarea.add(patientsheldbutton, 1, 1);
		mainarea.add(logoutbutton, 0, 2);
		BorderPane layout = new BorderPane();
		welcome.setPadding(new Insets(0, 0, 0, 180));
		layout.setTop(welcome);
		layout.setLeft(todaysappointmentslayout);
		layout.setCenter(mainarea);
		
		//Display stage
		mainmenu = new Scene(layout, 640, 480);
		window.setScene(mainmenu);
		window.show();
		
	}
	private static void patientsearch() {
		VBox layout2 = new VBox(20);
		TextField patientidinput = new TextField();
		backbutton.setOnAction(e-> window.setScene(mainmenu));
		layout2.getChildren().addAll(patientidl, patientidinput, searchbutton, backbutton);
		searchbutton.setOnAction(e -> {
			pid = patientidinput.getText();
			a = PatientDAO.getPatientInfo(Integer.parseInt(pid), 0);
			//a = new Patient(Integer.parseInt(pid));
			//These strings represents the prepared statements that will be executed to retrieve the patient info from the database
			//Feeds the results obtained from the database to the 'patientrecords' menu
			patientrecords(a);
		});
		window.setTitle(patientsearch);
		Scene patientsearch= new Scene(layout2, 640, 640);
		
		window.setScene(patientsearch);
	}
	/**
	 * Displays a patient's records.
	 * @param patient info. (Should be replaced by patient object
	 */
	public static void patientrecords(Patient a) {
		VBox layout2 = new VBox(10);
		Label firstname = new Label(a.getFirstname()); Label lastname = new Label(a.getLastname()); Label birthdate = new Label((a.getBirthdate()).toString());
		Label homeaddress = new Label(a.getAddress()); Label gender = new Label(a.getGender()); Label phonenumber = new Label(a.getPhoneNumber());
		Label diagnosis = new Label(a.getDiagnosis()); Label Ssn = new Label(a.getSsn()); Label lastapt = new Label((a.getLastVisit()).toString());
		diagnosishistorybutton.setOnAction(e->diagnosishistory(a));
		backbutton.setOnAction(e->patientsearch());
		editrecordbutton.setOnAction(e-> recordeditor(a));
		layout2.getChildren().addAll(firstnamel, firstname, lastnamel, lastname, birthdatel, birthdate, homeaddressl, homeaddress, genderl, gender, phonenumberl, phonenumber, diagnosisl, diagnosis, ssnl, Ssn, lastvisitl, lastapt, diagnosishistorybutton, editrecordbutton, backbutton);
		Scene patientrecords = new Scene(layout2, 800, 800);
		window.setScene(patientrecords);
	}
	private static void recordeditor(Patient a) {
		VBox layout3 = new VBox(10);
		Date BirthDate;
		
		String updatePersonalInfo = "UPDATE mentcare.Personal_Info SET Fname = ? , Lname = ?, BDate = ?, Address = ?, Sex = ?, Phone_Number = ? WHERE PNumber = ? ";
		String updateMedicalInfo = "UPDATE mentcare.Medical_Info SET Ssn = ?, Last_Visit = ? WHERE PNum = ?";
		String updateDiagnosis = "UPDATE mentcare.Medical_Info SET Diagnosis = ? WHERE PNum = ?";
		String insertIntoDiagHistory = "INSERT INTO mentcare.Diagnosis_History VALUES ( ? , ?, ?, ? )";
		String selectCurrentDiag = "SELECT mentcare.Medical_Info.Diagnosis FROM mentcare.Medical_Info WHERE ? = PNum";
		
		TextField fname = new TextField(a.getFirstname()); TextField lname = new TextField(a.getLastname()); 
		TextField birthdate = new TextField((a.getBirthdate()).toString()); TextField addr = new TextField(a.getAddress()); 
		TextField sex = new TextField(a.getGender()); TextField phonenum = new TextField(a.getPhoneNumber());
		TextField social = new TextField(a.getSsn()); TextField lastapt = new TextField((a.getLastVisit()).toString());
		TextField diago = new TextField(a.getDiagnosis());
		
		
		updatebutton.setOnAction( e -> {
			a.updateRecord(fname.getText(), lname.getText(), LocalDate.parse(birthdate.getText()), addr.getText(), sex.getText(), phonenum.getText(), social.getText(), LocalDate.parse(lastapt.getText()), diago.getText(), a.getPatientnum());
			//new Thread(a).start();
			
			PatientDAO.updatePatientInfo(a);
			patientrecords(a);
	});
		
		
		backbutton.setOnAction(e-> {
			PatientDAO.updatePatientInfo(a);
			patientrecords(a);
		});
		
		layout3.getChildren().addAll(firstnamel, fname, lastnamel, lname, birthdatel, birthdate, homeaddressl, addr, genderl, sex, phonenumberl, phonenum, diagnosisl, diago , ssnl, social, lastvisitl, lastapt, updatebutton, backbutton);
		
		
		Scene recordeditor = new Scene(layout3, 680, 680);
		window.setScene(recordeditor);
	}
	private static void diagnosishistory(Patient a) {
		
		BorderPane diaghistlayout = new BorderPane();
		VBox Dleft = new VBox();
		VBox Dmid = new VBox();
		VBox Dright = new VBox();
		
		Dleft.setPadding(new Insets(15, 12, 15, 12));
		Dleft.setSpacing(10);
		Text t1 = new Text("Diagnosis: ");
		Dleft.getChildren().add(t1);
		
		Dmid.setPadding(new Insets(15, 12, 15, 12));
		Dmid.setSpacing(10);
		Text t2 = new Text("Doctor Who Diagnosed: ");
		Dmid.getChildren().add(t2);
		
		
		Dright.setPadding(new Insets(15, 12, 15, 12));
		Dright.setSpacing(10);
		Text t3 = new Text("Date of Diagnosis: ");
		Dright.getChildren().add(t3);
		
		diaghistlayout.setLeft(Dleft);
		diaghistlayout.setCenter(Dmid);
		diaghistlayout.setRight(Dright);
		
		String selhistory = "SELECT * FROM mentcare.Diagnosis_History WHERE ? = mentcare.Diagnosis_History.PNum";
		
		PreparedStatement pstmt;
		Collection<String> Diagnoses = new ArrayList<>();
		Collection<String> DoctorNames = new ArrayList<>();
		Collection<String> DatesofD = new ArrayList<>();
		try {
			pstmt = viewMenu.con.prepareStatement(selhistory);
			pstmt.setInt(1, a.getPatientnum());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Diagnoses.add(rs.getString("Diagnosis"));
				DoctorNames.add(rs.getString("Name_of_doctor"));
				DatesofD.add(rs.getDate("Date_of_diag").toString());
			}
			pstmt.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String s : Diagnoses){
			Label l = new Label(s);
			Dleft.getChildren().add(l);
		}
		
		for(String s: DoctorNames){
			Label l = new Label(s);
			Dmid.getChildren().add(l);
		}
		
		for(String s: DatesofD){
			Label l = new Label(s);
			Dright.getChildren().add(l);
		}
		
	    backbutton.setOnAction(e-> {
	    	PatientDAO.updatePatientInfo(a);
	    	patientrecords(a);
	    });
	    Dleft.getChildren().add(backbutton);
		
		Scene diaghistview = new Scene(diaghistlayout, 480, 480);
		
		window.setScene(diaghistview);
		
		
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
			viewMenu vMenu = new viewMenu();
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
