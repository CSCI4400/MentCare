/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import application.DBConfig;
import application.HawksoftSprint2;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
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
                    try{
                        if(!firstField.getText().trim().equals("") &&
                                !lastField.getText().trim().equals("") &&
                                !birthField.getText().trim().equals("") &&
                                !addressField1.getText().trim().equals("") &&
                                !sexChoice.getValue().equals("") &&
                                !(phoneField.getText().trim().equals("") ||
                                phoneField.getText().trim().length() != 12)){
                            String first = firstField.getText().trim();
                            String last = lastField.getText().trim();
                            String birth = birthField.getText().trim();
                            String addr = addressField1.getText().trim().concat(" ").
                                    concat(addressField2.getText().trim());
                            String sex = sexChoice.getValue().toString();
                            String phNum = phoneField.getText().trim();
                            
                            String patQuery = "INSERT INTO `Patient_Info`(`FNAME`, `LName`, `BDate`, `Address`, `Sex`,`Phone_Number`,`Dead`) "
                                + "VALUES (?,?,?,?,?,?,?)";
                            
                            Connection conn = DBConfig.getConnection();
                            PreparedStatement addPat = conn.prepareStatement(patQuery,Statement.RETURN_GENERATED_KEYS);
                            
                            addPat.setString(1, first);
                            addPat.setString(2, last);
                            addPat.setDate(3, Date.valueOf(birth));
                            addPat.setString(4, addr);
                            addPat.setString(5, sex);
                            addPat.setString(6, phNum);
                            addPat.setString(7,"no");
                            
                            System.out.println("Query Sent" + addPat.toString());
                            
                            int accepted  = addPat.executeUpdate();
                            
                            if(accepted == 1){
                                root = FXMLLoader.load(getClass().getResource("/view/AddPatient.fxml"));
                                AddPatientController act1 = new AddPatientController();
                                act1.setMain(main);
                                break;
                            }
                            else{
                                System.out.println("Query failed");
                                root = FXMLLoader.load(getClass().getResource("/view/AddPatient.fxml"));
                                AddPatientController act1 = new AddPatientController();
                                act1.setMain(main);
                                break;
                            }
                            
                        }
                        else{
                            break;
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    
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
