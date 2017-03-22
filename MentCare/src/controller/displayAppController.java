package controller;
/*
 * @author Caleb
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import application.MainFXApp;
import application.DBConfig;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import model.Appointment;

public class displayAppController{

	Stage stage;
	Scene scene;
	Parent root;

	//always reference main method, and build constructor
	private MainFXApp main;
	public void setMain(MainFXApp mainIn)
	{
		main = mainIn;
	}

	@FXML private TableView<Appointment> patientTable;
	@FXML private TableColumn<Appointment, String> tAppNum;
    @FXML private TableColumn<Appointment, String> PnumCol;
    @FXML private TableColumn<Appointment, String> PnameCol;
    @FXML private TableColumn<Appointment, String> DocIDCol;
    @FXML private TableColumn<Appointment, String> DateCol;
    @FXML private TableColumn<Appointment, String> TimeCol;
    @FXML private TableColumn<Appointment, String> tpassed;
    @FXML private TableColumn<Appointment, String> MissedCol;
    @FXML private TableColumn<Appointment, String> tPhoneCol;
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
    @FXML private Label dateFeedback;
    @FXML private TextField DateTF;
    static List<Appointment> list = new ArrayList<Appointment>();
    static ObservableList<Appointment> appList = FXCollections.observableList(list);
    static ArrayList<Appointment> tempList = new ArrayList<Appointment>();
    /*
     * ============================================================================================================
     * @author Danni
     * initialize -> moves unmarked past appointments from current to missed db table
     * AttendAppointment -> moves selected row data to either past or missed appointment db table depending on
     * 						which button is selected
     * ============================================================================================================
     */
    public void initialize() throws Exception{
    	try{
	    	//sets date to the format we are using 
	    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	//gets current date
	    	Date date = new Date();
	    	//sets date to current date with the right format
	    	String today = dateFormat.format(date);
	    	System.out.println(today);
	    	
	    	String AppNum, Pnum, Pname, DocID, apDate, apTime, missed, passed, phoneNum;
	    	//pulls all data from Current_Appointment db table
			String getData = ("SELECT * FROM `mentcare`.`Current_Appointment`");
			//connects to db
			Connection conn = DBConfig.getConnection();
			PreparedStatement ps = conn.prepareStatement(getData);
			ResultSet rs = ps.executeQuery();
			//retrieves data from the db table so long as there is data then sets in model
			while(rs.next())
			{
				//sets variables with retrieved db data
				AppNum = Integer.toString(rs.getInt("AppID"));
	  		    Pnum = Integer.toString(rs.getInt("Pnum"));
	    		Pname = rs.getString("Pname");
	    		DocID = rs.getString("DocID");
	    		apDate = rs.getString("apDate");
	    		apTime = rs.getString("apTime");
	    		missed = Integer.toString(rs.getInt("missed"));
	    		passed = Integer.toString(rs.getInt("passed"));
	    		phoneNum = rs.getString("pPhone");
				//sets data in model Appointment
				Appointment temp = new Appointment(AppNum, Pnum, Pname, DocID, apDate, apTime, passed, missed, phoneNum);
			    //puts Appointment object temp into an ArrayList
			    tempList.add(temp);
			}
			//counts and displays how many appointments are in the table
			System.out.println("Row Count: " + tempList.size());
		   	for(int i = 0; i < tempList.size(); i++){
		   		//calls getters for appointment time and appointment date for position i in tempList
		   		String findDate = tempList.get(i).getApDateString();
		   		String findTime = tempList.get(i).getApTimeString();
		   		//uses joda to set dates to the correct date format we are using - requires joda jar file to work
		   		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd"); 
		   		//sets appDate and curDate to be of the same format and type
		   		LocalDate appDate = formatter.parseLocalDate(findDate);
		   		LocalDate curDate = formatter.parseLocalDate(today);
		   		//for testing purposes -> easy on the eyes
		   		System.out.println("Appointment Date: " + appDate);
		   		System.out.println("Today's Date: " + curDate);
		   		/*
		   		 * If appointment date is past today's date -> "automatically" moves past unmarked (not manually
		   		 * marked as "attended" or "missed") appointments into Missed_Appointments db table.
		   		 * moves if appointment date is NOT today's date or a future date
		   		*/
		   		//uses joda to check appointment date against current date - requires joda jar file
		   		if(appDate.isBefore(curDate)){
		   			//moves past appointments from Current_Appointment db table into Missed_Appointment db table
		    		//passes data based on date and time of appointment from current to missed appointment db tables
			 		String currToMiss = ("INSERT INTO `mentcare`.`Missed_Appointment`(AppID, Pnum, Pname, DocID, apDate, apTime) "
		 				+ "SELECT AppID, Pnum, Pname, DocID, apDate, apTime FROM Current_Appointment WHERE `apDate`='" + findDate
		 				+ "' AND `apTime`='" + findTime + "';");
			 		PreparedStatement ps2 = conn.prepareStatement(currToMiss);
					ps2.execute();
			   		System.out.println(currToMiss);
				 	//deletes from Current_Appointment db table
			   		String delcurrApp = ("DELETE FROM `mentcare`.`Current_Appointment` WHERE `apDate`='" + findDate +
		   				 "' AND `apTime`='" + findTime + "';");
		    		PreparedStatement ps3 = conn.prepareStatement(delcurrApp);
					ps3.execute();
			  	 	System.out.println(delcurrApp);
		    	}else{
		    		//Shows what is kept during the past appointment filtering
		    		String name = tempList.get(i).getPname().getValue();
		    		System.out.println("Keep: " + name);
		    	}
		    	System.out.println("Loop: " + i);
		    }
		   	System.out.println("*DONE SCANNING CURRENT APPOINTMENTS*");
		   	//Clears the list now so that it does not cause conflicts later when it is called in a different method.
			tempList.clear();	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

	@FXML private Button btnAttend;
    @FXML private Button btnMiss;
    //enables attended and missed buttons once a row has been selected in the table
    @FXML
    void mouseClicked(MouseEvent event) {
    	int selectedIndex = patientTable.getSelectionModel().getSelectedIndex();
    	if(selectedIndex >= 0){
    		btnAttend.setDisable(false);
    		btnMiss.setDisable(false);
    		btnCancel.setDisable(false);
    	}
    }
    
    /*
     * Gets selected table row and puts it in either past or missed db table and  
     * deletes that row from current appointment table. Also, removes that row's data  
     * from the table in the display GUI.
     */
    @FXML
    public void AttendAppointment(ActionEvent event) throws Exception{
    	try{//try1
    		//finds selected row in table and stores it in a variable
	    	int selectedIndex = patientTable.getSelectionModel().getSelectedIndex();
	    	//gets item from list for a row in table
			String dateTemp = tempList.get(selectedIndex).getApDateString();
			String timeTemp = tempList.get(selectedIndex).getApTimeString();
			//connection to db
			Connection conn = DBConfig.getConnection();
	    	if(btnAttend.isArmed() == true){
		 		//passes from current to past appointment db tables
		 		String currToPass = ("INSERT INTO `mentcare`.`Previous_Appointment`(AppID, Pnum, Pname, DocID, apDate, apTime) "
		 				+ "SELECT AppID, Pnum, Pname, DocID, apDate, apTime FROM Current_Appointment WHERE `apDate`='" + dateTemp
		 				+ "' AND `apTime`='" + timeTemp + "';");
		   		System.out.println(currToPass);
		   		PreparedStatement ps4 = conn.prepareStatement(currToPass);
			 	ps4.execute();
			 	//deletes from current appointment
	    		String delcurrAttend = ("DELETE FROM `mentcare`.`Current_Appointment` WHERE `apDate`='" + dateTemp +
	   				 "' AND `apTime`='" + timeTemp + "';");
	   		 	System.out.println(delcurrAttend);
	   		 	PreparedStatement ps5 = conn.prepareStatement(delcurrAttend);
		 		ps5.execute();
		 		//removes selected row from display GUI table
		   		patientTable.getItems().remove(selectedIndex);
	    	}else if(btnMiss.isArmed() == true){
	    		try{//try2
	    			//gets data from the row at that date and time -> always unique
		    		String getData = ("SELECT * FROM `mentcare`.`Current_Appointment` WHERE `apDate`='" + dateTemp +
		   				 "' AND `apTime`='" + timeTemp + "';");
		    		PreparedStatement ps6 = conn.prepareStatement(getData);
		    		ps6.execute();
		    		//creates popup for when the missed button is clicked
			    	Alert alert = new Alert(AlertType.CONFIRMATION);
			    	DialogPane dialogPane = alert.getDialogPane();
			    	//css for missed alert box
			    	dialogPane.setStyle("-fx-background-image: url(application/gui_bg.jpg);"
			    					  + "-fx-font-size: 15px;"
			    					  + "-fx-mid-text-color: #010a66;"
			    					  + "-fx-font-family: georgia;");
			    	alert.setTitle("Missed Appointment");
			    	//sets selected patient phone number if no number was entered when the appointment was created
			    	if(patientTable.getSelectionModel().getSelectedItem().getPhone().getValue() == null){
			    		StringProperty phone = new SimpleStringProperty("No contact number");
			    		patientTable.getSelectionModel().getSelectedItem().setPhone(phone);
			    	}
			    	//gets the currently selected row patient name and contact number from the GUI table then displays it
			    	alert.setHeaderText(patientTable.getSelectionModel().getSelectedItem().getPname().getValue() + " missed an appointment!" 
			    					 + "\t\t  Contact: " + patientTable.getSelectionModel().getSelectedItem().getPhone().getValue());
			    	alert.setContentText("Reason for Missing:");
			    	//creates text area
			    	TextArea reason = new TextArea();
			    	reason.setPromptText("Enter patient's reason for missing appointment.");
			    	//makes text area editable for the user to type in input
			    	reason.setEditable(true);
			    	reason.setWrapText(true);
			    	//sets grid dimensions
			    	reason.setMaxWidth(Double.MAX_VALUE);
			    	reason.setMaxHeight(Double.MAX_VALUE);
			    	GridPane.setVgrow(reason, Priority.ALWAYS);
			    	GridPane.setHgrow(reason, Priority.ALWAYS);
			    	//places objects on grid
			    	GridPane expContent = new GridPane();
			    	expContent.setMaxWidth(Double.MAX_VALUE);
			    	expContent.add(reason, 0, 0);	
			    	//makes dialog box expandable -> default is not
			    	alert.getDialogPane().setExpandableContent(expContent);
			    	//expands window to view full text area -> hides automatically if this is not set
			    	alert.getDialogPane().setExpanded(true);
			    	//waits for button to be clicked to perform an action -> stores in variable
			    	Optional<ButtonType> result = alert.showAndWait();
			    	//if the user clicks the OK button -> sends table row information to db
			    	if(result.get() == ButtonType.OK){
			    		/*
			    		 * For data going to Missed_Appointment db table, it is split into 2 queries because the 
			    		 * first query calls everything stored in that row in the Current_Appointment db table. The 
			    		 * ReasonMissed column does not exist in the current db table so it causes conflicts when  
			    		 * sending to the missed db table with the way the query is written. So, there are 2 
			    		 * queries instead of one.
			    		 */
			    		//gets text from text area field
				    	String pReason = reason.getText();
				    	//if patient does not give a reason for missing their appointment -> catches
				    	if(pReason.isEmpty()){
				    		pReason = "No reason was given.";
				    	}
			    		//pass from current to miss appointment
						String currToMiss = ("INSERT INTO `mentcare`.`Missed_Appointment`(AppID, Pnum, Pname, DocID, apDate, apTime) "
								+ "SELECT AppID, Pnum, Pname, DocID, apDate, apTime FROM Current_Appointment WHERE `apDate`='" + dateTemp
								+ "' AND `apTime`='" + timeTemp + "';");
						//sends reason for missing appointment to Missing_Appointment db table
				 		String missReason = ("UPDATE `mentcare`.`Missed_Appointment` SET `ReasonMissed`='" + pReason + "' WHERE `apDate`='" + dateTemp
								+ "' AND `apTime`='" + timeTemp + "';");
				   		PreparedStatement ps7 = conn.prepareStatement(currToMiss);
				   		PreparedStatement ps8 = conn.prepareStatement(missReason);
					 	ps7.execute();
					 	ps8.execute();
					 	System.out.println(currToMiss);
				   		System.out.println(missReason);
					 	//deletes from current appointment
					 	String delcurAtten = ("DELETE FROM `mentcare`.`Current_Appointment` WHERE `apDate`='" + dateTemp +
				   				 "' AND `apTime`='" + timeTemp + "';");
				   		PreparedStatement ps9 = conn.prepareStatement(delcurAtten);
				   		ps9.execute();
				   		System.out.println(delcurAtten);
				   		//removes selected row from display GUI table
				   		patientTable.getItems().remove(selectedIndex);
				   		//closes alert window
				   		alert.hide();
			    	}else{
			    		//closes alert window
			    		alert.hide();
			    	}
	    		}catch(SQLException ex){//try2
					ex.printStackTrace();
	    		}
    		}else{
    			System.out.println("Failed to send current appointment information to either attended or missed db tables.");
	    	}
    	}catch (Exception e){//try1
    		e.printStackTrace();
    	}
    }
    //=============================================================================================================
    @FXML
    public void CancelAppointment(ActionEvent event) throws Exception{
    	try{
	    	int selectedIndex = patientTable.getSelectionModel().getSelectedIndex();

	    	if(selectedIndex >= 0){
	    		String dateTemp = tempList.get(selectedIndex).getApDateString();
	    		String timeTemp = tempList.get(selectedIndex).getApTimeString();
	    		/*
	    	     * ================================================================================================
	    	     * @author Danni
	    	     * CancelAppointment -> displays a pop-up confirmation dialog box that will delete the selected 
	    	     * 						appointment from current db table when the correct button is pressed
	    	     * ================================================================================================
	    	     *
	    		 * Makes pop-up dialog box warning the user about cancelling appointments.
	    		 * Cancelled appointments cannot be recovered once deleted. They can only be rescheduled.
	    		 * No RECOVER method will be made as it may conflict with other appointments that can be scheduled
	    		 * for the newly opened (cancelled) time slot.
	    		 */
	    		//creates a confirmation alert -> comes with some useful preset features -> changes depending on alertType chosen
	    		Alert confirm = new Alert(AlertType.CONFIRMATION);
	    		DialogPane dialogPane = confirm.getDialogPane();
	    		//css for cancel appointment confirmation box
		    	dialogPane.setStyle("-fx-background-image: url(application/gui_bg.jpg);"
		    					  + "-fx-font-size: 15px;"
		    					  + "-fx-mid-text-color: #010a66;"
		    					  + "-fx-font-family: georgia;");
		    	confirm.setTitle("Cancel Appointment");
		    	//gets data from the row at that date and time -> always unique
	    		String getData = ("SELECT * FROM `mentcare`.`Current_Appointment` WHERE `apDate`='" + dateTemp +
	   				 "' AND `apTime`='" + timeTemp + "';");
	    		Connection conn = DBConfig.getConnection();
	    		PreparedStatement ps10 = conn.prepareStatement(getData);
	    		ps10.execute();
	    		//Asks if you really want to delete the selected appointment
	    		confirm.setHeaderText("Are you sure you want to cancel " 
	    				+ patientTable.getSelectionModel().getSelectedItem().getPname().getValue() + "'s appointment?");
	    		//Warns about not being able to recover a cancelled appointment
		    	confirm.setContentText("Cancelled appointments cannot be recovered, only rescheduled.");
		    	//removes default OK and Cancel buttons from dialog gui
		    	confirm.getButtonTypes().clear();
		    	//adds Yes and No buttons to dialog gui
		    	confirm.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
	    		 Optional<ButtonType> res = confirm.showAndWait();
	    		//if the user clicks the YES button -> sends table row information to db
		    		if(res.get() == ButtonType.YES){
		    			String delCurr = ("DELETE FROM `mentcare`.`Current_Appointment` WHERE `apDate`='" + dateTemp +
	    				 "' AND `apTime`='" + timeTemp + "';"); 
		    			System.out.println(delCurr);
		    			PreparedStatement ps11 = conn.prepareStatement(delCurr);
		    			ps11.execute();
		    			patientTable.getItems().remove(selectedIndex);
		    		}else{
		    			//closes pop-up dialog box
		    			confirm.hide();
		    		}
	    	}//====================================================================================================
	    	else
	    	{
	    	/*	// Nothing selected. Pop-up needs many of the same stuff as a basic window
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
    //Leap Year Check for date validation
    public boolean isLeapYear(String y){
    	int year = Integer.parseInt(y);
    	boolean leap;
    	leap = (year % 4 == 0);

        // divisible by 4 and not 100
        leap = leap && (year % 100 != 0);

        // divisible by 4 and not 100 unless divisible by 400
        leap = leap|| (year % 400 == 0);

    	return leap;
    }
    @FXML
	void ClickGoButton(ActionEvent event) {
    	String enterDate = DateTF.getText();

    	try{
			boolean empty = false;
			boolean valid = true;
	    	//yyyy-mm-dd
	    	Pattern p1 = Pattern.compile("([^0-9-])", Pattern.CASE_INSENSITIVE);

	    	Matcher bDate = p1.matcher(enterDate);
	    	boolean Dat = bDate.find();

	    	if(enterDate.equals("")){ //empty field check
	    		dateFeedback.setText("Empty Field");
	    		empty = true;
	    		dateFeedback.setVisible(true);
	    		valid = false;
	    	}
	    	int count = 0;
	    	for(int i = 0; i < enterDate.length(); i++){
	    		if(enterDate.charAt(i) == '-')
	    			count++;
	    	}

	    	if(enterDate.length() != 10) //length check
	    	{
	    		valid = false;
	    		if(!empty)
	    			dateFeedback.setText("Invalid Length"); //Replace with Label warning
	    		dateFeedback.setVisible(true);
	    	}
	    	else
	    	{

	    			if(Dat) //invalid character check
	    			{
	    				dateFeedback.setText("Invalid Character");
	    				dateFeedback.setVisible(true);
	    				valid = false;
	    			}
	    			else if(count != 2) // number of dashes check
	    			{
	    				dateFeedback.setText("Invalid Format");
	    				dateFeedback.setVisible(true);
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

	    				//Check for dashes in the right places,
	    				//check for month !> 12 || < 1, day >31 || < 1, Y !< 2017
	    				// Check february range
	    				//check for 30 day months, 4, 6, 9, 11

	    				if(enterDate.charAt(4) != '-' || enterDate.charAt(7) != '-') //check for correct dash placement
	    				{
	    					valid = false;
	    					dateFeedback.setText("Invalid Format");
		    				dateFeedback.setVisible(true);
	    				}
	    				else if(mon > 12 || mon < 1) //check that month exists
	    				{
	    					valid = false;
	    					dateFeedback.setText("Invalid Month");
		    				dateFeedback.setVisible(true);
	    				}
	    				else if((mon == 4 || mon == 6 || mon == 9 || mon == 11) && d > 30){ //Check 30 day months
	    					valid = false;
	    					dateFeedback.setText("Invalid Day");
		    				dateFeedback.setVisible(true);
	    				}
	    				else if((mon == 2 && isLeapYear(enterDate.substring(0, 4))) && d > 29){ //Check Feb for leap year
	    					valid = false;
	    					dateFeedback.setText("Invalid Day");
		    				dateFeedback.setVisible(true);
	    				}
	    				else if((mon == 2 && !isLeapYear(enterDate.substring(0, 4)) && d > 28)){
	    					valid = false;
	    					dateFeedback.setText("Invalid Day");
		    				dateFeedback.setVisible(true);
	    				}
	    				else if((d > 31 || d < 1)){
	    					valid = false;
	    					dateFeedback.setText("Invalid Day");
		    				dateFeedback.setVisible(true);
	    				}

	    				}

	    			}

		    if(valid){

		    	dateFeedback.setVisible(false);

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
