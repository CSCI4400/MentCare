package controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Patient;

public class SearchPatientController {
	
	static Button backbutton = new Button("Back");
	static Button searchbutton = new Button("Search");
	static Label patientidl = new Label("What is the Patient ID Number?");
	static Patient a = new Patient();
	static String pid; //used to store the ID# of the patient whose record is being looked at
	static String patientsearch = "Search";
	
	public static void searchPatientDoc(Stage window){
		VBox layout2 = new VBox(20);
		TextField patientidinput = new TextField();
		DocViewController docView = new DocViewController();
		backbutton.setOnAction(e-> {
			try {
				docView.start(window);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		layout2.getChildren().addAll(patientidl, patientidinput, searchbutton, backbutton);
		searchbutton.setOnAction(e -> {
			pid = patientidinput.getText();
			a = PatientDAO.getPatientInfo(Integer.parseInt(pid), 0, window);
			a = new Patient();
			a.setPatientnum(Integer.parseInt(pid));
			//These strings represents the prepared statements that will be executed to retrieve the patient info from the database
			//Feeds the results obtained from the database to the 'patientrecords' menu
			PatientRecordsController.ViewPatientRecordsDoc(a, window);
		});
		window.setTitle(patientsearch);
		Scene patientsearchDoc = new Scene(layout2, 640, 640);

		window.setScene(patientsearchDoc);
		
	}
	
	public static void searchPatientRecep(Stage window){
		VBox layout2 = new VBox(20);
		TextField patientidinput = new TextField();
		RecepViewController RView = new RecepViewController();
		backbutton.setOnAction(e-> {
			try {
				RView.start(window);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		layout2.getChildren().addAll(patientidl, patientidinput, searchbutton, backbutton);
		searchbutton.setOnAction(e -> {
			pid = patientidinput.getText();
			a = PatientDAO.getPatientInfo(Integer.parseInt(pid), 1, window);
			a = new Patient();
			a.setPatientnum(Integer.parseInt(pid));
			//These strings represents the prepared statements that will be executed to retrieve the patient info from the database
			//Feeds the results obtained from the database to the 'patientrecords' menu
			PatientRecordsController.ViewPatientRecordsRecep(a, window);
		});
		window.setTitle(patientsearch);
		Scene patientsearchRecep = new Scene(layout2, 640, 640);
		
		window.setScene(patientsearchRecep);
		
	}

}
