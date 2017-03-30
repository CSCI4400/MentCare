package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	public static Text firstnamel = new Text("First Name:");
	public static Text lastnamel = new Text("Last name:");
	public static Text birthdatel = new Text("Birthdate:");
	public static Text homeaddressl = new Text("Home Address");
	public static Text genderl = new Text("Gender:");
	public static Text phonenumberl = new Text("Phone Number:");
	public static Text diagnosisl = new Text("Diagnosis:");
	public static Text ssnl = new Text("SSN: ");
	public static Text lastvisitl = new Text("Last Visit Was: ");
	static String deathCheck = "SELECT mentcare.Patient_Info.Dead FROM mentcare.Patient_Info WHERE mentcare.Patient_Info.PNumber = ?";
	
	public static void ViewPatientRecordsDoc(Patient a, Stage window){
		VBox layout2 = new VBox(10);
		Label firstname = new Label(a.getFirstname()); Label lastname = new Label(a.getLastname()); Label birthdate = new Label((a.getBirthdate()).toString());
		Label homeaddress = new Label(a.getAddress()); Label gender = new Label(a.getGender()); Label phonenumber = new Label(a.getPhoneNumber());
		Label diagnosis = new Label(a.getDiagnosis()); Label Ssn = new Label(a.getSsn()); Label lastapt = new Label((a.getLastVisit()).toString());
		//Bolding all the labels for the patient information
		try {
			PreparedStatement pstmt = ViewMenuController.con.prepareStatement(deathCheck);
			pstmt.setInt(1, a.getPatientnum());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getString("Dead").equals("yes")){
					updatebutton.setDisable(true);
				}
			}
			
			pstmt.close();
			rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		diagnosishistorybutton.setOnAction(e->DiagnosisHistoryView.DiagnosisHistory(a, window));
		backbutton.setOnAction(e->SearchPatientController.searchPatientDoc(window));
		updatebutton.setOnAction(e-> EditPatientRecordsController.DocEditPatientRecords(a, window));
		layout2.getChildren().addAll(firstnamel, firstname, lastnamel, lastname, birthdatel, birthdate, homeaddressl, homeaddress, genderl, gender, phonenumberl, phonenumber, diagnosisl, diagnosis, ssnl, Ssn, lastvisitl, lastapt, diagnosishistorybutton, updatebutton, backbutton);
		Scene Docpatientrecords = new Scene(layout2, 700, 700);
		window.setScene(Docpatientrecords);
	}
	
	public static void ViewPatientRecordsRecep(Patient a, Stage window){
		VBox layout3 = new VBox(10);
		Label firstname = new Label(a.getFirstname()); Label lastname = new Label(a.getLastname()); Label birthdate = new Label((a.getBirthdate()).toString());
		Label homeaddress = new Label(a.getAddress()); Label gender = new Label(a.getGender()); Label phonenumber = new Label(a.getPhoneNumber());
		Label lastapt = new Label((a.getLastVisit()).toString());
		//Bolding all the labels for the patient information
		try {
			PreparedStatement pstmt = ViewMenuController.con.prepareStatement(deathCheck);
			pstmt.setInt(1, a.getPatientnum());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getString("Dead").equals("yes")){
					updatebutton.setDisable(true);
				}
			}
			
			pstmt.close();
			rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		backbutton.setOnAction(e->SearchPatientController.searchPatientRecep(window));
		updatebutton.setOnAction(e-> EditPatientRecordsController.RecepEditPatientRecords(a, window));
		layout3.getChildren().addAll(firstnamel, firstname, lastnamel, lastname, birthdatel, birthdate, homeaddressl, homeaddress, genderl, gender, phonenumberl, phonenumber, lastvisitl, lastapt, updatebutton, backbutton);
		Scene Receppatientrecords = new Scene(layout3, 700, 700);
		window.setScene(Receppatientrecords);
		
	}

}
