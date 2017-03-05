package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.MainFXApp;
import application.DBConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;

public class updateAppController {

	Stage stage;
	Scene scene;
	Parent root;

	//always reference main method, and build constructor
	private MainFXApp main;
	public void setMain(MainFXApp mainIn)
	{
	main=mainIn;
	}

	//This is all the stuff that needs to be worked with.
	@FXML private Label statusL;
    @FXML private TabPane tabPane;
    @FXML private Button goButton;
    @FXML private Button cancelButton;
    @FXML private Button submitButton;
    @FXML private Label pNameL;
    @FXML private Label providerL;
    @FXML private TextField pNumTF;
    @FXML private TextField providerTF;
    @FXML private TextField pNameTF;
    
    @FXML
	void ClickCancelButton(ActionEvent event) throws Exception {
    	//finds what stage the button is in
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		//gets some fxml file
		root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
		//sets fxml file as a scene
		scene = new Scene(root);
		//loads the scene on top of whatever stage the button is in
		stage.setScene(scene);
	}
    
    
    @FXML
	void ClickGoButton(ActionEvent event) {
    	String pID = pNumTF.getText();
    	pNameL.setText("");
	    statusL.setText("Status: ");
		try (Connection conn = DBConfig.getConnection()){
		Statement statement = conn.createStatement();
    	ResultSet RS = null;
    	String query = ("select * from mentcare.Current_Appointment where Pnum="+pID+';');
    	RS = statement.executeQuery(query);
    	if(RS != null){
    		while (RS.next()) {
  		      pNameL.setText(RS.getString("Pname"));
  		      // providerL.setText(RS.getString("DocID"));
  		      // Put other attributes here
  		    }
		}
    	// this part below just lets the user know that patient id doesn't exist or isn't valid
    	if(pNameL.getText() == ""){
    		statusL.setText("Invalid patient ID!");
    	}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			statusL.setText("Invalid patient ID!");
		}
    }

    @FXML
    void ClickSubmitUpdateButton(ActionEvent event) {
    	//debugging
    	// System.out.println("ClickSubmitUpdateButton");

    	try{
    		Connection conn = DBConfig.getConnection();
    		Statement statement = conn.createStatement();
        	// gets values from user
        	String pID, pName;
        	pID = pNumTF.getText();
    		pName = pNameTF.getText();

    		// checks to see if these values are null or invalid. If there are then the current value is retained
    		if (pName == null || pName == "" || pName.trim().isEmpty()) {
    			pName = pNameL.getText();
    		}

    		// Needs to be a prepared statement
    		String update = ("update mentcare.Current_Appointment set Pname=");
    		update = (update+  "'" + pName + "' where Pnum = " + pID + ";");
    		statement.executeUpdate(update);
    		statusL.setText("Status: Update complete.");
    		conn.close();
    		
        } catch (SQLException e) {
    		DBConfig.displayException(e);
    	}


    }


}