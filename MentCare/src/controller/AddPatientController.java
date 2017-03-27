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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author sad2e
 */
public class AddPatientController {
    
    Stage stage;
    Scene scene;
    Parent root;
    
    private HawksoftSprint2 main;
    
    public void setMain(HawksoftSprint2 mainIn){
        main = mainIn;
    }
    
    @FXML private Button addButton;
    @FXML private Button backButton;
    @FXML private TextField firstField;
    @FXML private TextField lastField;
    @FXML private TextField birthField;
    @FXML private TextField addressField1;
    @FXML private TextField addressField2;
    @FXML private ChoiceBox sexChoice;
    @FXML private TextField phoneField;
    
    
    @FXML public void onAddPatient(ActionEvent click) throws Exception {
        try{
            stage = (Stage) ((Button) click.getSource()).getScene().getWindow();
        
            String source = ((Node) click.getSource()).getId();
            
            switch (source) {
		case "addButton":
                    root = FXMLLoader.load(getClass().getResource("/view/AddPatient.fxml"));
                    AddPatientController act1 = new AddPatientController();
                    act1.setMain(main);
                    break;
                case "backButton":
                    root = FXMLLoader.load(getClass().getResource("/view/SelectionScreen.fxml"));
                    SelectionScreenController act2 = new SelectionScreenController();
                    act2.setMain(main);
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