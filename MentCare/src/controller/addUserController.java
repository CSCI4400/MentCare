//Created by Anna 3/25/2017
//modified by Anna 3/28/17 at 10:16am, 2 updates 

//TODO
/*
 * clear the fields 
 */

package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.mysql.jdbc.Statement;
import application.DBConfig;
import application.MainFXApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.currentUser;
import model.newUser;

public class addUserController {

	//all textfields 
    @FXML
    private TextField tfPass;
    @FXML
    private TextField tfName;
    
    //all buttons 
    @FXML
    private Button btnAddUser;
    
    //all labels 
    @FXML
    private Label lblPass;
    @FXML
    private Label lblName;
    @FXML
    private Label lblErr;
    @FXML
    private Label lblID;
   
    //all radio buttons and togglegroups
    @FXML
    private RadioButton radDoc;
    @FXML
    private ToggleGroup roleGroup;
    @FXML
    private RadioButton radRecep;
    @FXML
    private RadioButton radNurse;
    @FXML
    private RadioButton radBusiness;
    
    //for the current day
    String today;
    
    
  //groups radio buttons into toggle, disable buttons, set values, gets current date
    public void initialize()
   {        
        //set the date
        //curDate = new Date();
        Date date = new Date();
        DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
        today = dformat.format(date);
        System.out.print("Date: " + today);
        
    	btnAddUser.setDisable(false);
    	
    	radDoc.setToggleGroup(roleGroup);
    	radRecep.setToggleGroup(roleGroup);
    	radBusiness.setToggleGroup(roleGroup);
    	radNurse.setToggleGroup(roleGroup);
    	
    	radRecep.setUserData(111);
    	radNurse.setUserData(333);
    	radDoc.setUserData(555);
    	radBusiness.setUserData(777);
   }
    
  //always reference main method, and build constructor
    private MainFXApp main;
    public void setMain(MainFXApp mainIn)
    {
    	main = mainIn;
    }
    
    //makes a unique userID
    private String createUserID(String role) throws SQLException
	{
		//creates an int to make a unique ID
		int uniqueID = 9000000;
	
		//string version of ID
		String strUniqueID = "";
		
		//for the next auto increment
		int nextIncrement = 0;
		
		//to hold results
		ResultSet rs = null;
		
		//the final ID
		String finalID = "";
		
		//create query to get the next auto increment 
        String IDQuery = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'mentcare' AND TABLE_NAME = 'Users'";
		
		 //try to connect to db
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement getID = conn.prepareStatement(IDQuery, Statement.RETURN_GENERATED_KEYS);)
        {
        	//print query
        	System.out.println("Query Sent" + getID.toString());

        	//get the result set and execute query
        	rs = getID.executeQuery();
        
        	//move through the results
        	if (rs.next())
        	{
        		//get the next auto increment
        		nextIncrement = rs.getInt("AUTO_INCREMENT");
        		System.out.println("nextIncrement" + nextIncrement);
        	}     
        }//end try
        
        //add the auto increment to the number to make ID
        uniqueID = uniqueID + nextIncrement;
        
        //turn it to string
        strUniqueID = "" + uniqueID;
        
        //replace that first number 
        strUniqueID.replaceFirst(strUniqueID, "0");
        
        finalID = role + strUniqueID;
        return finalID;
	}//end method
    
    @FXML
    void submitUser(ActionEvent event) throws SQLException {
    	if(errorChecking() == true){//puts data through error checking before doing anything with it - avoids/handles exceptions
    		//create user object
		    newUser user = new newUser();	
		    	
		    //---Begin Anna 1---------------------------------------------------------------------------------------------
		    //create string to concat the createdby and createdby ID number together 
		    String createdIDandName = "";
		    //---End Anna 1-----------------------------------------------------------------------------------------------
		    	
		    //set values from the GUI
		    user.setName(tfName.getText());
		    user.setRole(((Labeled) roleGroup.getSelectedToggle()).getText().toString());
		    user.setPassword(tfPass.getText());
		    user.setCreateDate(today);
		    
			//---Begin Anna 2---------------------------------------------------------------------------------------------
		    //concatenates the logged on user's ID and name togther and sets this for the createdBY in the database- Anna
		    createdIDandName = loginController.loggedOnUser.getName() + ", " + loginController.loggedOnUser.getID();
		    user.setCreatedBy(createdIDandName);
		    //---End Anna 2-----------------------------------------------------------------------------------------------
		    
		    try {
		    	//get the unique ID
				user.setID(createUserID(roleGroup.getSelectedToggle().getUserData().toString()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   	
		   	//set the ID label on GUI
		   	lblID.setText(user.getID());
		   	
		   	//print out the user created 
		   	System.out.println("User: " + user);
		   	
		   	//send into database
		   	//create query to send appt in db
		    String newUserQuery = "INSERT INTO `Users`(`idNum`, `password`, `role`, `FullName`, `CreatedBy`, `CreatedDate`) VALUES (?,?,?,?,?,?)";
		    //try to connect to db
		    try (Connection conn = DBConfig.getConnection();
		         PreparedStatement createUser = conn.prepareStatement(newUserQuery,Statement.RETURN_GENERATED_KEYS);)
		    {
		    	//set info into the query
		    	createUser.setString(1, user.getID());
		        createUser.setString(2, user.getPassword());
		        createUser.setString(3, user.getRole());
		        createUser.setString(4, user.getName());
		        createUser.setString(5, user.getCreatedBy());
		        createUser.setString(6, user.getCreateDate());
		        
			    //print query
		        System.out.println("Query Sent " + createUser.toString());
				
		        //pass the query in
		        createUser.executeUpdate();	
		    }catch (SQLException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }//end catch
	    }else{
	    	System.out.println("Failed error checking.");
	   	}
    }//end method
    
    /*
     * ========================================================================================================
     * @author Danni
     * ========================================================================================================
     */
    Parent root;
    Stage stage;
    Scene scene;
    //links buttons
	@FXML Button btnBack;
	@FXML Button btnRefresh;
	//takes user back to main view page
    @FXML 
    void backMain (ActionEvent event) throws Exception{
    	stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
    }
    //clears current data so the user can create more new users without having to leave the page
    @FXML
    void refresh (ActionEvent event) throws Exception{
    	stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("/view/addUserView.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
    }
    //error checking for Anna
    boolean errorChecking(){
    	//if pass equals true once this method is complete -> new user will created
    	boolean passed = true;
    	try{
	    	//clears labels to get rid of previous errors - throws only current errors
	   		lblName.setText("");
	   		lblPass.setText("");
	   		lblErr.setText("");
	    	//stores textfield data into variables
	    	String name = tfName.getText();
	    	String password = tfPass.getText();
	    	//checks to make sure everything is filled in - throws error if a field or more is left empty
	    	if(name.equals("") || password.equals("") || roleGroup.selectedToggleProperty().getValue() == null){
	    		passed = false;
	    		lblErr.setText("Please fill in all fields.");
	    	}else{
		    	//checks name for special characters
		    	Pattern p = Pattern.compile("[^a-zA-Z-\\.\\s]");
		    	Matcher nam = p.matcher(name);
		    	boolean n = nam.find();
		    	//throws error if it is true that a special character was found
		    	if(n){
		    		passed = false;
		    		lblName.setText("Name cannot contain special characters.");
		    	}
		    	//checks the password for anything that is not a number
		    	Pattern p2 = Pattern.compile("[^0-9]");
		    	Matcher pass = p2.matcher(password);
		    	boolean pw = pass.find();
		    	//throws error if any character other than a number is found
		    	if(pw){
		    		passed = false;
		    		lblPass.setText("Password can only contain numbers.");
		    	}
		    	//stores password length in a variable
		    	int plen = tfPass.getLength();
		    	//checks the password length for the correct length
		    	if(plen != 6){
		    		if(plen < 6){
		    			lblPass.setText("Password is too short.");
		    		}
		    		if(plen > 6){
		    			lblPass.setText("Password is too long.");
		    		}
		    		passed = false;
		    	}
	    	}
    	}catch(Exception e){
    		e.getMessage();
    	}
    	return passed;
    }
}//end class

