package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.MainFXApp;
import application.DBConfig;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Appointment;

public class displayAppController {

	Stage stage;
	Scene scene;
	Parent root;

	//always reference main method, and build constructor
	private MainFXApp main;
	public void setMain(MainFXApp mainIn)
	{
	main=mainIn;
	}

	@FXML  private TableView<Appointment> patientTable;
	@FXML  private TableColumn<Appointment, String> tAppNum;
    @FXML  private TableColumn<Appointment, String> PnumCol;
    @FXML  private TableColumn<Appointment, String> PnameCol;
    @FXML  private TableColumn<Appointment, String> DocIDCol;
    @FXML  private TableColumn<Appointment, String> DateCol;
    @FXML  private TableColumn<Appointment, String> TimeCol;
    @FXML  private TableColumn<Appointment, String> tpassed;
    @FXML  private TableColumn<Appointment, String> MissedCol;
    @FXML  private TableColumn<Appointment, String> tPhoneCol;
    @FXML private Button btnCancel = new Button();
    @FXML private CheckBox cbWeek = new CheckBox();

    @FXML private Button cancelButton;
    @FXML private Button displayButt;
    @FXML private Button displayAllButt;
    @FXML private Label fNameL;
    @FXML private Label lNameL;
    @FXML private Label addressL;
    @FXML private Label locationL;
    @FXML private Label zipL;
    @FXML private Label phoneL;
    @FXML private TextField DateTF;
    static List<Appointment> list = new ArrayList<Appointment>();
    static ObservableList<Appointment> appList = FXCollections.observableList(list);
    static ArrayList<Appointment> tempList = new ArrayList<Appointment>();

    //Danni<start>----------------------------------------------------------------------------------------------------------------
    @FXML private RadioButton rbAttend;
    @FXML private RadioButton rbMiss;
    @FXML private ToggleGroup attended;

    @FXML
    void mouseClicked(MouseEvent event) {
    	int selectedIndex = patientTable.getSelectionModel().getSelectedIndex();
    	if(selectedIndex >= 0){
    		rbAttend.setDisable(false);
    		rbMiss.setDisable(false);
    	}
    }

    @FXML
    public void AttendAppointment(ActionEvent event) throws Exception{
    	try{
	    	int selectedIndex = patientTable.getSelectionModel().getSelectedIndex();
			String dateTemp = tempList.get(selectedIndex).getApDateString();
			String timeTemp = tempList.get(selectedIndex).getApTimeString();
			Connection conn = DBConfig.getConnection();
			Statement statement = conn.createStatement();
	    	if(attended.getSelectedToggle() == rbAttend){
		 		//pass from current to past appointment
		 		String currToPass = ("INSERT INTO `mentcare`.`Previous_Appointment`(AppID, Pnum, Pname, DocID, apDate, apTime) "
		 				+ "SELECT AppID, Pnum, Pname, DocID, apDate, apTime FROM Current_Appointment WHERE `apDate`='" + dateTemp
		 				+ "' AND `apTime`='" + timeTemp + "';");
		   		System.out.println(currToPass);
			 	statement.execute(currToPass);
			 	//delete from current appointment
	    		String delcurrAttend = ("DELETE FROM `mentcare`.`Current_Appointment` WHERE `apDate`='" + dateTemp +
	   				 "' AND `apTime`='" + timeTemp + "';");
	   		 	System.out.println(delcurrAttend);
		 		statement.execute(delcurrAttend);
	    	}else if(attended.getSelectedToggle() == rbMiss){
		 		//pass from current to miss appointment
		 		String currToMiss = ("INSERT INTO `mentcare`.`Missed_Appointment`(AppID, Pnum, Pname, DocID, apDate, apTime) "
		 				+ "SELECT AppID, Pnum, Pname, DocID, apDate, apTime FROM Current_Appointment WHERE `apDate`='" + dateTemp
		 				+ "' AND `apTime`='" + timeTemp + "';");
		   		System.out.println(currToMiss);
			 	statement.execute(currToMiss);
			 	//delete from current appointment
			 	String delcurAtten = ("DELETE FROM `mentcare`.`Current_Appointment` WHERE `apDate`='" + dateTemp +
		   				 "' AND `apTime`='" + timeTemp + "';");
		   		System.out.println(delcurAtten);
		   		statement.execute(delcurAtten);
	    	}else{
	    		System.out.println("Failed to send current to past or missed appointment db tables.");
	    	}
	    	patientTable.getItems().remove(selectedIndex);
	    	//deselect radio buttons after use
	 		rbAttend.setSelected(false);
		 	rbMiss.setSelected(false);
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    }
    //<end>-----------------------------------------------------------------------------------------------------------------------
    @FXML
    public void CancelAppointment(ActionEvent event) throws Exception{
    	try{
	    	int selectedIndex = patientTable.getSelectionModel().getSelectedIndex();

	    	if(selectedIndex >= 0){
	    		 patientTable.getItems().remove(selectedIndex);
	    		 String dateTemp = tempList.get(selectedIndex).getApDateString();
	    		 String timeTemp = tempList.get(selectedIndex).getApTimeString();
	    		 String stateString = ("DELETE FROM `mentcare`.`Current_Appointment` WHERE `apDate`='" + dateTemp +
	    				 "' AND `apTime`='" + timeTemp + "';"); //that is for the date in textfield!
	    		 System.out.println(stateString);
		 		 Connection conn = DBConfig.getConnection();
		 		 Statement statement = conn.createStatement();
		 		 statement.execute(stateString);
	    	}
	    	else
	    	{
	    	/*	// Nothing selected. Popup needs many of the same stuff as a basic window
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(((Window) event.getSource()).getScene().getWindow());
	            alert.setTitle("No Selection");
	            alert.setHeaderText("No Person Selected");
	            alert.setContentText("Please select a person in the table.");

	            alert.showAndWait();*/
	    	}


    	} catch(Exception e){
    		e.printStackTrace();
    	}


    }

    @FXML
    void ClickCancelButton(ActionEvent event) throws Exception {

    	CancelAppointment(event);

    }

    @FXML
	void ClickBackButton(ActionEvent event) throws Exception {
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
	    	String enterDate = DateTF.getText();
			try{
				boolean valid = true;
		    	//yyyy-mm-dd
		    	Pattern p1 = Pattern.compile("([^0-9-])", Pattern.CASE_INSENSITIVE);

		    	Matcher bDate = p1.matcher(enterDate);
		    	boolean Dat = bDate.find();

		    	if(enterDate.equals("")){
		    		System.out.println("Empty Field");
		    		valid = false;
		    	}
		    	int count = 0;
		    	for(int i = 0; i < enterDate.length(); i++){
		    		if(enterDate.charAt(i) == '-')
		    			count++;
		    	}

		    	if(enterDate.length() != 10)
		    	{
		    		valid = false;
		    		System.out.println("NOT A DATE"); //Replace with Label warning
		    	}
		    	else
		    	{

		    			if(Dat)
		    			{
		    				System.out.println("NOT A DATE");
		    				valid = false;
		    			}
		    			else if(!enterDate.equals("") && count != 2)
		    			{
		    				System.out.println("NOT A DATE");
		    				valid = false;
		    			}
		    			else
		    			{
		    				String[] parts = enterDate.split("-");
		    				String part1 = parts[0];
		    				String part2 = parts[1];
		    				String part3 = parts[2];
		    				int y = Integer.parseInt(part1);
		    				int mon = Integer.parseInt(part2);
		    				int d = Integer.parseInt(part3);


		    				if(!enterDate.equals("") && enterDate.charAt(4) != '-' || enterDate.charAt(7) != '-'){
		    					System.out.println("Invalid Date: bad format");
		    					valid = false;
		    				}
		    				else if(!enterDate.equals("") && mon  > 12 || mon < 1 || d > 31 || d < 1 || y < 2017){
		    					System.out.println("Invalid Date: ");
		    					valid = false;
		    				}
		    				else if(!enterDate.equals("") && mon == 2 && d > 29){
		    					System.out.println("Invalid Date: February out of range");
		    					valid = false;
		    				}
		    				else if(!enterDate.equals("") && mon == 4 || mon == 6 || mon == 9 || mon == 11 && d > 30){
		    					System.out.println("Invalid Date: out of range for month");
		    					valid = false;
		    				}

		    			}
		    		}

		    if(valid){


			tempList.clear();
			appList.clear();
		    String query = ("select * from mentcare.Current_Appointment where apDate='" + enterDate + "'"); //that is for the date in textfield!
		    Connection conn = DBConfig.getConnection();
			Statement statement = conn.createStatement();
	    	ResultSet RS = null;
	    	String AppNum = null, Pnum = null, Pname = null, phoneNum = null,
	    			DocID = null, apDate = null,  apTime = null, passed = null, missed;
	    	String[] date = new String[7];
	    	if(cbWeek.isSelected())
	    	{
	    		boolean first = true;
	    		String month = enterDate.substring(5,  7);
	    		int day = Integer.parseInt(enterDate.substring(8, 10)); //2017-01-31
	    		int lastDay = -1;
	    		int numMonth = Integer.parseInt(month);
	    		int numYear = Integer.parseInt(enterDate.substring(0, 4));
	    		 //March april june september november
		    	 switch(month){
		    	 case "01":
		    	 case "05":
		    	 case "07":		//31 days
		    	 case "08":
		    	 case "10":
		    	 case "12":
		    		 lastDay = 31;
		    		 break;
		    	 case "03":
		    	 case "04":		//30 days
		    	 case "06":
		    	 case "09":
		     	 case "11":
		     		lastDay = 30;
		     		 break;

		     	 case "02":		//28 days
		     		lastDay = 28;
		    	 default:
		    	 }
		    	 int j;
		    	 for(int i = day; i<day + 7; i++)
	    		 {
	    			 j = i;

	       		 if(i > lastDay) //We are in the next month
	    		 {
	    			 if(first)
	    			 {
	    				 numMonth++;
	    				 first = false;
	    			 }
	    			 if(numMonth > 12)
	    			 {
	    				 numMonth = 1;
	    				 numYear++;
	    			 }
	    			 j = j - lastDay;
	    			 if(numMonth < 10 && numMonth+1 < 10)
	    				 date[i - day] = Integer.toString(numYear)+"-"+"0"+Integer.toString(numMonth);
	    			 else
	    				 date[i - day] = Integer.toString(numYear)+"-"+Integer.toString(numMonth);

	    			 if(j < 10)
	    				 date[i - day] += "-0"+Integer.toString(j);
	    			 else
	    				 date[i - day] += "-"+Integer.toString(j);
	    		 }
	    		 else
	    		 {
	    			 if(numMonth < 10)
	    				 date[i - day] = enterDate.substring(0, 5)+"0"+Integer.toString(numMonth);
	    			 else
	    				 date[i - day] = enterDate.substring(0, 5)+Integer.toString(numMonth);

	    			 if(j < 10)
	    				 date[i - day] += "-0"+Integer.toString(j);
	    			 else
	    				 date[i - day] += "-"+Integer.toString(j);


	    		 }
	    		 System.out.println(date[i-day]);
	    		 }
	    	}

	    	String queryV = ("select * from mentcare.Current_Appointment where ");
	    	for(int p = 0; p<date.length; p++){
	    		if(p!=(date.length-1)){
	    			queryV  = queryV + ("apDate='"+ date[p] +"' or ");
	    		} else{
	    			queryV  = queryV + ("apDate='"+ date[p] +"';");
	    		}

	    	}
	    	//Debugging
	    	System.out.println(queryV);

	    	//this will execute the String 'query' exactly as if you were in SQL console
	    	//and it returns a result set which contains everything we want, but we need to decode it first
	    	if(!cbWeek.isSelected()){
	    		RS = statement.executeQuery(query);
	    	} else{
	    		RS = statement.executeQuery(queryV);
	    	}
	    	//if the query goes through, RS will no longer be null
    		while (RS.next()) {

    		AppNum = Integer.toString(RS.getInt("AppID"));
  		    Pnum = Integer.toString(RS.getInt("Pnum"));
    		Pname = RS.getString("Pname");
    		DocID = (RS.getString("DocID"));
    		apDate = (RS.getString("apDate"));
    		apTime = RS.getString("apTime");
    		passed = Integer.toString(RS.getInt("passed"));
    		missed = Integer.toString(RS.getInt("missed"));
    		phoneNum = RS.getString("pPhone");

    		Appointment temp = new Appointment(AppNum, Pnum, Pname, DocID, apDate, apTime, passed, missed, phoneNum);

		      appList.add(temp);
		      tempList.add(temp);

  		    }

    		//tAppNum.setCellValueFactory(cellData -> cellData.getValue().getAppNum());
    		PnumCol.setCellValueFactory(cellData -> cellData.getValue().getPnum());
            PnameCol.setCellValueFactory(cellData -> cellData.getValue().getPname());
            DocIDCol.setCellValueFactory(cellData -> cellData.getValue().getDocID());
            DateCol.setCellValueFactory(cellData -> cellData.getValue().getApDate());
            TimeCol.setCellValueFactory(cellData -> cellData.getValue().getApTime());
            //tpassed.setCellValueFactory(cellData -> cellData.getValue().getPassed());
            MissedCol.setCellValueFactory(cellData -> cellData.getValue().getMissed());
            tPhoneCol.setCellValueFactory(cellData -> cellData.getValue().getPhone());
            patientTable.setItems(appList);
		    }
			} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @FXML
	void ClickDisplayAllButton(ActionEvent event) throws Exception {
    	System.out.println("Display All Button pressed");
    	try{
    		tempList.clear();
    		String query = ("select * from mentcare.Current_Appointment");
    		Connection conn = DBConfig.getConnection();
    		Statement statement = conn.createStatement();
        	ResultSet RS = null;
        	String AppNum = null, Pnum = null, Pname = null, phoneNum = null,
        			DocID = null, apDate = null,  apTime = null, passed = null, missed;

        	List<Appointment> list = new ArrayList<Appointment>();
        	ObservableList<Appointment> appList = FXCollections.observableList(list);

        	//this will execute the String 'query' exactly as if you were in SQL console
        	//and it returns a result set which contains everything we want, but we need to decode it first
        	RS = statement.executeQuery(query);
        	//if the query goes through, RS will no longer be null
        		while (RS.next()) {

        		AppNum = Integer.toString(RS.getInt("AppID"));
      		    Pnum = Integer.toString(RS.getInt("Pnum"));
        		Pname = RS.getString("Pname");
        		DocID = (RS.getString("DocID"));
        		apDate = (RS.getString("apDate"));
        		apTime = RS.getString("apTime");
        		missed = Integer.toString(RS.getInt("missed"));
        		passed = Integer.toString(RS.getInt("passed"));
        		phoneNum = RS.getString("pPhone");
        		Appointment temp = new Appointment(AppNum, Pnum, Pname, DocID, apDate, apTime, passed, missed, phoneNum);
  		      	appList.add(temp);
  		      	tempList.add(temp);
      		    }

        		//tAppNum.setCellValueFactory(cellData -> cellData.getValue().getAppNum());
        		PnumCol.setCellValueFactory(cellData -> cellData.getValue().getPnum());
                PnameCol.setCellValueFactory(cellData -> cellData.getValue().getPname());
                DocIDCol.setCellValueFactory(cellData -> cellData.getValue().getDocID());
                DateCol.setCellValueFactory(cellData -> cellData.getValue().getApDate());
                TimeCol.setCellValueFactory(cellData -> cellData.getValue().getApTime());
                //tpassed.setCellValueFactory(cellData -> cellData.getValue().getPassed());
                MissedCol.setCellValueFactory(cellData -> cellData.getValue().getMissed());
                tPhoneCol.setCellValueFactory(cellData -> cellData.getValue().getPhone());
                patientTable.setItems(appList);

    	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
    }

}
