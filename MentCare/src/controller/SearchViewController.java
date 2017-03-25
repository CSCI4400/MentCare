package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Patient;

public class SearchViewController {
	
	static String pid;
	static Patient a = new Patient();

    @FXML
    private TextField PIDSearchBar;

    @FXML
    private Button SearchButton;

    @FXML
    private Button BackButton;

    @FXML
    void GoBack(ActionEvent event) {

    }

    @FXML
    void SearchPatient(ActionEvent event) {
    	pid = PIDSearchBar.getText();
		a = PatientDAO.getPatientInfo(Integer.parseInt(pid), 0);
		//These strings represents the prepared statements that will be executed to retrieve the patient info from the database
		//Feeds the results obtained from the database to the 'patientrecords' menu
		//patientrecords(a);

    }

}
