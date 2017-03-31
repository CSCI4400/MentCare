package controller;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DoctorViewController extends Application {

    @FXML
    private Button CreateAppointmentButton;

    @FXML
    private Button AddPatientButton;

    @FXML
    private Button SearchPatientButton;

    @FXML
    private Button InstitutPatientButton;

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
    	Node node = (Node) event.getSource();
    	Stage primaryStage = (Stage) node.getScene().getWindow();
    	SearchPatientController.searchPatientDoc(primaryStage);
    }

    @FXML
    void ViewInstitutPatient(ActionEvent event) {

    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try{
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/view/DoctorView.fxml"));
			Scene scene = new Scene(root,600,600);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		
	}

}

