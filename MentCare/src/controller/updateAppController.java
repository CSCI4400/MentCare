package controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.DBConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class updateAppController {

	Stage stage;
	Scene scene;
	Parent root;
	@FXML private Label statusLabel;
    @FXML private TabPane tabPane;
    @FXML private Button goButton;
    @FXML private Button cancelButton;
    @FXML private Button submitButton;
    @FXML private Label FnameLabel;
    @FXML private Label LnameLabel;
    @FXML private Label DocIDLabel;
    @FXML private Label apTimeLabel;
    @FXML private Label apDateLabel;
    @FXML private ComboBox<String> apTimeField;
    @FXML private DatePicker apDateField;
    @FXML private TextField DocIDField;
    @FXML private TextField PnumField;
    @FXML private TextField providerTF;
    @FXML private TextField FnameField;
    @FXML private TextField LnameField;
    @FXML private Label AppIDLabel;
    
    public void initialize() {
    	apTimeField.getItems().addAll(
    			"8","9","10","11","12","1","2","3","4","5"
    			);
    	// SET BUTTON TO DISABLED BECAUSE ANNA HATES USERS
    	submitButton.setDisable(true);
    	
    	// Only enable submit once all fields have a value *STILL KINDA BUGGY*
    	FnameField.setOnAction((event) -> {
    		if(checkFields() && submitButton.isDisabled()) submitButton.setDisable(false);
    	});
    	LnameField.setOnAction((event) -> {
    		if(checkFields() && submitButton.isDisabled()) submitButton.setDisable(false);
    	});
    	DocIDField.setOnAction((event) -> {
    		if(checkFields() && submitButton.isDisabled()) submitButton.setDisable(false);
    	});
    	apTimeField.setOnAction((event) -> {
    		if(checkFields() && submitButton.isDisabled()) submitButton.setDisable(false);
    	});
    	apDateField.setOnAction((event) -> {
    		if(checkFields() && submitButton.isDisabled()) submitButton.setDisable(false);
    	}); 
    }
    
    @FXML
	void ClickCancelButton(ActionEvent event) throws Exception {
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
	}
    
    
    @FXML
	void ClickGoButton(ActionEvent event) {
		try (
				Connection conn = DBConfig.getConnection();
				Statement statement = conn.createStatement();
				ResultSet RS = statement.executeQuery("select * from mentcare.Current_Appointment where PNumber=" + PnumField.getText() + ";");
				) // End try-with-res
		{
	    	
	    	if(RS != null){
	    		while (RS.next()) {
	  		      FnameLabel.setText(RS.getString("Fname"));
	  		      LnameLabel.setText(RS.getString("Lname"));
	  		      DocIDLabel.setText(RS.getString("DocID"));
	  		      apDateLabel.setText(RS.getString("apDate"));
	  		      apTimeLabel.setText(RS.getString("apTime"));
	  		      AppIDLabel.setText(Integer.toString((RS.getInt("AppID"))));
	    		}
	    		statusLabel.setText("Status: Appointment Found");
	    	} // End if
		} catch (SQLException e) {
				DBConfig.displayException(e);
		}// End Catch
		
    } // End Method

    @FXML
    void ClickSubmitUpdateButton(ActionEvent event) {
    	int AppID;
		String Pnum, Fname, Lname, apDate, apTime, DocID;
    	Pnum = PnumField.getText();
		Fname = FnameField.getText();
		Lname = LnameField.getText();
		apDate = apDateField.getValue().toString();
		apTime = apTimeField.getValue();
		DocID = DocIDField.getText();
		AppID = Integer.parseInt(AppIDLabel.getText());

		String query = ("UPDATE mentcare.Current_Appointment SET Fname = ?, Lname = ?, DocID = ?, apDate = ?, apTime = ? WHERE AppID = ?");
		
		
    	try (Connection conn = DBConfig.getConnection();
    			PreparedStatement statement = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);)
    	{
    		statement.setString(1, Fname);
    		statement.setString(2, Lname);
    		statement.setString(3, DocID);
    		statement.setString(4, apDate);
    		statement.setString(5, apTime);
    		statement.setInt(6, AppID);
    		statement.executeUpdate();
        } catch (SQLException e) {
    		DBConfig.displayException(e);
    		e.printStackTrace();
    	}
    	resetLabels();

    } // End submit method
    
    void resetLabels() {
    	// Helper method, clears fields and labels
    	statusLabel.setText("Status: Update complete.");
		FnameLabel.setText("");
		LnameLabel.setText("");
		FnameField.setText("");
		LnameField.setText("");
		PnumField.setText("");
		DocIDLabel.setText("");
		apDateLabel.setText("");
		apTimeLabel.setText("");
		apDateField.setValue(null);
		apTimeField.setValue(null);
		DocIDField.setText("");
		
    } // End resetLabels method
    
    boolean checkFields() {
    	// Helper method, returns true if all fields have some value (DOES NOT YET CHECK THE VALUE, DAMN USERS!)
    	if (
    			!PnumField.getText().isEmpty() &&
    			!FnameField.getText().isEmpty() &&
    			!LnameField.getText().isEmpty() &&
    			!DocIDField.getText().isEmpty() &&
    			!(apDateField == null) &&
    			!(apTimeField.getValue() == null)
    			) { return true; }
     	return false;
    } // End check fields method
     
} // End class


