package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import application.MainFXApp;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Patient;

public class EditPatientRecordsController {
	
	static Button backbutton = new Button("Back");
	static Button updatebutton = new Button("Update");
	static Label deathlabel = new Label("Is the patient deceased?");
	static RadioButton yesdead = new RadioButton("Yes");
	static RadioButton nodead = new RadioButton("No");
	
	public static Text firstnamel = new Text("First Name:");
	public static Text lastnamel = new Text("Last name:");
	public static Text birthdatel = new Text("Birthdate:");
	public static Text homeaddressl = new Text("Home Address");
	public static Text genderl = new Text("Gender:");
	public static Text phonenumberl = new Text("Phone Number:");
	public static Text diagnosisl = new Text("Diagnosis:");
	public static Text ssnl = new Text("SSN: ");
	public static Text lastvisitl = new Text("Last Visit Was: ");
	static CheckBox tempDiagnosis = new CheckBox("Diagnosis is temporary");
	static String deathCheck = "SELECT mentcare.Patient_Info.Dead FROM mentcare.Patient_Info WHERE mentcare.Patient_Info.PNumber = ?";
	static String setDead = "UPDATE mentcare.Patient_Info SET mentcare.Patient_Info.Dead = 'yes' WHERE PNumber = ? ";
	
	public static void DocEditPatientRecords(Patient a, Stage window){
		VBox layout3 = new VBox(10);

		TextField fname = new TextField(a.getFirstname()); TextField lname = new TextField(a.getLastname());
		TextField birthdate = new TextField((a.getBirthdate()).toString()); TextField addr = new TextField(a.getAddress());
		TextField sex = new TextField(a.getGender()); TextField phonenum = new TextField(a.getPhoneNumber());
		TextField social = new TextField(a.getSsn()); TextField lastapt = new TextField((a.getLastVisit()).toString());
		TextField diago = new TextField(a.getDiagnosis());
		
		final ToggleGroup deathSelect = new ToggleGroup();
		yesdead.setToggleGroup(deathSelect);
		nodead.setToggleGroup(deathSelect);
		nodead.setSelected(true);
		
		try {
			PreparedStatement pstmt = MainFXApp.con.prepareStatement(deathCheck);
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


		updatebutton.setOnAction( e -> {
			if(yesdead.isSelected()){
				try {
					PreparedStatement pstmt = MainFXApp.con.prepareStatement(setDead);
					pstmt.setInt(1, a.getPatientnum());
					pstmt.execute();
					pstmt.close();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			else if(nodead.isSelected()){
				a.updateRecord(fname.getText(), lname.getText(), LocalDate.parse(birthdate.getText()), addr.getText(), sex.getText(), phonenum.getText(), social.getText(), LocalDate.parse(lastapt.getText()), diago.getText(), a.getPatientnum());
				if(tempDiagnosis.isSelected()){ 
					PatientDAO.updatePatientInfo(a, 1);
			}
				else if(!tempDiagnosis.isSelected()){
					PatientDAO.updatePatientInfo(a, 0);
			}
			}
			
			PatientRecordsController.ViewPatientRecordsDoc(a, window);
			});
		
		backbutton.setOnAction(e-> {
			//PatientDAO.updatePatientInfo(a, 0);
			PatientRecordsController.ViewPatientRecordsDoc(a, window);
		});

		

		layout3.getChildren().addAll(firstnamel, fname, lastnamel, lname, birthdatel, birthdate, homeaddressl, addr, genderl, sex, phonenumberl, phonenum, diagnosisl, diago , tempDiagnosis, ssnl, social, lastvisitl, lastapt, deathlabel, yesdead, nodead, updatebutton, backbutton);


		Scene recordeditor = new Scene(layout3, 680, 720);
		window.setScene(recordeditor);
	}
	
	public static void RecepEditPatientRecords(Patient a, Stage window){
		VBox layout3 = new VBox(10);
		
		TextField fname = new TextField(a.getFirstname()); TextField lname = new TextField(a.getLastname()); 
		TextField birthdate = new TextField((a.getBirthdate()).toString()); TextField addr = new TextField(a.getAddress()); 
		TextField sex = new TextField(a.getGender()); TextField phonenum = new TextField(a.getPhoneNumber());
		TextField social = new TextField(a.getSsn()); TextField lastapt = new TextField((a.getLastVisit()).toString());
		TextField diago = new TextField(a.getDiagnosis());
		
		try {
			PreparedStatement pstmt = MainFXApp.con.prepareStatement(deathCheck);
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
		
		
		updatebutton.setOnAction( e -> {
			a.updateRecord(fname.getText(), lname.getText(), LocalDate.parse(birthdate.getText()), addr.getText(), sex.getText(), phonenum.getText(), social.getText(), LocalDate.parse(lastapt.getText()), diago.getText(), a.getPatientnum());
			
			PatientDAO.updatePatientInfo(a, 0);
			PatientRecordsController.ViewPatientRecordsRecep(a, window);
	});
		
		
		backbutton.setOnAction(e-> {
			//PatientDAO.updatePatientInfo(a, 0);
			PatientRecordsController.ViewPatientRecordsRecep(a, window);
		});
		
		layout3.getChildren().addAll(firstnamel, fname, lastnamel, lname, birthdatel, birthdate, homeaddressl, addr, genderl, sex, phonenumberl, phonenum, lastvisitl, lastapt, updatebutton, backbutton);
		
		
		Scene recordeditor = new Scene(layout3, 680, 680);
		window.setScene(recordeditor);
		
	}

}
