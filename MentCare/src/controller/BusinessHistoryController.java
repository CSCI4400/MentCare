/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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



/**
 * FXML Controller class
 *
 * @author sad2e
 */
public class BusinessHistoryController {

    Stage stage;
    Scene scene;
    Parent root;
    
    private MainFXApp main;
    
    public void setMain(MainFXApp mainIn){
        main = mainIn;
    }
    
    @FXML private Button predictionButton;
    @FXML private Button backButton;
    
    @FXML public void buttonClicked(ActionEvent click) throws Exception {
        try{
            stage = (Stage) ((Button) click.getSource()).getScene().getWindow();
        
            String source = ((Node) click.getSource()).getId();
            
            switch (source) {
		case "predictionButton":
                    root = FXMLLoader.load(getClass().getResource("/view/BusinessPrediction.fxml"));
                    BusinessPredictionController act1 = new BusinessPredictionController();
                    act1.setMain(main);
                    break;
                case "backButton":
                    root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
                    patientViewController act2 = new patientViewController();
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
