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
 * FXML Controller class
 *
 * @author sad2e
 */
public class BusinessPredictionController {

    Stage stage;
    Scene scene;
    Parent root;
    
    private HawksoftSprint2 main;
    
    public void setMain(HawksoftSprint2 mainIn){
        main = mainIn;
    }
    
    @FXML private Button historyButton;
    @FXML private Button backButton;
    
    @FXML public void buttonClicked(ActionEvent click) throws Exception {
        try{
            stage = (Stage) ((Button) click.getSource()).getScene().getWindow();
        
            String source = ((Node) click.getSource()).getId();
            
            switch (source) {
		case "historyButton":
                    root = FXMLLoader.load(getClass().getResource("/view/BusinessHistory.fxml"));
                    BusinessHistoryController act1 = new BusinessHistoryController();
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