/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hawksoft;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.text.*;

/**
 *
 * @author sad2e_000
 */
public class NurseLoginController {
    
    Stage stage;
    Scene scene;
    Parent root;
    
    @FXML private TextField nurseID;
    @FXML private TextField nursePassword;
    @FXML private Button login;
    @FXML private Text badCred;
    
    @FXML private void loginSubmit(ActionEvent e) throws IOException{
        ResultSet results = null;
        try{
            Connection conn = DBConfig.getConnection();
            String check = "SELECT Password FROM `Nurse_Cred` WHERE ID = ?";
            PreparedStatement checkPass = conn.prepareStatement(check);
            checkPass.setInt(1,Integer.parseInt(nurseID.getText()));
            results = checkPass.executeQuery();
            results.first();
            String pass = (String) results.getObject("Password");
            if(pass.compareTo(nursePassword.getText()) == 0){
                root = FXMLLoader.load(getClass().getResource("patientSearch.fxml"));
                PatientSearchController cont = new PatientSearchController();
                cont.setMain(main);
            }
            else{
                badCred.setText("Bad Credentials, try again");
            }
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
