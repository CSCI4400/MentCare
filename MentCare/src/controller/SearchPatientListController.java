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
import javafx.stage.Stage;
import model.Patient;

public class SearchPatientListController {

	static Button backbutton = new Button("Back");
	static Button selectbutton = new Button("Select");
	static Patient c;
	static String patientInfo;

	public static void displaySearchResults(ArrayList<Patient> searchResults, Stage window){

		final ToggleGroup patients = new ToggleGroup();

		ArrayList<RadioButton> rbList = new ArrayList<RadioButton>();

		VBox searchResultslayout = new VBox();
		Label searchResulttitle = new Label("Search Results: ");
		for(Patient p : searchResults){
			RadioButton rb = new RadioButton("Patient ID Number: " + p.getPatientnum() + "\t" + "Name: " + p.getFirstname() + " " + p.getLastname());
			rb.setToggleGroup(patients);
			rbList.add(rb);
			searchResultslayout.getChildren().add(rb);

		}

		RadioButton selected = rbList.get(0);
		selected.setSelected(true);

		backbutton.setOnAction(e -> {

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
					patientInfo = r.getText();
				}
			}
			System.out.println(patientInfo);

		});

		searchResultslayout.getChildren().add(selectbutton);
		searchResultslayout.getChildren().add(backbutton);

		Scene patientSearchResults = new Scene(searchResultslayout, 500, 500);
		patientSearchResults.getStylesheets().add(mainViewController.class.getResource("/application/application.css").toExternalForm());
		window.setScene(patientSearchResults);
	}

}
