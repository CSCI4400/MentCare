package controller;

import javax.print.DocFlavor.URL;

import application.MainFXApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Patient;

public class SearchPatientController {

	static Button backbutton = new Button("Back");
	static Button searchbutton = new Button("Search");
	static Label patientidl = new Label("Search for a patient by: ");
	static Patient a = new Patient();
	static String pid; //used to store the ID# of the patient whose record is being looked at
	static String patientsearch = "Search";
	static final ObservableList<String> options =
		    FXCollections.observableArrayList(
		        "Patient ID",
		        "Name",
		        "Address"
		    );
	static ComboBox comboBox = new ComboBox(options);

	public static void searchPatientDoc(Stage window){//This search method is for a doctor, so it
		//calls the method for the patient view that has medical info
		//sets up layout, since the view is dynamic and not contained in an FXML file
		backbutton.setFont(Font.font("Georgia", 15));
		searchbutton.setFont(Font.font("Georgia", 15));
		patientidl.setFont(Font.font("Georgia", 15));

		VBox layout2 = new VBox(20);
		TextField patientidinput = new TextField();

		comboBox.getSelectionModel().selectFirst();
		comboBox.getSelectionModel().getSelectedItem();

		//Validates input in the search textbox, only accepts numerical input
		ChangeListener<String> onlyNumbers = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")){
					patientidinput.setText(newValue.replaceAll("[^\\d]", ""));
				}

			}
		};

		patientidinput.textProperty().addListener(onlyNumbers);

		comboBox.setOnAction(e ->{
			if(comboBox.getSelectionModel().getSelectedItem().equals("Patient ID")){
				patientidinput.textProperty().addListener(onlyNumbers);
			}
			else{
				//Currently doesn't work, going to look at more
				patientidinput.textProperty().removeListener(onlyNumbers);
			}
		});




		backbutton.setOnAction(e-> {
			//back button returns to the main menu
			try {
				AnchorPane mainv = (AnchorPane) FXMLLoader.load(mainViewController.class.getResource("/view/mainView.fxml"));
				Scene scene = new Scene(mainv,600,400);
				window.setScene(scene);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		layout2.getChildren().addAll(patientidl, patientidinput, comboBox, searchbutton, backbutton);
		searchbutton.setOnAction(e -> {
			//gets the patient id number. Currently there is no error checking.
			if(comboBox.getSelectionModel().getSelectedItem().equals("Patient ID")){
				pid = patientidinput.getText();
				//validate input and add searching by fields other than id number here
				a = PatientDAO.getPatientInfo(Integer.parseInt(pid), window);
				a = new Patient();
				a.setPatientnum(Integer.parseInt(pid));
			}

			else if(comboBox.getSelectionModel().getSelectedItem().equals("Name")){
				String name = patientidinput.getText();
				PatientDAO.getPatientInfo(name, window, false);
			}

			else if(comboBox.getSelectionModel().getSelectedItem().equals("Address")){
				String address = patientidinput.getText();
				PatientDAO.getPatientInfo(address, window, true);
			}
				//Calls the static method for displaying patient record info for a doctor.
				//This means that medical info is displayed. Parameters are a patient object and the
				//current stage
				//PatientRecordsController.ViewPatientRecordsDoc(a, window);
		});
		window.setTitle(patientsearch);
		Scene patientsearchDoc = new Scene(layout2, 640, 640);

		patientsearchDoc.getStylesheets().add(mainViewController.class.getResource("/application/application.css").toExternalForm());

		window.setScene(patientsearchDoc);

	}

	public static void searchPatientRecep(Stage window){//This search button is for a receptionist,so
		//it calls the patient view that does not have medical info

		backbutton.setFont(Font.font("Georgia", 15));
		searchbutton.setFont(Font.font("Georgia", 15));
		patientidl.setFont(Font.font("Georgia", 15));

		VBox layout2 = new VBox(20);
		TextField patientidinput = new TextField();

		comboBox.getSelectionModel().selectFirst();
		comboBox.getSelectionModel().getSelectedItem();

		//Validates input in the search textbox, only accepts numerical input
		ChangeListener<String> onlyNumbers = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*")){
						patientidinput.setText(newValue.replaceAll("[^\\d]", ""));
					}

				}
			};

			patientidinput.textProperty().addListener(onlyNumbers);

			comboBox.setOnAction(e ->{
				if(comboBox.getSelectionModel().getSelectedItem().equals("Patient ID")){
					patientidinput.textProperty().addListener(onlyNumbers);
				}
				else{
					//Currently doesn't work, going to look at more
					patientidinput.textProperty().removeListener(onlyNumbers);
				}
			});


		backbutton.setOnAction(e-> {
			//back button returns to the main menu
			try {
				AnchorPane mainv = (AnchorPane) FXMLLoader.load(mainViewController.class.getResource("/view/mainView.fxml"));
				Scene scene = new Scene(mainv,600,400);
				window.setScene(scene);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		layout2.getChildren().addAll(patientidl, patientidinput, searchbutton, backbutton);
		searchbutton.setOnAction(e -> {
			//gets the patient id number. Currently there is no error checking.
			if(comboBox.getSelectionModel().getSelectedItem().equals("Patient ID")){
				pid = patientidinput.getText();
				//validate input and add searching by fields other than id number here
				a = PatientDAO.getPatientInfo(Integer.parseInt(pid), window);
				a = new Patient();
				a.setPatientnum(Integer.parseInt(pid));
			}

			else if(comboBox.getSelectionModel().getSelectedItem().equals("Name")){
				String name = patientidinput.getText();
				PatientDAO.getPatientInfo(name, window, false);
			}

			else if(comboBox.getSelectionModel().getSelectedItem().equals("Address")){
				String address = patientidinput.getText();
				PatientDAO.getPatientInfo(address, window, true);
			}
				//Calls the static method for displaying patient record info for a doctor.
				//This means that medical info is displayed. Parameters are a patient object and the
				//current stage
				//PatientRecordsController.ViewPatientRecordsRecep(a, window);
		});

		window.setTitle(patientsearch);
		Scene patientsearchRecep = new Scene(layout2, 640, 640);

		patientsearchRecep.getStylesheets().add(mainViewController.class.getResource("/application/application.css").toExternalForm());


		window.setScene(patientsearchRecep);

	}

}
