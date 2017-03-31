/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import application.MainFXApp;

import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sad2e
 */
public class BusinessPredictionController implements Initializable {

    Stage stage;
    Scene scene;
    Parent root;
    
    private MainFXApp main;
    
    public void setMain(MainFXApp mainIn){
        main = mainIn;
    }
    
    @FXML private Label weekLabel;
    @FXML private Label monthLabel;
    @FXML private Label yearLabel;
    @FXML private Button historyButton;
    @FXML private Button backButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	String today_date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    	
        weekLabel.setText("today_date");
        monthLabel.setText("Scooby-Doo");
        yearLabel.setText("Scooby-Doo");
        
    } 
    
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
