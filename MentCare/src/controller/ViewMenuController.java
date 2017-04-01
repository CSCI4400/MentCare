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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.DBConnection;
import model.TimeoutTimer;

public class ViewMenuController extends Application {
	
	//public static Connection con;
	static TimeoutTimer timeout;
	
	public static void main (String[] args){
		
		
		DBConnection idb = new DBConnection();
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
			GridPane BMView = (GridPane) FXMLLoader.load(getClass().getResource("/view/BusinessManagerView.fxml"));
			Scene scene2 = new Scene(BMView, 600, 600);
			Stage primaryStage = (Stage) node.getScene().getWindow();
			primaryStage.setScene(scene2);
			primaryStage.show();
			timeout = new TimeoutTimer(BMView, primaryStage, 10); //This method is overloaded; if you only use two arguments the time defaults to 120 seconds
			timeout.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @FXML
    void switchToDocView(ActionEvent event) {
    	try {
    		Node node = (Node) event.getSource();
			Stage primaryStage = (Stage) node.getScene().getWindow();
			//DoctorViewController docview = new DoctorViewController();
			//docview.start(primaryStage);
    		BorderPane DocView = (BorderPane) FXMLLoader.load(getClass().getResource("/view/DoctorView.fxml"));
    		Scene scene3 = new Scene(DocView, 600, 600);
    		primaryStage.setScene(scene3);
    		primaryStage.show();
    		timeout = new TimeoutTimer(DocView, primaryStage, 10); //This method is overloaded; if you only use two arguments the time defaults to 120 seconds
			timeout.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @FXML
    void switchToRecepView(ActionEvent event) {
    	try {
    		Node node = (Node) event.getSource();
			Stage primaryStage = (Stage) node.getScene().getWindow();
			//ReceptionistViewController recepview = new ReceptionistViewController();
			//recepview.start(primaryStage);
			BorderPane RecepView = (BorderPane) FXMLLoader.load(getClass().getResource("/view/ReceptionistView.fxml"));
			Scene scene4 = new Scene(RecepView, 600, 600);
			primaryStage.setScene(scene4);
			primaryStage.show();
			timeout = new TimeoutTimer(RecepView, primaryStage, 10); //This method is overloaded; if you only use two arguments the time defaults to 120 seconds
			timeout.start();
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		try{
		GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("/view/ViewMenu.fxml"));
		Scene scene = new Scene(root,600,600);
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		try {
			timeout.stop(); //Stop if timeout has been initialized
		} catch (Exception e) {} //Otherwise, do nothing
	}

}
