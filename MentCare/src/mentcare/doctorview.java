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

/**
 * Stage is the entire application window
 * Scene is the area inside stage that has buttons, etc
 *
 */
public class doctorview extends Application{

	String placeholder = "Placeholder for: ";
	
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
			
			//These strings represents the prepared statements that will be executed to retrieve the patient info from the database
			String selectPinfoStmt = "SELECT PNumber, LName, FName, BDate, Address, Sex, Phone_Number FROM mentcare.Personal_Info WHERE ? = mentcare.Personal_Info.PNumber";
			String selectMinfostmt = "SELECT * FROM mentcare.Medical_Info WHERE ? = mentcare.Medical_Info.PNum";
			int pnum = -1; //The variables passed to the 'patientrcords' method are initiated to blank values
			String fname = "", lname = "", address = "", sex = "", phonenum = "", diagnosis="", ssn="";
			Date bdate = null, lastvisit = null;
			try {
				PreparedStatement pstmt = viewMenu.con.prepareStatement(selectPinfoStmt);
				pstmt.setInt(1, Integer.parseInt(pid));
				ResultSet rs = pstmt.executeQuery(); //ResultSet contains the results of the query
				while(rs.next()){ //Gets the information from the "Personal Info" table
					pnum = rs.getInt("PNumber");
					fname = rs.getString("Fname");
					lname = rs.getString("Lname");
					address = rs.getString("Address");
					sex = rs.getString("Sex");
					phonenum = rs.getString("Phone_Number");
					bdate = rs.getDate("BDate");
				}
				
				pstmt = viewMenu.con.prepareStatement(selectMinfostmt);
				pstmt.setInt(1, Integer.parseInt(patientidinput.getText()));
				rs = pstmt.executeQuery();
				
				while(rs.next()){ //Gets the information from the "Medical Info" table
					diagnosis = rs.getString("Diagnosis");
					lastvisit = rs.getDate("Last_Visit");
					ssn = rs.getString("Ssn");
				}
				pstmt.close();
				rs.close();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(patientidinput.getText());
			//Feeds the results obtained from the database to the 'patientrecords' menu
			patientrecords(Integer.toString((pnum)), fname, lname, bdate.toString(), address, sex, phonenum, diagnosis, ssn, lastvisit.toString());
		});
		window.setTitle(patientsearch);
		Scene patientsearch= new Scene(layout2, 640, 640);
		
		window.setScene(patientsearch);
	}
	/**
	 * Displays a patient's records.
	 * @param patient info. (Should be replaced by patient object
	 */
	private static void patientrecords(String patientid, String firstnamestr, String lastnamestr, String birthdatestr, String homeaddressstr, String genderstr, String phonenumberstr, String diagnosisstr, String ssn, String lastaptdatestring) {
		VBox layout2 = new VBox(10);
		Label firstname = new Label(firstnamestr); Label lastname = new Label(lastnamestr); Label birthdate = new Label(birthdatestr);
		Label homeaddress = new Label(homeaddressstr); Label gender = new Label(genderstr); Label phonenumber = new Label(phonenumberstr);
		Label diagnosis = new Label(diagnosisstr); Label Ssn = new Label(ssn); Label lastapt = new Label(lastaptdatestring);
		diagnosishistorybutton.setOnAction(e->diagnosishistory(patientid, firstnamestr, lastnamestr, birthdatestr, homeaddressstr, genderstr, phonenumberstr, diagnosisstr, ssn, lastaptdatestring));
		backbutton.setOnAction(e->patientsearch());
		editrecordbutton.setOnAction(e-> recordeditor(patientid, firstnamestr, lastnamestr, birthdatestr, homeaddressstr, genderstr, phonenumberstr, diagnosisstr, ssn, lastaptdatestring));
		layout2.getChildren().addAll(firstnamel, firstname, lastnamel, lastname, birthdatel, birthdate, homeaddressl, homeaddress, genderl, gender, phonenumberl, phonenumber, diagnosisl, diagnosis, ssnl, Ssn, lastvisitl, lastapt, diagnosishistorybutton, editrecordbutton, backbutton);
		Scene patientrecords = new Scene(layout2, 640, 640);
		window.setScene(patientrecords);
	}
	private static void recordeditor(String patientid, String firstnamestr, String lastnamestr, String birthdatestr, String homeaddressstr, String genderstr, String phonenumberstr, String diagnosisstr, String ssn, String lastaptdatestring) {
		VBox layout3 = new VBox(10);
		Date BirthDate;
		
		String updatePersonalInfo = "UPDATE mentcare.Personal_Info SET Fname = ? , Lname = ?, BDate = ?, Address = ?, Sex = ?, Phone_Number = ? WHERE PNumber = ? ";
		String updateMedicalInfo = "UPDATE mentcare.Medical_Info SET Ssn = ?, Last_Visit = ? WHERE PNum = ?";
		String updateDiagnosis = "UPDATE mentcare.Medical_Info SET Diagnosis = ? WHERE PNum = ?";
		String insertIntoDiagHistory = "INSERT INTO mentcare.Diagnosis_History VALUES ( ? , ?, ?, ? )";
		String selectCurrentDiag = "SELECT mentcare.Medical_Info.Diagnosis FROM mentcare.Medical_Info WHERE ? = PNum";
		
		TextField fname = new TextField(firstnamestr); TextField lname = new TextField(lastnamestr); TextField birthdate = new TextField(birthdatestr);
		TextField addr = new TextField(homeaddressstr); TextField sex = new TextField(genderstr); TextField phonenum = new TextField(phonenumberstr);
		TextField social = new TextField(ssn); TextField lastapt = new TextField(lastaptdatestring); TextField diago = new TextField(diagnosisstr);
		
		updatebutton.setOnAction( e -> {
			try {
			Connection Con;
			PreparedStatement pstmt;
			
			Con = DriverManager.getConnection("jdbc:mysql://164.132.49.5:3306", "mentcare", "mentcare1");
			pstmt = Con.prepareStatement(updatePersonalInfo);//Updates the personal info and medical info tables, excluding diagnosis
			pstmt.setString(1, fname.getText());
			pstmt.setString(2,  lname.getText());
			pstmt.setObject(3, LocalDate.parse(birthdate.getText()));
			pstmt.setString(4, addr.getText());
			pstmt.setString(5,  sex.getText());
			pstmt.setString(6, phonenum.getText());
			pstmt.setInt(7, Integer.parseInt(patientid));
			pstmt.executeUpdate();
			pstmt = Con.prepareStatement(updateMedicalInfo);
			pstmt.setString(1, social.getText());
			pstmt.setObject(2, LocalDate.parse(lastapt.getText()));
			pstmt.setInt(3, Integer.parseInt(patientid));
			pstmt.executeUpdate();
			
			pstmt = Con.prepareStatement(selectCurrentDiag);
			pstmt.setInt(1, Integer.parseInt(patientid));
			ResultSet rs = pstmt.executeQuery();
			ArrayList<String> Diagnoses = new ArrayList<String>();
			while(rs.next()){
				Diagnoses.add(rs.getString("Diagnosis"));
			}
			if(!Diagnoses.get(0).equals(diago.getText())){
				pstmt = Con.prepareStatement(insertIntoDiagHistory);
				pstmt.setInt(1, Integer.parseInt(patientid));
				pstmt.setString(2, diago.getText());
				pstmt.setObject(3, LocalDate.now());
				pstmt.setObject(4, "Current Doctor");
				pstmt.executeUpdate();
				pstmt= Con.prepareStatement(updateDiagnosis);
				pstmt.setString(1, diago.getText());
				pstmt.setInt(2, Integer.parseInt(patientid));
				pstmt.executeUpdate();
			}
			
			pstmt.close();
			patientrecords(patientid, fname.getText(), lname.getText(), birthdate.getText(), addr.getText(), sex.getText(), phonenum.getText(), diago.getText(), social.getText(), lastapt.getText());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
	});
		
		
		backbutton.setOnAction(e-> patientrecords(patientid, fname.getText(), lname.getText(), birthdate.getText(), addr.getText(), sex.getText(), phonenum.getText(), diago.getText(), social.getText(), lastapt.getText()));
		
		layout3.getChildren().addAll(firstnamel, fname, lastnamel, lname, birthdatel, birthdate, homeaddressl, addr, genderl, sex, phonenumberl, phonenum, diagnosisl, diago , ssnl, social, lastvisitl, lastapt, updatebutton, backbutton);
		
		
		Scene recordeditor = new Scene(layout3, 680, 680);
		window.setScene(recordeditor);
	}
	private static void diagnosishistory(String patientid, String firstnamestr, String lastnamestr, String birthdatestr, String homeaddressstr, String genderstr, String phonenumberstr, String diagnosisstr, String ssn, String lastaptdatestring) {
		
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
			pstmt.setInt(1, Integer.parseInt(patientid));
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
		
	    backbutton.setOnAction(e-> patientrecords(patientid, firstnamestr, lastnamestr, birthdatestr, homeaddressstr, genderstr, phonenumberstr, diagnosisstr, ssn, lastaptdatestring));
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
