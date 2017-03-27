/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import application.HawksoftSprint2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author sad2e
 */
public class SelectionScreenController{
    
    Stage stage;
    Scene scene;
    Parent root;
    
    private HawksoftSprint2 main;
    
    public void setMain(HawksoftSprint2 mainIn){
        main = mainIn;
    }
    
    @FXML private Button addPatient;
    @FXML private Button updatePatient;
    @FXML private Button patientHistory;
    @FXML private Button businessHistory;
    @FXML private Button businessPrediction;
    
    @FXML public void buttonClicked(ActionEvent click) throws Exception {
        try{
            stage = (Stage) ((Button) click.getSource()).getScene().getWindow();
        
            String source = ((Node) click.getSource()).getId();
            
            switch (source) {
		case "addPatient":
                    root = FXMLLoader.load(getClass().getResource("/view/AddPatient.fxml"));
                    AddPatientController act1 = new AddPatientController();
                    act1.setMain(main);
                    break;
		case "updatePatient":
                    root = FXMLLoader.load(getClass().getResource("/view/UpdatePatient.fxml"));
                    UpdatePatientController act2 = new UpdatePatientController();
                    act2.setMain(main);
                    break;
                case "patientHistory":
                    root = FXMLLoader.load(getClass().getResource("/view/InformationHistory.fxml"));
                    InformationHistoryController act3 = new InformationHistoryController();
                    act3.setMain(main);
                    break;
                case "businessHistory":
                    root = FXMLLoader.load(getClass().getResource("/view/BusinessHistory.fxml"));
                    BusinessHistoryController act4 = new BusinessHistoryController();
                    act4.setMain(main);
                    break;
                case "businessPrediction":
                    root = FXMLLoader.load(getClass().getResource("/view/BusinessPrediction.fxml"));
                    BusinessPredictionController act5 = new BusinessPredictionController();
                    act5.setMain(main);
                    break;
		default:
                    break;
            }
            //sets fxml file as a scene
            scene = new Scene(root);
            //loads the scene on top of whatever stage the button is in
            stage.setScene(scene);
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
}
