/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hawksoft;


import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author sad2e_000
 */
public class UpdatePatientController implements Initializable {
    
    @FXML private Text submitted;
    
    @FXML private void submitChange(ActionEvent e) throws Exception{
        try{
            String pat = "SELECT FName,LName,BDate, Sex, Address, Phone_Number, Danger_lvl FROM `Personal_Info` WHERE PNumber = ?";
            
            Connection conn = DBConfig.getConnection();
            
            submitted.setText("Changes submitted successfully");
        }catch(SQLException ex){
            
        }
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
