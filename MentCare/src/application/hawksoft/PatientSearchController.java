/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hawksoft;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sad2e_000
 */
public class PatientSearchController {
    
    Stage stage;
    Scene scene;
    Parent root;
    
    @FXML private TextField fName;
    @FXML private TextField lName;
    @FXML private Button searchPatients;
    
    @FXML private void patientSearch(ActionEvent e){
        try{
            Connection conn = DBConfig.getConnection();
            String check = "SELECT FName,LName,BDate,Sex,Phone_Number FROM `Personal_Info` WHERE FName,LName = ?,?";
            PreparedStatement checkPass = conn.prepareStatement(check);
            checkPass.setString(1,fName.getText());
            checkPass.setString(2,lName.getText());
        }catch(SQLException ex){
            ex.printStackTrace();
        }
         
    }
    
    private Hawksoft main;
    public void setMain(Hawksoft mainIn)
    {
        main = mainIn;
    }

}
