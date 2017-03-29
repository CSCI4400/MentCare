package controller;

import java.time.LocalDate;

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

	public static void DocEditPatientRecords(Patient a, Stage window){
		VBox layout3 = new VBox(10);

		TextField fname = new TextField(a.getFirstname()); TextField lname = new TextField(a.getLastname());
		TextField birthdate = new TextField((a.getBirthdate()).toString()); TextField addr = new TextField(a.getAddress());
		TextField sex = new TextField(a.getGender()); TextField phonenum = new TextField(a.getPhoneNumber());
		TextField social = new TextField(a.getSsn()); TextField lastapt = new TextField((a.getLastVisit()).toString());
		TextField diago = new TextField(a.getDiagnosis());


		updatebutton.setOnAction( e -> {
			a.updateRecord(fname.getText(), lname.getText(), LocalDate.parse(birthdate.getText()), addr.getText(), sex.getText(), phonenum.getText(), social.getText(), LocalDate.parse(lastapt.getText()), diago.getText(), a.getPatientnum());
			if(tempDiagnosis.isSelected()){ 
				PatientDAO.updatePatientInfo(a, 1);
			}
			
			else if(!tempDiagnosis.isSelected()){
				PatientDAO.updatePatientInfo(a, 0);
			}
			
			PatientRecordsController.ViewPatientRecordsDoc(a, window);
			});
		
		backbutton.setOnAction(e-> {
			//PatientDAO.updatePatientInfo(a, 0);
			PatientRecordsController.ViewPatientRecordsDoc(a, window);
		});

		final ToggleGroup deathSelect = new ToggleGroup();
		yesdead.setToggleGroup(deathSelect);
		nodead.setToggleGroup(deathSelect);
		nodead.setSelected(true);

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
		
		
		updatebutton.setOnAction( e -> {
			/* if(tempDiagnosis.isSelected()){ //Add code here for handling status of Diagnosis in database
			 * ifSelected will be true when checkbox is selected (Diagnosis is temporary) and false when 
			 * checkbos is not selected (Diagnosis is not temporary)
			}*/
			a.updateRecord(fname.getText(), lname.getText(), LocalDate.parse(birthdate.getText()), addr.getText(), sex.getText(), phonenum.getText(), social.getText(), LocalDate.parse(lastapt.getText()), diago.getText(), a.getPatientnum());
			//new Thread(a).start();
			
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
