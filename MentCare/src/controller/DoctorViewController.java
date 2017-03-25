package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.TimeoutTimer;

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
    	try {
    		Node node = (Node) event.getSource();
			GridPane SearchView = (GridPane) FXMLLoader.load(getClass().getResource("/view/PatientIDSearchWindow.fxml"));
			Scene scene2 = new Scene(SearchView, 600, 600);
			Stage primaryStage = (Stage) node.getScene().getWindow();
			primaryStage.setScene(scene2);
			primaryStage.show();
			TimeoutTimer timeout = new TimeoutTimer(SearchView, primaryStage, 10); //This method is overloaded; if you only use two arguments the time defaults
			timeout.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @FXML
    void ViewInstPatients(ActionEvent event) {
    	

    }

}
