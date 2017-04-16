//fixed the back button- Anna 4/15/17

package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import application.DBConfig;
import application.MainFXApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    		//need to set common column names for database.
    		//PNumber may be subject to change
    		String query = ("select * from mentcare.Psych_Notes where Pnum='" + enterPnum + "'"); //Grabs all columns based on Pnum textfield.
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
}

