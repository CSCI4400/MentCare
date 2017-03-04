package mentcare;

import javafx.scene.layout.VBox;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

/**
 * Stage is the entire application window
 * Scene is the area inside stage that has buttons, etc
 *
 */
public class doctorview extends Application{

	String placeholder = "Placeholder for: ";
	
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
	
	public void start(Stage primaryStage) throws Exception {
		//Adds buttons and labels
		window = primaryStage;
		welcome = new Label(welcomestring);
		window.setTitle("Program title");
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
			patientidinput.getText();
			
			//These strings represents the prepared statements that will be executed to retrieve the patient info from the database
			String selectPinfoStmt = "SELECT PNumber, LName, FName, BDate, Address, Sex, Phone_Number FROM mentcare.Personal_Info WHERE ? = mentcare.Personal_Info.PNumber";
			String selectMinfostmt = "SELECT * FROM mentcare.Medical_Info WHERE ? = mentcare.Medical_Info.PNum";
			int pnum = -1; //The variables passed to the 'patientrcords' method are initiated to blank values
			String fname = "", lname = "", address = "", sex = "", phonenum = "", diagnosis="", ssn="";
			Date bdate = null, lastvisit = null;
			try {
				PreparedStatement pstmt = viewMenu.con.prepareStatement(selectPinfoStmt);
				pstmt.setInt(1, Integer.parseInt(patientidinput.getText()));
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
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(patientidinput.getText());
			//Feeds the results obtained from the database to the 'patientrecords' menu
			patientrecords(Integer.toString((pnum)), lname, fname, bdate.toString(), address, sex, phonenum, diagnosis);
		});
		window.setTitle(patientsearch);
		Scene patientsearch= new Scene(layout2, 640, 640);
		
		window.setScene(patientsearch);
	}
	/**
	 * Displays a patient's records.
	 * @param patient info. (Should be replaced by patient object
	 */
	private static void patientrecords(String patientid, String firstnamestr, String lastnamestr, String birthdatestr, String homeaddressstr, String genderstr, String phonenumberstr, String diagnosisstr) {
		VBox layout2 = new VBox(10);
		Label firstname = new Label(firstnamestr); Label lastname = new Label(lastnamestr); Label birthdate = new Label(birthdatestr);
		Label homeaddress = new Label(homeaddressstr); Label gender = new Label(genderstr); Label phonenumber = new Label(phonenumberstr);
		Label diagnosis = new Label(diagnosisstr);
		diagnosishistorybutton.setOnAction(e->diagnosishistory(patientid));
		backbutton.setOnAction(e->patientsearch());
		editrecordbutton.setOnAction(e-> recordeditor(patientid, firstnamestr, lastnamestr, birthdatestr, homeaddressstr, genderstr, phonenumberstr, diagnosisstr));
		layout2.getChildren().addAll(firstnamel, firstname, lastnamel, lastname, birthdatel, birthdate, homeaddressl, homeaddress, genderl, gender, phonenumberl, phonenumber, diagnosisl, diagnosis, diagnosishistorybutton, editrecordbutton, backbutton);
		Scene patientrecords = new Scene(layout2, 640, 640);
		window.setScene(patientrecords);
	}
	private static void recordeditor(String patientid, String firstnamestr, String lastnamestr, String birthdatestr, String homeaddressstr, String genderstr, String phonenumberstr, String diagnosisstr) {
		VBox layout2 = new VBox(10);
		backbutton.setOnAction(e-> patientrecords(patientid, firstnamestr, lastnamestr, birthdatestr, homeaddressstr, genderstr, phonenumberstr, diagnosisstr));
		layout2.getChildren().addAll(backbutton);
		Scene recordeditor = new Scene(layout2, 640, 480);
		window.setScene(recordeditor);
	}
	private static void diagnosishistory(String patientid) {
		
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
