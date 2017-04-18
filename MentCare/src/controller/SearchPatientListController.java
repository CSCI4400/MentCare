package controller;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Patient;

public class SearchPatientListController {

	static Button backbutton = new Button("Back");
	static Button selectbutton = new Button("Select");
	static Patient c;
	static int pnum;

	public static void displaySearchResults(ArrayList<Patient> searchResults, Stage window){

		backbutton.setFont(Font.font("Georgia", 15));

		selectbutton.setFont(Font.font("Georgia", 15));

		final ToggleGroup patients = new ToggleGroup();

		ArrayList<RadioButton> rbList = new ArrayList<RadioButton>();

		VBox searchResultslayout = new VBox(10);
		Label searchResulttitle = new Label("Search Results: ");


		int index = 0;

		for(Patient p : searchResults){
			RadioButton rb = new RadioButton("Patient ID Number: " + p.getPatientnum() + "\t" + "Name: " + p.getFirstname() + " " + p.getLastname());
			rb.setFont(Font.font("Georgia", 15));
			rbList.add(rb);
			rb.setUserData(p.getPatientnum());
			rb.setToggleGroup(patients);
			System.out.println(p.getLastname());
		}

		for(RadioButton r: rbList){
			searchResultslayout.getChildren().add(r);
		}


		RadioButton selected = rbList.get(0);
		selected.setSelected(true);

		backbutton.setOnAction(e -> {

			rbList.clear();
			searchResults.clear();

			//Calls appropriate search to return to based on who is logged in.
			//Currently commented out since login system is disabled

			/*if(loginController.loggedOnUser.getRole().equals("Doctor")){
				SearchPatientController.searchPatientDoc(window);
			}

			else if(loginController.loggedOnUser.getRole().equals("Doctor")){
				SearchPatientController.searchPatientRecep(window);
			}

			else{*/
				SearchPatientController.searchPatientDoc(window);
			//}


		});


		selectbutton.setOnAction( e -> {
			for(RadioButton r: rbList){
				if(r.isSelected()){
					pnum = (int) r.getUserData();
				}
			}

			for(Patient p: searchResults){
				if(p.getPatientnum() == pnum){
					c = p;
				}
			}

			rbList.clear();
			searchResults.clear();

			/*if(loginController.loggedOnUser.getRole().equals("Doctor")){
				PatientRecordsController.ViewPatientRecordsDoc(c, window);
			}

			else if(loginController.loggedOnUser.getRole().equals("Receptionist")){
				PatientRecordsController.ViewPatientRecordsRecep(c, window);
			}*/

			PatientRecordsController.ViewPatientRecordsDoc(c, window);

		});

		searchResultslayout.getChildren().add(selectbutton);
		searchResultslayout.getChildren().add(backbutton);

		Scene patientSearchResults = new Scene(searchResultslayout, 500, 500);
		patientSearchResults.getStylesheets().add(mainViewController.class.getResource("/application/application.css").toExternalForm());
		window.setScene(patientSearchResults);
	}

}
