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

import controller.ViewMenuController;
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
import model.InitialDBConnection;
import model.TimeoutTimer;
import javafx.scene.control.*;
import javafx.scene.text.*;



public class ReceptionistView extends Application {
	
	static Stage window;
	static Button logoutButton = new Button("Log Out");
	static String patientsearch = "Search";
	static String pid;
	
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
	static Button editrecordbutton = new Button("Update Personal Information");
	static Button searchbutton = new Button(patientsearch);
	static Button yesbutton = new Button("yes");
	static Button nobutton = new Button("no");
	static Button backbutton = new Button("Back");
	static Button okbutton = new Button("ok");
	static Button cancelbutton = new Button("cancel");
	static Button updatebutton = new Button("Update");
	static Button viewappointments = new Button("View Appointments");
	static String exitconfirmation = "Are you sure you wanted to exit?";
	
	//Time out variables
		Point2D prevMousePos = new Point2D(0,0);
		int timeOutDelay = 5;

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
		
		
		mainmenu = new Scene(grid, 500, 500);
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
	
	private static void patientsearch() {
		
		String selectPStmt = "SELECT * FROM mentcare.Personal_Info WHERE ? = mentcare.Personal_Info.PNumber";
		String selectMStmt = "SELECT Last_Visit FROM mentcare.Medical_Info WHERE ? = mentcare.Medical_Info.PNum";
		
		VBox layout2 = new VBox(20);
		TextField patientidinput = new TextField();
		backbutton.setOnAction(e -> window.setScene(mainmenu));
		layout2.getChildren().addAll(patientidl, patientidinput, searchbutton, backbutton);
		searchbutton.setOnAction(e -> {
			pid = patientidinput.getText();
			int pnum = -1; //The variables passed to the 'patientrcords' method are initiated to blank values
			String fname = "", lname = "", address = "", sex = "", phonenum = "", diagnosis="", ssn="";
			Date bdate = null, lastvisit = null;
			try {
				PreparedStatement pstmt = ViewMenuController.con.prepareStatement(selectPStmt);
				pstmt.setInt(1, Integer.parseInt(pid));
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()){
					pnum = rs.getInt("PNumber");
					fname = rs.getString("Fname");
					lname = rs.getString("Lname");
					address = rs.getString("Address");
					sex = rs.getString("Sex");
					phonenum = rs.getString("Phone_Number");
					bdate = rs.getDate("BDate");
				}
				
				pstmt = ViewMenuController.con.prepareStatement(selectMStmt);
				pstmt.setInt(1,Integer.parseInt(pid));
				rs = pstmt.executeQuery();
				while(rs.next()){
					lastvisit = rs.getDate("Last_Visit");
				}
				pstmt.close();
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			patientrecords(Integer.toString((pnum)), fname, lname, bdate.toString(), address, sex, phonenum, lastvisit.toString());
		});
		window.setTitle(patientsearch);
		Scene patientsearch= new Scene(layout2, 640, 640);
		
		window.setScene(patientsearch);
	}
	
	private static void patientrecords(String patientid, String firstnamestr, String lastnamestr, String birthdatestr, String homeaddressstr, String genderstr, String phonenumberstr, String lastvisitstring) {
		VBox layout2 = new VBox(10);
		Label firstname = new Label(firstnamestr); Label lastname = new Label(lastnamestr); Label birthdate = new Label(birthdatestr);
		Label homeaddress = new Label(homeaddressstr); Label gender = new Label(genderstr); Label phonenumber = new Label(phonenumberstr);
		Label lastvisit = new Label(lastvisitstring);
		backbutton.setOnAction(e->patientsearch());
		editrecordbutton.setOnAction(e-> recordedit(patientid, firstnamestr, lastnamestr, birthdatestr, homeaddressstr, genderstr, phonenumberstr, lastvisitstring));
		layout2.getChildren().addAll(firstnamel, firstname, lastnamel, lastname, birthdatel, birthdate, homeaddressl, homeaddress, genderl, gender, phonenumberl, phonenumber, lastvisitl, lastvisit, editrecordbutton, backbutton);
		Scene patientrecords = new Scene(layout2, 640, 640);
		window.setScene(patientrecords);
	}

	private static void recordedit(String patientid, String firstnamestr, String lastnamestr, String birthdatestr,
			String homeaddressstr, String genderstr, String phonenumberstr, String lastvisitstring) {
		
		String updatePersonalInfo = "UPDATE mentcare.Personal_Info SET Fname = ? , Lname = ?, BDate = ?, Address = ?, Sex = ?, Phone_Number = ? WHERE PNumber = ? ";
		String updateMedicalInfo = "UPDATE mentcare.Medical_Info SET Last_Visit = ? WHERE PNum = ?";
		
		VBox layout3 = new VBox(10);
		Date BirthDate;
		TextField fname = new TextField(firstnamestr); TextField lname = new TextField(lastnamestr); TextField birthdate = new TextField(birthdatestr);
		TextField addr = new TextField(homeaddressstr); TextField sex = new TextField(genderstr); TextField phonenum = new TextField(phonenumberstr);
		TextField lastapt = new TextField(lastvisitstring);
		
		updatebutton.setOnAction(e-> {
			try{
			Connection Con;
			PreparedStatement pstmt;
			
			Con = DriverManager.getConnection("jdbc:mysql://164.132.49.5:3306", "mentcare", InitialDBConnection.DBPASSWORD);
			pstmt = Con.prepareStatement(updatePersonalInfo);//Updates the personal info and possibly last visit date in medical info
			pstmt.setString(1, fname.getText());
			pstmt.setString(2,  lname.getText());
			pstmt.setObject(3, LocalDate.parse(birthdate.getText()));
			pstmt.setString(4, addr.getText());
			pstmt.setString(5,  sex.getText());
			pstmt.setString(6, phonenum.getText());
			pstmt.setInt(7, Integer.parseInt(patientid));
			pstmt.executeUpdate();
			pstmt = Con.prepareStatement(updateMedicalInfo);
			pstmt.setObject(1, LocalDate.parse(lastapt.getText()));
			pstmt.setInt(2, Integer.parseInt(pid));
			pstmt.executeUpdate();
			pstmt.close();
			
			patientrecords(patientid, fname.getText(), lname.getText(), birthdate.getText(), addr.getText(), sex.getText(), phonenum.getText(), lastapt.getText());
			
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		
		
		
		backbutton.setOnAction(e-> patientrecords(patientid, fname.getText(), lname.getText(), birthdate.getText(), addr.getText(), sex.getText(), phonenum.getText(), lastapt.getText()));
		
		layout3.getChildren().addAll(firstnamel, fname, lastnamel, lname, birthdatel, birthdate, homeaddressl, addr, genderl, sex, phonenumberl, phonenum, lastvisitl, lastapt, updatebutton, backbutton);
		
		
		Scene recordeditor = new Scene(layout3, 680, 680);
		window.setScene(recordeditor);
		
		
		
		
	}
	
	
	
}