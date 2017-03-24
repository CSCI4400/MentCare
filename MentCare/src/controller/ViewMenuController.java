package controller;

import java.io.IOException;
import java.sql.Connection;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.InitialDBConnection;

public class ViewMenuController extends Application {
	
	public static Connection con;
	
	public static void main (String[] args){
		
		InitialDBConnection idb = new InitialDBConnection();
		Thread t = new Thread(idb);
		t.start();
		launch(args);
		
	}

    @FXML
    private Button BMView;

    @FXML
    private Button RecepView;

    @FXML
    private Button DocView;

    @FXML
    void switchToBMView(ActionEvent event) {
    	try {
    		Node node = (Node) event.getSource();
			AnchorPane BMView = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/BusinessManagerView.fxml"));
			Scene scene2 = new Scene(BMView, 600, 600);
			Stage primaryStage = (Stage) node.getScene().getWindow();
			primaryStage.setScene(scene2);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @FXML
    void switchToDocView(ActionEvent event) {

    }

    @FXML
    void switchToRecepView(ActionEvent event) {

    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		try{
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ViewMenu.fxml"));
		Scene scene = new Scene(root,600,600);
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
