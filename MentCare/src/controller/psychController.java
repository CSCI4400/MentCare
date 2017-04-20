//fixed the back button- Anna 4/15/17
//Added ability to update psych notes Robert 4/19/17

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
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
	//creates lists for psychologist
	static List<Psychologist> list = new ArrayList<Psychologist>();
	@FXML static ObservableList<Psychologist> appList = FXCollections.observableList(list);
	static ArrayList<Psychologist> tempList = new ArrayList<Psychologist>();
	
	//sets main in Main.java 
	public void setMain(MainFXApp mainIn)
	{
		main = mainIn;
	}
	//retrives and displays doctor notes
	@FXML
	void ClickSearchButton(ActionEvent event) throws Exception{
		System.out.println("Search Button pressed.");
		try{
			tempList.clear();
    		String enterPnum = PnumTF.getText();
    		
    		String query = ("select * from mentcare2.Psych_Notes where Pnum='" + enterPnum + "'"); //Grabs all columns based on Pnum textfield.
    		Connection conn = DBConfig.getConnection();
    		Statement statement = conn.createStatement();
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
  		      		tempList.add(psych);
      		    }
        		//Updates table in psychView
        		PNumCol.setCellValueFactory(cellData -> cellData.getValue().getPNumber());
                DocNumCol.setCellValueFactory(cellData -> cellData.getValue().getDocID());
                PsychNoteCol.setCellValueFactory(cellData -> cellData.getValue().getPsychNotes());
                System.out.println(tempList);
                System.out.println(appList);
                psychTable.setItems(appList);
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
	    	//css for missed alert box
	    	dialogPane.setStyle("-fx-background-image: url(application/mentcare_bg.jpg);"//TODO for UI committee
	    					  + "-fx-font-size: 15px;"
	    					  + "-fx-mid-text-color: #010a66;"
	    					  + "-fx-font-family: georgia;");
	    	alert.setTitle("Detailed Patient Information");
	    	
	    	//gets the currently selected row patient name and contact number from the GUI table then displays it
	    	alert.setHeaderText(psychTable.getSelectionModel().getSelectedItem().getPNumber().getValue()); 
	    					
	    	alert.setContentText(psychTable.getSelectionModel().getSelectedItem().getDocID().getValue());;
	    	
	    	ButtonType UpdateBtn = new ButtonType("Update");
	    	ButtonType OKBtn = new ButtonType("OK");
	    	//creates text area
	    	TextArea notes = new TextArea();
	    	notes.setText(psychTable.getSelectionModel().getSelectedItem().getPsychNotes().getValue());
	    	//makes text area editable for the user to type in input
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
	    	//waits for button to be clicked to perform an action -> stores in variable
	    	alert.getButtonTypes().setAll(OKBtn,UpdateBtn);
	    	
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if(result.get() == OKBtn)
	    	{
    		alert.hide();
	    	}
	    	//updates psychnotes based on patient number and doctor ID
	    	else if (result.get()==UpdateBtn){
	    		System.out.println("insert");
	    		String pnum=alert.getHeaderText();
	    		String docid=alert.getContentText();
	    		String notesUpdateQuery = "UPDATE mentcare2.Psych_Notes SET NOTES = ? WHERE Pnum='"+pnum+"' and DocID='"+docid+"'";
	    		try{ 
	    			
	    			Connection conn = DBConfig.getConnection();
                     
                   
                     PreparedStatement PreparedStatement = conn.prepareStatement(notesUpdateQuery);
                     PreparedStatement.setString(1, notes.getText());
         
                     PreparedStatement.execute();
                     PreparedStatement.close();
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}
	    	}
	    		
	    	}
    	}
	}

