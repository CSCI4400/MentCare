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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author sad2e
 */
public class UpdatePatientController {
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
                    root = FXMLLoader.load(getClass().getResource("/view/UpdatePatient.fxml"));
                    UpdatePatientController act1 = new UpdatePatientController();
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
	public void updatePatient(){
    	try{
    		Connection conn = DBConfig.getConnection();
            	Statement stmt = conn.createStatement();
            
    		String fName = firstField.getText();
    		String lName = lastField.getText();
    		String bDate = birthField.getText();
    		String address = addressField1.getText();
    		String sex = sexChoice.getValue().toString();
    		String pNumber = phoneField.getText();
    	
    		String query = "UPDATE `Personal_Info` SET `LName` =" + lName + ", `FName` =" + fName + ", `BDate` = " + bDate + ", `Address` = " + address + ", `Sex` = " + sex + 
    			", `Phone_Number` = " + pNumber + "WHERE `FName` = " + fName + " AND `LName` = " + lName;
    		
    		stmt.executeUpdate(query);
    	}
    	catch (SQLException e){
    		
    	}
    }
}
