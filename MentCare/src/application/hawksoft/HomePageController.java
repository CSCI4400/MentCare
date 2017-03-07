/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.hawksoft;

import java.io.IOException;
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
 * @author sad2e_000
 */
public class HomePageController {
    
    Stage stage;
    Scene scene;
    Parent root;
    private Hawksoft main;
    
    public void setMain(Hawksoft mainIn)
    {
	main = mainIn;
    }
    
    @FXML private Button nurseLogin;
    
    @FXML private void buttonClick(ActionEvent event) throws IOException {
        //finds what stage the button is in
	stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        //this gets the name of button
	String temp = ((Node) event.getSource()).getId().toString();
        switch(temp){
            case "nurseLogin":
                root = FXMLLoader.load(getClass().getResource("nurseLogin.fxml"));
                NurseLoginController cont = new NurseLoginController();
                cont.setMain(main);
		break;
            default:
                root = FXMLLoader.load(getClass().getResource("homePage.fxml"));
                HomePageController contr = new HomePageController();
                contr.setMain(main);
		break;
        }
        //sets fxml file as a scene
	scene = new Scene(root);
	//loads the scene on top of whatever stage the button is in
	stage.setScene(scene);
    }  

}
