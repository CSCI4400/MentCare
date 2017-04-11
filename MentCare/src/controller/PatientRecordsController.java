package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.MainFXApp;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Patient;
import view.DiagnosisHistoryView;

public class PatientRecordsController {

	static Button diagnosishistorybutton = new Button("Diagnosis History");
	static Button backbutton = new Button("Back");
	static Button updatebutton = new Button("Update Information");
	//These are the labels for the patient information
	public static Text firstnamel = new Text("First Name:");
	public static Text lastnamel = new Text("Last name:");
	public static Text birthdatel = new Text("Birthdate:");
	public static Text homeaddressl = new Text("Home Address");
	public static Text genderl = new Text("Gender:");
	public static Text phonenumberl = new Text("Phone Number:");
	public static Text diagnosisl = new Text("Diagnosis:");
	public static Text ssnl = new Text("SSN: ");
	public static Text lastvisitl = new Text("Last Visit Was: ");
	//This is the database query to check if a patient is dead
	static String deathCheck = "SELECT mentcare.Patient_Info.Dead FROM mentcare.Patient_Info WHERE mentcare.Patient_Info.PNumber = ?";

	public static void ViewPatientRecordsDoc(Patient a, Stage window){
		//This method is the patient record viewer for a doctor.
		//That means that medical information like diagnosis is included.
		VBox layout2 = new VBox(10);
		Label firstname = new Label(a.getFirstname()); Label lastname = new Label(a.getLastname()); Label birthdate = new Label((a.getBirthdate()).toString());
		Label homeaddress = new Label(a.getAddress()); Label gender = new Label(a.getGender()); Label phonenumber = new Label(a.getPhoneNumber());
		Label diagnosis = new Label(a.getDiagnosis()); Label Ssn = new Label(a.getSsn()); Label lastapt = new Label((a.getLastVisit()).toString());
		try {
			//Prepares a query and checks the database to see if the patient is dead
			PreparedStatement pstmt = MainFXApp.con.prepareStatement(deathCheck);
			//Completes the query with the patient ID number for the current patient
			pstmt.setInt(1, a.getPatientnum());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getString("Dead").equals("yes")){
					//disables update button for a dead patient
					updatebutton.setDisable(true);
				}
				else if(rs.getString("Dead").equals("no")){
					//enables update button if the patient is alive.
					//Necessary to re-enable update button in case last patient viewed was dead.
					updatebutton.setDisable(false);
				}
			}

			pstmt.close();
			rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



		//Calls the static method for the diagnosis history view for the current patient.
		//Parameters are the patient object and the current stage
		diagnosishistorybutton.setOnAction(e->DiagnosisHistoryView.DiagnosisHistory(a, window));
		//Back button returns to the Doctor search scene
		backbutton.setOnAction(e->SearchPatientController.searchPatientDoc(window));
		//Update button calls the static method for editing a patient record for a doctor
		//Parameters are the current patient object and the current stage
		updatebutton.setOnAction(e-> EditPatientRecordsController.DocEditPatientRecords(a, window));
		//Adds the labels to the view
		layout2.getChildren().addAll(firstnamel, firstname, lastnamel, lastname, birthdatel, birthdate, homeaddressl, homeaddress, genderl, gender, phonenumberl, phonenumber, diagnosisl, diagnosis, ssnl, Ssn, lastvisitl, lastapt, diagnosishistorybutton, updatebutton, backbutton);
		Scene Docpatientrecords = new Scene(layout2, 700, 700);
		window.setScene(Docpatientrecords);
	}

	public static void ViewPatientRecordsRecep(Patient a, Stage window){
		//This is the view patient record method for a receptionist.
		//That means no medical info is displayed
		VBox layout3 = new VBox(10);
		Label firstname = new Label(a.getFirstname()); Label lastname = new Label(a.getLastname()); Label birthdate = new Label((a.getBirthdate()).toString());
		Label homeaddress = new Label(a.getAddress()); Label gender = new Label(a.getGender()); Label phonenumber = new Label(a.getPhoneNumber());
		Label lastapt = new Label((a.getLastVisit()).toString());
		try {
			//Queries the database to check if the patient is dead.
			//Works the same way as in the doctor view above
			PreparedStatement pstmt = MainFXApp.con.prepareStatement(deathCheck);
			//Completes query with Patient ID number of current patient
			pstmt.setInt(1, a.getPatientnum());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getString("Dead").equals("yes")){
					//disables update button for a dead patient
					updatebutton.setDisable(true);
				}
				else if(rs.getString("Dead").equals("no")){
					//enables update button if the patient is alive.
					//Necessary to re-enable update button in case last patient viewed was dead.
					updatebutton.setDisable(false);
				}
			}

			pstmt.close();
			rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//back button returns to the receptionist search patient view
		backbutton.setOnAction(e->SearchPatientController.searchPatientRecep(window));
		//Update button calls the static method for editing a patient record as a receptionist
		//This means medical info is not visible and cannot be changed.
		//Parameters are the current patient object and the current stage.
		updatebutton.setOnAction(e-> EditPatientRecordsController.RecepEditPatientRecords(a, window));
		//Adds all the labels to the window
		layout3.getChildren().addAll(firstnamel, firstname, lastnamel, lastname, birthdatel, birthdate, homeaddressl, homeaddress, genderl, gender, phonenumberl, phonenumber, lastvisitl, lastapt, updatebutton, backbutton);
		Scene Receppatientrecords = new Scene(layout3, 700, 700);
		window.setScene(Receppatientrecords);

	}

}
