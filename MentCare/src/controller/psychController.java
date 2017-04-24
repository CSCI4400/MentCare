//fixed the back button- Anna 4/15/17
//Added ability to create Patients Robert 4/21/17
//Needs CSS styling
//alert for failed entry now enabled. 

//Robert 4/23/17
//catch block still prints stack trace, but pop-up displays error message/reprompts users.
//Added prepared statement to searchButton
//ReAdded labels to searchButton. They should now show when users input invalid/nonexistent users


package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import application.DBConfig;
import application.MainFXApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import model.Psychologist;

public class psychController {
	MainFXApp main = new MainFXApp();
	//needed for changing views
	Parent root;
	Stage stage;
	Scene scene;
	//links FXML ids
	@FXML private Button searchBtn;
	@FXML private TableView<Psychologist> psychTable;
	@FXML private TableColumn<Psychologist, String> PsychNoteCol;
	@FXML private TableColumn<Psychologist, String> PNumCol;
	@FXML private TableColumn<Psychologist, String> DocNumCol;
	@FXML private TextField PnumTF;
	@FXML private Button backBtn;
	@FXML private Label lblErrInvalidInput;
	@FXML private Label lblErrUserNotFound;
	//creates lists for psychologist
	static List<Psychologist> list = new ArrayList<Psychologist>();
	@FXML static ObservableList<Psychologist> appList = FXCollections.observableList(list);
	@FXML private Button btnCreate;
	//gets which role the user is
	private String type = loginController.loggedOnUser.getID().substring(0, 3);
	
	//userd to grab doctor id for database entry.
	private String doc = loginController.loggedOnUser.getID();
	
	//sets main in Main.java 
	public void setMain(MainFXApp mainIn)
	{
		main = mainIn;
	}

	//sets permissions for users
	public void initialize() throws Exception{
		//permissions set by type
		if(type.equals("333")){
			btnCreate.setVisible(false);
		}else if(type.equals("555")){
			btnCreate.setVisible(true);
		}else{
			System.out.println("Invalid identification number for button function.");
		}
	}
	
	//retrieves and displays doctor notes
	@FXML
	void ClickSearchButton(ActionEvent event) throws Exception{
		System.out.println("Search Button pressed.");
		System.out.println("Search Button pressed.");
		lblErrInvalidInput.setText("");
		lblErrUserNotFound.setText("");
		
		try{
			appList.clear();
    		String enterPnum = PnumTF.getText();
    		if(PnumTF.getText().matches("[0-9]+"))
    		{
    		String query = ("select * from mentcare2.Psych_Notes, where Pnum=?"); //Grabs all columns based on Pnum textfield.
    		
    		Connection conn = DBConfig.getConnection();
    		PreparedStatement statement = conn.prepareStatement(query);
    		statement.setString(1,enterPnum);
    		
        	ResultSet RS = null;
        	String Pnum = null, DocID = null, PsychNotes = null;

        	//List<Psychologist> list = new ArrayList<Psychologist>();
        	//ObservableList<Psychologist> appList = FXCollections.observableList(list);

        	//this will execute the String 'query' exactly as if you were in SQL console
        	//and it returns a result set which contains everything we want, but we need to decode it first
        	RS = statement.executeQuery(query);
        	//if the query goes through, RS will no longer be null
        		while (RS.next()){	
        			//ToDo Create Database tables with mentioned parameters.
        			Pnum = Integer.toString(RS.getInt("Pnum"));
        			DocID = RS.getString("DocID");
        			PsychNotes = RS.getString("Notes");
        			System.out.println("Pnum : " + Pnum + "\nDocID: " + DocID + "\nPsychNotes: " + PsychNotes);
        			Psychologist psych = new Psychologist(Pnum, DocID, PsychNotes);
  		      		appList.add(psych);
      		    }
        		if(Pnum==null)
            	{	lblErrUserNotFound.setText("No User Exists.");
            		System.out.println("No such user.");
            		
            	}
        		//Updates table in psychView
        		PNumCol.setCellValueFactory(cellData -> cellData.getValue().getPNumber());
                DocNumCol.setCellValueFactory(cellData -> cellData.getValue().getDocID());
                PsychNoteCol.setCellValueFactory(cellData -> cellData.getValue().getPsychNotes());
                System.out.println(appList);
                psychTable.setItems(appList);
    		}
    		//whitelisted numeric input, if any other character is found then error label appears.
    		else
    		{
    			lblErrInvalidInput.setText("Invalid Input. Please Enter Patient number.");
    			System.out.println("Invalid Input.");
    		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//takes user back to main view page
	@FXML
	void ClickBackBtn (ActionEvent event) throws Exception{
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
	}
	@FXML
    public void mouseClicked(MouseEvent event) {
    	int selectedIndex = psychTable.getSelectionModel().getSelectedIndex();
    	if(selectedIndex >= 0){
    		Alert alert = new Alert(AlertType.INFORMATION);
	    	DialogPane dialogPane = alert.getDialogPane();
	    	//css for info alert box
	    	dialogPane.getStylesheets().add(
	    			   getClass().getResource("/application/application.css").toExternalForm());
	    	dialogPane.getStyleClass().add("alert");
	    	alert.setTitle("Detailed Patient Information");
	    	
	    	//gets the currently selected row patient name and contact number from the GUI table then displays it
	    	alert.setHeaderText("Patient ID: " + psychTable.getSelectionModel().getSelectedItem().getPNumber().getValue()); 
	    					
	    	alert.setContentText("Doctor: " + psychTable.getSelectionModel().getSelectedItem().getDocID().getValue());;
	    	
	    	ButtonType UpdateBtn = new ButtonType("Update");

	    	//creates text area
	    	TextArea notes = new TextArea();
	    	notes.setText(psychTable.getSelectionModel().getSelectedItem().getPsychNotes().getValue());
	    	
	    	//makes text area editable for the doctor to make changes
	    	notes.setEditable(true);
	    	notes.setWrapText(true);
	    	//sets grid dimensions
	    	notes.setMaxWidth(Double.MAX_VALUE);
	    	notes.setMaxHeight(Double.MAX_VALUE);
	    	GridPane.setVgrow(notes, Priority.ALWAYS);
	    	GridPane.setHgrow(notes, Priority.ALWAYS);
	    	//places objects on grid
	    	GridPane expContent = new GridPane();
	    	expContent.setMaxWidth(Double.MAX_VALUE);
	    	expContent.add(notes, 0, 0);	
	    	//makes dialog box expandable -> default is not
	    	alert.getDialogPane().setExpandableContent(expContent);
	    	//expands window to view full text area -> hides automatically if this is not set
	    	alert.getDialogPane().setExpanded(true);
	    	
	    	if(type.equals("333")){//nurses
		    	Optional<ButtonType> result = alert.showAndWait();
	    		if(result.get() == ButtonType.OK)
		    	{
		    		alert.hide();
		    	}else{
		    		System.out.println("Invalid identification number for nurse function.");
		    	}
	    	}else if(type.equals("555")){//doctors
	    		//waits for button to be clicked to perform an action -> stores in variable
		    	alert.getButtonTypes().setAll(UpdateBtn);
		    	
		    	Optional<ButtonType> result = alert.showAndWait();
	    		if(result.get() == ButtonType.OK)
		    	{
		    		alert.hide();
		    	}else if (result.get() == UpdateBtn){//updates psychnotes based on patient number and doctor ID
		    		//The following strings hold the information from the selected column.
		    		//must use this format to avoid grabbing "Doctor" from header.
		    		String pnum = psychTable.getSelectionModel().getSelectedItem().getPNumber().getValue(); 
		    		String docid = psychTable.getSelectionModel().getSelectedItem().getDocID().getValue();
		    		String notesUpdateQuery = "UPDATE mentcare2.Psych_Notes SET NOTES = ? WHERE Pnum=? and DocID=?";
		    		
		    		try{ 
		    			Connection conn = DBConfig.getConnection();
		    			
	                    PreparedStatement PreparedStatement = conn.prepareStatement(notesUpdateQuery);
	                    PreparedStatement.setString(1, notes.getText());
	                    PreparedStatement.setString(2, pnum);
	                    PreparedStatement.setString(3, docid);
	                   
	                     PreparedStatement.execute();
	                    PreparedStatement.close();
	                    alert.close();
	                    //used to reset search results. It is slow though.
	                    ClickSearchButton(null);
	                    
		    		}catch(Exception e){
		    			e.printStackTrace();
		    			System.out.println("Connection error");
		    		}
		    	}else{
		    		System.out.println("Invalid identification number for doctor function.");
		    	}
	    	}
	    }
    }
	//allows doctors to create new notes about their patients
	@FXML
	void newNotes(ActionEvent event) throws Exception{
		//for Robert - pop up space
		System.out.println("Notes go HERE, Robert!");
		// Create the custom dialog.
		Dialog dialog = new Dialog();
		
		DialogPane dialogPane = dialog.getDialogPane();
		//css for info alert box
    	dialogPane.getStylesheets().add(
    			   getClass().getResource("/application/application.css").toExternalForm());
    	dialogPane.getStyleClass().add("alert");
    	
		dialog.setTitle("Create Patient Notes");
		dialog.setHeaderText("Enter Patient Info Below");

		// Set the icon (must be included in the project).
		//dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

		// Set the button types.
		ButtonType createBtn = new ButtonType("Create", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(createBtn, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 90, 10, 10));

		TextField pnum = new TextField();
		pnum.setPromptText("Patient Number");
		TextArea notes = new TextArea();
		notes.setPromptText("Enter Patient Notes Here");

		grid.add(new Label("Patient ID:"), 0, 0);
		grid.add(pnum, 1, 0);
		grid.add(new Label("Notes:"), 0, 1);
		grid.add(notes, 1, 1);

	
		dialog.getDialogPane().setContent(grid);

		notes.setEditable(true);
		notes.setWrapText(true);
		//sets grid dimensions
		//notes.setMaxWidth(Double.MAX_VALUE);
		//notes.setMaxHeight(Double.MAX_VALUE);
		//GridPane.setVgrow(notes, Priority.ALWAYS);
		//GridPane.setHgrow(notes, Priority.ALWAYS);
		    	
		    	
		grid.setMaxWidth(Double.MAX_VALUE);
		Optional result = dialog.showAndWait();
		//makes dialog box expandable -> default is not
		String notesInsert = "INSERT INTO mentcare2.Psych_Notes (Pnum, DocID, Notes) VALUES (?,?,?)";
		    System.out.println(doc);
		//expands window to view full text area -> hides automatically if this is not set
		dialog.getDialogPane().setExpanded(true);
		if (result.get() == createBtn){
			System.out.println("inserted");
			try{ 
    			Connection conn = DBConfig.getConnection();
    			
                PreparedStatement PreparedStatement = conn.prepareStatement(notesInsert);
                PreparedStatement.setString(1, pnum.getText());
                PreparedStatement.setString(2, doc);
                PreparedStatement.setString(3, notes.getText());
               
                PreparedStatement.execute();
                PreparedStatement.close();
                dialog.close();
                //used to reset search results. It is slow though.
                //ClickSearchButton(null);
                
    		}catch(Exception e){
    			//print stack trace for developers
    			e.printStackTrace();
    		
    			
    			Alert failure = new Alert(AlertType.ERROR);
    			//safely catches error by pop-up alert.
    			failure.setContentText("Patient name doesn't exist. Please try again.");
    			Optional<ButtonType> error = failure.showAndWait();
    		}
			
			
			
		}
		else if(result.get() == ButtonType.OK){
			dialog.close();
		}

		
		
	}//end method
}//end class