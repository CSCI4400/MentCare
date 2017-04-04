/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import application.DBConfig;
import application.MainFXApp;
import java.net.URL;
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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import model.Patient;

/**
 *
 * @author sad2e
 */
public class UpdatePatientController implements Initializable {
    Stage stage;
    Scene scene;
    Parent root;
    
    private MainFXApp main;
    
    public void setMain(MainFXApp mainIn){
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
    @FXML private TextField soc;
    @FXML private TextField diagnosis;
    @FXML private ChoiceBox<Patient> patients;
    
    private ArrayList<Patient> patientList = new ArrayList<Patient>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            String whoAreYou = ("SELECT LName,FName,BDate FROM Personal_Info");
            Connection connect = DBConfig.getConnection();
            PreparedStatement ps = connect.prepareStatement(whoAreYou);
            ResultSet results = ps.executeQuery();
            
            String lname, fname;
            Date bdate;
            
            while(results.next()){
                lname = results.getString("LName");
                fname = results.getString("FName");
                bdate = results.getDate("BDate");
                
                LocalDate date = bdate.toLocalDate();
                
                Patient temp = new Patient();
                temp.setFirstname(fname);
                temp.setLastname(lname);
                temp.setBirthdate(date);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
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
