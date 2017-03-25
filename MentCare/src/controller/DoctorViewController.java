package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DoctorViewController {

    @FXML
    private Button CreatAppointButton;

    @FXML
    private Button AddPatientButton;

    @FXML
    private Button PatientRecordsButton;

    @FXML
    private Button InstitutionPatientButton;

    @FXML
    private Button LogOutButton;

    @FXML
    void AddPatient(ActionEvent event) {

    }

    @FXML
    void CreateAppointment(ActionEvent event) {

    }

    @FXML
    void LogOut(ActionEvent event) {
    	Node node = (Node) event.getSource();
    	Stage primaryStage = (Stage) node.getScene().getWindow();
    	ViewMenuController vmc = new ViewMenuController();
    	try {
			vmc.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @FXML
    void SearchPatient(ActionEvent event) {
    	

    }

    @FXML
    void ViewInstPatients(ActionEvent event) {

    }

}
