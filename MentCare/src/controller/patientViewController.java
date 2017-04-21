package controller;

import application.MainFXApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class patientViewController {

	Stage stage;
	Scene scene;
	Parent root;
	// always reference main method, and build constructor
	private MainFXApp main;

	public void setMain(MainFXApp mainIn) {
		main = mainIn;
	}

	@FXML
	private Button addButton;
	@FXML
	private Button historyButton;
	@FXML
	private Button searchPatientButton;
	//Button added to fxml by TigerWooos
	@FXML
	private Button patientListButton;
	//button added to fxml by Butterscotch
	@FXML
    private Button psychButton;


	@FXML
	void ClickButton(ActionEvent event) throws Exception {
		try {
			// finds what stage the button is in
			stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			// this gets the name of button
			String temp = ((Node) event.getSource()).getId().toString();
			// debugging
			System.out.println("(Node) event.getSource()).getId().toString() = " + temp);
			// this will allow for all buttons to go through one method, loading
			// the
			// right fxml file

			switch (temp) {
			case "addButton":
				root = FXMLLoader.load(getClass().getResource("/view/AddPatient.fxml"));
				AddPatientController con1 = new AddPatientController();
				con1.setMain(main);
				break;
			case "historyButton":
				root = FXMLLoader.load(getClass().getResource("/view/InformationHistory.fxml"));
				InformationHistoryController con3 = new InformationHistoryController();
				con3.setMain(main);
				break;
			case "searchPatientButton":
				//Currently searches as if the person using is a doctor.
				//Add code here to call search for a receptionist or search for a doc based on
				//who is logged in. The method for receptionist is also static and is called
				// earchPatientRecep. It takes the same argument (the current stage) and the
				//back button on both search methods returns to the main menu.
				if(loginController.loggedOnUser.equals("Doctor")){
					SearchPatientController.searchPatientDoc(stage);
				}
				else if(loginController.loggedOnUser.equals("Receptionist")){
					SearchPatientController.searchPatientRecep(stage);
				}
				else{
					//Fall back for testing, delete or comment out once login system is in place
					SearchPatientController.searchPatientDoc(stage);
				}
				break;
				//CKS button loads patient list created by TigerWoods
			case "patientListButton":
				root = FXMLLoader.load(getClass().getResource("/view/PatientListView.fxml"));
				InformationHistoryController con4 = new InformationHistoryController();
				con4.setMain(main);
				break;
			case "psychButton":
				//added by Butterscotch
				root = FXMLLoader.load(getClass().getResource("/view/psychView.fxml"));
				psychController con6 = new psychController();
				con6.setMain(main);
				break;
			case "Pprescription":
				//added by Team5
				root = FXMLLoader.load(getClass().getResource("/view/Doctor.fxml"));
				psychController con7 = new psychController();
				con7.setMain(main);
				break;
			default:
				root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
				mainViewController con5 = new mainViewController();
				con5.setMain(main);
				break;
			}
			// sets fxml file as a scene
			scene = new Scene(root);
			// loads the scene on top of whatever stage the button is in
			stage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
