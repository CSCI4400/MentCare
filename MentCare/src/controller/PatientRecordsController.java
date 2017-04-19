package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.MainFXApp;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Patient;
import model.currentUser;
import view.DiagnosisHistoryView;

public class PatientRecordsController {

	static Button diagnosishistorybutton = new Button("Diagnosis History");
	static Button backbutton = new Button("Back");
	static Button updatebutton = new Button("Update Information");
	//These are the labels for the patient information
	public static Text pidLabel = new Text("Patient ID Number: ");
	public static Text firstnamel = new Text("First Name:");
	public static Text lastnamel = new Text("Last name:");
	public static Text birthdatel = new Text("Birthdate:");
	public static Text homeaddressl = new Text("Home Address");
	public static Text genderl = new Text("Gender:");
	public static Text phonenumberl = new Text("Phone Number:");
	public static Text diagnosisl = new Text("Diagnosis:");
	public static Text ssnl = new Text("SSN: ");
	public static Text lastvisitl = new Text("Last Visit Was: ");
	public static Text patient_iss = new Text ("Patient Issue Reported: ");
	public static Text threat_level = new Text ("Threat Issue: ");

	//This is the database query to check if a patient is dead
	static String deathCheck = "SELECT mentcare2.Personal_Info.Dead FROM mentcare2.Personal_Info WHERE mentcare2.Personal_Info.PNumber = ?";

	public static void ViewPatientRecordsDoc(Patient a, Stage window){
		//This method is the patient record viewer for a doctor.
		//That means that medical information like diagnosis is included.
		VBox layout2 = new VBox(10);
		Label patientid = new Label(Integer.toString(a.getPatientnum()));
		Label firstname = new Label(a.getFirstname()); Label lastname = new Label(a.getLastname()); Label birthdate = new Label((a.getBirthdate()).toString());
		Label homeaddress = new Label(a.getAddress()); Label gender = new Label(a.getGender()); Label phonenumber = new Label(a.getPhoneNumber());
		Label diagnosis = new Label(a.getDiagnosis()); Label Ssn = new Label(a.getSsn()); Label lastapt = new Label((a.getLastVisit()).toString());
		Label patient_issue = new Label(a.getPatient_issue()); Label threat_lvl = new Label(a.getThreat_level());
		//Sets font of labels
		firstnamel.setFont(Font.font("Georgia", 12));
		firstnamel.setStyle("-fx-font-weight: bold");
		lastnamel.setFont(Font.font("Georgia", 12));
		lastnamel.setStyle("-fx-font-weight: bold");
		birthdatel.setFont(Font.font("Georgia", 12));
		birthdatel.setStyle("-fx-font-weight: bold");
		homeaddressl.setFont(Font.font("Georgia", 12));
		homeaddressl.setStyle("-fx-font-weight: bold");
		genderl.setFont(Font.font("Georgia", 12));
		genderl.setStyle("-fx-font-weight: bold");
		phonenumberl.setFont(Font.font("Georgia", 12));
		phonenumberl.setStyle("-fx-font-weight: bold");
		diagnosisl.setFont(Font.font("Georgia", 12));
		diagnosisl.setStyle("-fx-font-weight: bold");
		ssnl.setFont(Font.font("Georgia", 12));
		ssnl.setStyle("-fx-font-weight: bold");
		lastvisitl.setFont(Font.font("Georgia", 12));
		lastvisitl.setStyle("-fx-font-weight: bold");
		pidLabel.setFont(Font.font("Georgia", 12));
		pidLabel.setStyle("-fx-font-weight: bold");
		patient_iss.setFont(Font.font("Georgia", 12));
		patient_iss.setStyle("-fx-font-weight: bold");
		threat_level.setFont(Font.font("Georgia", 12));
		threat_level.setStyle("-fx-font-weight: bold");

		//Sets font of patient information
		firstname.setFont(Font.font("Georgia", 12));
		lastname.setFont(Font.font("Georgia", 12));
		birthdate.setFont(Font.font("Georgia", 12));
		homeaddress.setFont(Font.font("Georgia", 12));
		gender.setFont(Font.font("Georgia", 12));
		phonenumber.setFont(Font.font("Georgia", 12));
		diagnosis.setFont(Font.font("Georgia", 12));
		Ssn.setFont(Font.font("Georgia", 12));
		lastapt.setFont(Font.font("Georgia", 12));
		patientid.setFont(Font.font("Georgia", 12));
		patient_issue.setFont(Font.font("Georgia", 12));
		threat_lvl.setFont(Font.font("Georgia", 12));

		diagnosishistorybutton.setFont(Font.font("Georgia", 12));
		backbutton.setFont(Font.font("Georgia", 12));
		updatebutton.setFont(Font.font("Georgia", 12));

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
		layout2.getChildren().addAll(pidLabel, patientid, firstnamel, firstname, lastnamel, lastname, birthdatel, birthdate, homeaddressl, homeaddress, genderl, gender, phonenumberl, phonenumber, diagnosisl, diagnosis, ssnl, Ssn, lastvisitl, lastapt, patient_iss, patient_issue, threat_level, threat_lvl, diagnosishistorybutton, updatebutton, backbutton);
		Scene Docpatientrecords = new Scene(layout2, 700, 700);

		Docpatientrecords.getStylesheets().add(mainViewController.class.getResource("/application/application.css").toExternalForm());


		window.setScene(Docpatientrecords);
	}

	public static void ViewPatientRecordsRecep(Patient a, Stage window){
		//This is the view patient record method for a receptionist.
		//That means no medical info is displayed
		VBox layout3 = new VBox(10);
		Label firstname = new Label(a.getFirstname()); Label lastname = new Label(a.getLastname()); Label birthdate = new Label((a.getBirthdate()).toString());
		Label homeaddress = new Label(a.getAddress()); Label gender = new Label(a.getGender()); Label phonenumber = new Label(a.getPhoneNumber());
		Label lastapt = new Label((a.getLastVisit()).toString());
		Label patient_issue = new Label(a.getPatient_issue()); Label threat_lvl = new Label(a.getThreat_level());

		//Sets font of labels
		firstnamel.setFont(Font.font("Georgia", 12));
		firstnamel.setStyle("-fx-font-weight: bold");
		lastnamel.setFont(Font.font("Georgia", 12));
		lastnamel.setStyle("-fx-font-weight: bold");
		birthdatel.setFont(Font.font("Georgia", 12));
		birthdatel.setStyle("-fx-font-weight: bold");
		homeaddressl.setFont(Font.font("Georgia", 12));
		homeaddressl.setStyle("-fx-font-weight: bold");
		genderl.setFont(Font.font("Georgia", 12));
		genderl.setStyle("-fx-font-weight: bold");
		phonenumberl.setFont(Font.font("Georgia", 12));
		phonenumberl.setStyle("-fx-font-weight: bold");
		diagnosisl.setFont(Font.font("Georgia", 12));
		diagnosisl.setStyle("-fx-font-weight: bold");
		ssnl.setFont(Font.font("Georgia", 12));
		ssnl.setStyle("-fx-font-weight: bold");
		lastvisitl.setFont(Font.font("Georgia", 12));
		lastvisitl.setStyle("-fx-font-weight: bold");
		patient_iss.setFont(Font.font("Georgia", 12));
		patient_iss.setStyle("-fx-font-weight: bold");
		threat_level.setFont(Font.font("Georgia", 12));
		threat_level.setStyle("-fx-font-weight: bold");

		//Sets font of patient information
		firstname.setFont(Font.font("Georgia", 12));
		lastname.setFont(Font.font("Georgia", 12));
		birthdate.setFont(Font.font("Georgia", 12));
		homeaddress.setFont(Font.font("Georgia", 12));
		gender.setFont(Font.font("Georgia", 12));
		phonenumber.setFont(Font.font("Georgia", 12));
		lastapt.setFont(Font.font("Georgia", 12));
		patient_issue.setFont(Font.font("Georgia", 12));
		threat_lvl.setFont(Font.font("Georgia", 12));

		diagnosishistorybutton.setFont(Font.font("Georgia", 12));
		backbutton.setFont(Font.font("Georgia", 12));
		updatebutton.setFont(Font.font("Georgia", 12));


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
		layout3.getChildren().addAll(firstnamel, firstname, lastnamel, lastname, birthdatel, birthdate, homeaddressl, homeaddress, genderl, gender, phonenumberl, phonenumber, lastvisitl, lastapt, patient_iss, patient_issue, threat_level, threat_lvl, updatebutton, backbutton);
		Scene Receppatientrecords = new Scene(layout3, 700, 850);

		Receppatientrecords.getStylesheets().add(mainViewController.class.getResource("/application/application.css").toExternalForm());


		window.setScene(Receppatientrecords);

	}

	public static void NoPatientFound(Stage window){
		//This is the scene that appears if a search does not return any results
		VBox layout4 = new VBox(10);
		Label noPatientFound = new Label("No patient found");
		noPatientFound.setFont(Font.font("Georgia", 12));
		//backbutton currently goes to the Doctor search.
		//Should be an if statement that calls appropriate search (Doctor or Receptionist)
		backbutton.setOnAction(e-> {
		/*if(loginController.loggedOnUser.getRole().equals("Doctor")){
			SearchPatientController.searchPatientDoc(window);
		}
		else if(loginController.loggedOnUser.getRole().equals("Receptionist")){
			SearchPatientController.searchPatientRecep(window);
		}
		else{*/
			//Fall back for testing, delete once login system is up and running
			SearchPatientController.searchPatientDoc(window);
		//}
		});
		layout4.getChildren().addAll(noPatientFound, backbutton);
		Scene noPatientFoundLayout = new Scene(layout4, 500, 650);
		noPatientFoundLayout.getStylesheets().add(mainViewController.class.getResource("/application/application.css").toExternalForm());
		window.setScene(noPatientFoundLayout);
	}

}
