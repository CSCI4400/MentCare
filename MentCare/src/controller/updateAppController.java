package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.MainFXApp;
import application.DBConfig;
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
    @FXML private Label fNameL;
    @FXML private Label lNameL;
    @FXML private Label addressL;
    @FXML private Label countyL;
    @FXML private Label stateL;
    @FXML private Label zipL;
    @FXML private Label phoneL;
    @FXML private TextField voteridTF;
    @FXML private TextField fNameTF;
    @FXML private TextField lNameTF;
    @FXML private TextField addressTF;
    @FXML private TextField countyTF;
    @FXML private TextField stateTF;
    @FXML private TextField zipTF;
    @FXML private TextField phoneTF;

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
    	String vID = voteridTF.getText();
    	//debugging
    	System.out.println("ClickGoButton vID = " + vID);

    	fNameL.setText("");
	    lNameL.setText("");
	    addressL.setText("");
	    countyL.setText("");
	    stateL.setText("");
	    zipL.setText("");
	    phoneL.setText("");
	    statusL.setText("Status: ");

		try{
		Connection conn = DBConfig.getConnection();
		Statement statement = conn.createStatement();
    	ResultSet RS = null;
    	String query = ("select * from voter where voterid=" + vID);
    	//this will execute the String 'query' exactly as if you were in SQL console
    	//and it returns a result set which contains everything we want, but we need to decode it first
    	RS = statement.executeQuery(query);
    	//if the query goes through, RS will no longer be null
    	if(RS != null){
    		while (RS.next()) {
  		      // I am getting the data in each column by using the column name directly
  		      fNameL.setText(RS.getString("firstName"));
  		      lNameL.setText(RS.getString("lastName"));
  		      addressL.setText(RS.getString("address"));
  		      countyL.setText(RS.getString("county"));
  		      stateL.setText(RS.getString("state"));
  		      zipL.setText(Integer.toString(RS.getInt("zipcode")));
  		      phoneL.setText(Integer.toString(RS.getInt("phone")));
  		    }
		}
    	//this part below just lets the user know that voter id doesn't exist or isn't valid
    	if(fNameL.getText() == ""){
    		statusL.setText("Invalid Voter ID!");
    	}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			statusL.setText("Invalid Voter ID!");
		}
    }

    @FXML
    void ClickSubmitUpdateButton(ActionEvent event) {
    	//debugging
    	System.out.println("ClickSubmitUpdateButton (updateVoterController)");

    	try{
    		Connection conn = DBConfig.getConnection();
    		Statement statement = conn.createStatement();
        	int RS = 0;
        	//gets values from user
        	String vID, firstName, lastName, address, zipCode,state,county,  phoneNumber;
        	vID = voteridTF.getText();
    		firstName = fNameTF.getText();
    		lastName = lNameTF.getText();
    		address = addressTF.getText();
    		zipCode = zipTF.getText();
    		county = countyTF.getText();
    		state = stateTF.getText();
    		phoneNumber = phoneTF.getText();

    		// checks to see if these values are nul or invalid. If there are then the current value is retained
    		if (firstName == null || firstName == "" || firstName.trim().isEmpty()) {
    			firstName = fNameL.getText();
    		}
    		if (lastName == null || lastName == "" || lastName.trim().isEmpty()) {
    			lastName = lNameL.getText();
    		}
    		if (address == null || address == "" || address.trim().isEmpty()) {
    			address = addressL.getText();
    		}
    		if (state == null || state == "" || state.trim().isEmpty() || state.length() != 2) {
    			state = stateL.getText();
    		}
    		if (county == null || county == "" || county.trim().isEmpty()) {
    			county = countyL.getText();
    		}
    		if (zipCode == null || zipCode == "" || zipCode.trim().isEmpty() || zipCode.length() != 5) {
    			zipCode = zipL.getText();
    		}
    		if (phoneNumber == null || phoneNumber == "" || phoneNumber.trim().isEmpty() || phoneNumber.length() != 10) {
    			phoneNumber = phoneL.getText();
    		}

    		//I am putting together a String that is basically exactly how you would code it
    		//into the console in XAMMP or MySQL
    		String update = ("update `ivoter`.`voter` set `firstName`=");
    		update = (update+ "'" +
    				firstName+ "', `lastName`='" +
    				lastName+ "', `address`='" +
    				address+ "', `county`='" +
    				county+ "', `state`='" +
    				state+ "', `zipcode`='" +
    				zipCode+ "', `phone`='" +
    				phoneNumber+ "' WHERE `voterId`='" +
    				vID + "'");
    		//debugging
    		System.out.println("Update = " + update);

    		// this will shove the string update into the SQL database
    		// and execute it exactly as if it were in the console
    		RS = statement.executeUpdate(update);
    		// statement.executeUpdate() will return a 1 if the DB actually adds 1 new voter
    		// and will return 0 if it doesn't. Probably also generating errors too.
    		if (RS == 1){
    			statusL.setText("Status: Voter updated successfully!");
    		}
    		//this will update the text labels
    		ClickGoButton(event);
        } catch (Exception e) {
    		statusL.setText("Status: operation failed due to: " + e.getMessage());
    		System.out.println(e.getMessage());
    	}


    }


}