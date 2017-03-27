/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import controller.SelectionScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sad2e
 */
public class HawksoftSprint2 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        try{
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/SelectionScreen.fxml"));
        
            Scene scene = new Scene(root);
        
            stage.setScene(scene);
        
            SelectionScreenController controller = new SelectionScreenController();
        
            controller.setMain(this);
        
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
	}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
