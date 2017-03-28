package controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Patient;

public class PatientSearchController {

	static Button searchbutton = new Button("Search");
	static Button editrecordbutton = new Button("Edit Record");
	static Button backbutton = new Button("Bacl");
	static String pid;
	static Label patientidl = new Label("What is the Patient ID Number?");
	static Patient a;

	public static void searchPatient(Stage window){
		VBox layout2 = new VBox(20);
		TextField patientidinput = new TextField();
		backbutton.setOnAction(e-> window.setScene(DoctorViewController.mainmenu));
		layout2.getChildren().addAll(patientidl, patientidinput, searchbutton, backbutton);
		searchbutton.setOnAction(e -> {
			pid = patientidinput.getText();
			a = PatientDAO.getPatientInfo(Integer.parseInt(pid), 0);
			a = new Patient();
			a.setPatientnum(Integer.parseInt(pid));
			//These strings represents the prepared statements that will be executed to retrieve the patient info from the database
			//Feeds the results obtained from the database to the 'patientrecords' menu
			//PatientRecordsController.ViewPatientRecords(a, DoctorViewController.diagnosishistorybutton, backbutton, editrecordbutton, window, firstnamel, lastnamel, birthdatel, homeaddressl, genderl, phonenumberl, diagnosisl, ssnl, lastvisitl);
		});
		window.setTitle("Search");
		Scene patientsearch= new Scene(layout2, 640, 640);

		window.setScene(patientsearch);

	}

}
