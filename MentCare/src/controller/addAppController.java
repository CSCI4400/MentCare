/*
 * the program won't enable the submit button until a time is selected, it also disables all time values, only way to
 * enable the time values is by selecting a date, the program essentially "self polices" that part 
 *
 * updates: made it so it searches for patient instead, doesn't overwrite patient information 4/5/17 at 9pm
 * program works with mentcare2
 * @author Anna
 */

package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.DBConfig;
import application.MainFXApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class addAppController {

        Stage stage;
        Scene scene;
        Parent root;
        
        //Connection to DB
        Connection conn = MainFXApp.con;
        
        //labels 
        @FXML private Label lblErrPatient;

        //buttons for times
        @FXML private RadioButton rb1;
        @FXML private RadioButton rb2;
        @FXML private RadioButton rb3;
        @FXML private RadioButton rb4;
        @FXML private RadioButton rb5;
        @FXML private RadioButton rb8;
        @FXML private RadioButton rb9;
        @FXML private RadioButton rb10;
        @FXML private RadioButton rb11;
        @FXML private RadioButton rb12;

         @FXML private TextField patientName;
        @FXML private DatePicker datePick;
        @FXML private Button submit;
        @FXML private Button cancel;
       
        //added by Anna1------used to search the patient, prevents any overwriting with patient info from the patient table
        @FXML private Button searchBtn;
        //-----------------------------

        @FXML private TextField PnumTF;
        @FXML private TextField patientPhone;

        //make group for radio buttons
         @FXML private ToggleGroup rg = new ToggleGroup();
         
         //variable to hold the current selected time 
         private String selectedTime; 
         
         //groups radio buttons into toggle, disable buttons, set values
         public void initialize()
        {
        	 //disable the datepicker
        	 datePick.setDisable(true);
                //set values
                 //morning
                rb8.setUserData(8);
                rb9.setUserData(9);
                rb10.setUserData(10);
                rb11.setUserData(11);
                rb12.setUserData(12);

                 //afternoon
                rb1.setUserData(1);
                rb2.setUserData(2);
                rb3.setUserData(3);
                rb4.setUserData(4);
                rb5.setUserData(5);
                //--------------------

                 //add to toggle groups
                //morning
                rb8.setToggleGroup(rg);
                rb9.setToggleGroup(rg);
                rb10.setToggleGroup(rg);
                rb11.setToggleGroup(rg);
                rb12.setToggleGroup(rg);

                //afternoon
                rb1.setToggleGroup(rg);
                rb2.setToggleGroup(rg);
                rb3.setToggleGroup(rg);
                rb4.setToggleGroup(rg);
                rb5.setToggleGroup(rg);

                 //disable the submit button and all select buttons 
                //until all dates are checked and a time is selected 
                submit.setDisable(true);
                
                rb8.setDisable(true);
                rb9.setDisable(true);
                rb10.setDisable(true);
                rb11.setDisable(true);
                rb12.setDisable(true);
                rb1.setDisable(true);
                rb2.setDisable(true);
                rb3.setDisable(true);
                rb4.setDisable(true);
                rb5.setDisable(true);    
         }

         
        //always reference main method, and build constructor
        private MainFXApp main;
        public void setMain(MainFXApp mainIn)
        {
        main=mainIn;
        }


        //-------added Anna 2-----------------
        //used to search the patient to create appt
        @FXML
        void searchPatient(ActionEvent event) throws SQLException {
        	
        	//clear fields 
        	patientName.clear();
        	patientPhone.clear();
        	
        	//deselect everything if user moves around
        	submit.setDisable(true);
            
            rb8.setDisable(true);
            rb9.setDisable(true);
            rb10.setDisable(true);
            rb11.setDisable(true);
            rb12.setDisable(true);
            rb1.setDisable(true);
            rb2.setDisable(true);
            rb3.setDisable(true);
            rb4.setDisable(true);
            rb5.setDisable(true);
            
          //deselect time buttons
    		rb8.setSelected(false);
    		rb9.setSelected(false);
    		rb10.setSelected(false);
    		rb11.setSelected(false);
    		rb12.setSelected(false);
    		rb1.setSelected(false);
    		rb2.setSelected(false);
    		rb3.setSelected(false);
    		rb4.setSelected(false);
    		rb5.setSelected(false);
    		
    		//clear label
    		  lblErrPatient.setText("");
        	
        	
        	//get the patient ID
        	String patientID = PnumTF.getText();
        	
        	//for concatenating name as last name, first name
        	String concatName;
        	
        	//search based on patient name
        	String searchQuery = "SELECT `LName`, `FName`, `Phone_Number`  FROM `Personal_Info` WHERE PNumber = ?";
        	
        	  ResultSet rs = null;

              //connect to database
              try(
                              PreparedStatement findPatient = conn.prepareStatement(searchQuery);
                      ){

                              //insert the selected number for the query
                              findPatient.setString(1, patientID);
                              rs = findPatient.executeQuery();//execute query
                              
                              //check and see if you got data or not
                              if (!rs.isBeforeFirst() ) {    
                            	    System.out.println("No patient found"); 
                            	    lblErrPatient.setText("No patient found");
                            	    
                            	} 
                              else //go ahead and get the data the data
                              {
                            	  rs.next();
                            	  lblErrPatient.setText("");
                            	  System.out.println("patient found");
                            	  
                            	  String lastName = rs.getString("LName");
                            	  String firstName = rs.getString("FName");
                            	  String phone = rs.getString("Phone_Number");
                            	  
                            	  concatName = lastName + ", " + firstName;
                            	  
                            	  //display data
                            	  patientName.setText(concatName);
                            	  patientPhone.setText(phone);
                            	  
                            	  //enable the datepicker
                            	  datePick.setDisable(false);
                              }
                              
              		}catch(SQLException ex){//try
                        ex.printStackTrace();
              		}
        	

        }

        
        
      //called when a radio button is selected, assigns value to radio button, only enables submit when radio button is selected
        @FXML
        void timeSelect(ActionEvent event) 
        {

        	//set the time value 
        	selectedTime = rg.getSelectedToggle().getUserData().toString();
        	//System.out.println("selectedTime " + selectedTime);
        	
        	//enable the submit button if they actually select a time 
        	submit.setDisable(false);
        }


        //this is where you want to connect to the database and check appointment times, disables all the filled times
        @FXML
        void checkDate (ActionEvent event) throws SQLException
        {
        	//enable all time buttons when different date selected
    		rb8.setDisable(false);
    		rb9.setDisable(false);
    		rb10.setDisable(false);
    		rb11.setDisable(false);
    		rb12.setDisable(false);
    		rb1.setDisable(false);
    		rb2.setDisable(false);
    		rb3.setDisable(false);
    		rb4.setDisable(false);
    		rb5.setDisable(false);
    		
    		//deselect time buttons
    		rb8.setSelected(false);
    		rb9.setSelected(false);
    		rb10.setSelected(false);
    		rb11.setSelected(false);
    		rb12.setSelected(false);
    		rb1.setSelected(false);
    		rb2.setSelected(false);
    		rb3.setSelected(false);
    		rb4.setSelected(false);
    		rb5.setSelected(false);
    		
    		//disable the submit button in case users are moving around
    		submit.setDisable(true);

    		
            //make a time variable to catch current times from database
            String curTime;

            //create query to send appt in db
            String dateChecking = "SELECT apTime FROM `Current_Appointment` WHERE apDate = ?";
            ResultSet rs = null;

            //connect to database
            try(
                            PreparedStatement checkDates = conn.prepareStatement(dateChecking);
                    ){

                            //insert the selected date for the query
                            checkDates.setString(1, datePick.getValue().toString());
                            rs = checkDates.executeQuery();//execute query

                            // move through and grab info
                            while (rs.next())
                            {
                                   //grab time to check
                                   curTime = (String) rs.getObject(1);

                                 //start comparing times and disabling the buttons
                   				if(curTime.compareTo("8")==0)
                   				{
                   					rb8.setDisable(true);
                   					rb8.setSelected(false);
                   				}
                   				if(curTime.compareTo("9")==0)
                   				{
                   					rb9.setDisable(true);
                   					rb9.setSelected(false);
                   				}
                   				if(curTime.compareTo("10")==0)
                   				{
                   					rb10.setDisable(true);
                   					rb10.setSelected(false);
                   				}
                   				if(curTime.compareTo("11")==0)
                   				{
                   					rb11.setDisable(true);
                   					rb11.setSelected(false);
                   				}
                   				if(curTime.compareTo("12")==0)
                   				{
                   					rb12.setDisable(true);
                   					rb12.setSelected(false);
                   				}
                   				if(curTime.compareTo("1")==0)
                   				{
                   					rb1.setDisable(true);
                   					rb1.setSelected(false);
                   				}
                   				if(curTime.compareTo("2")==0)
                   				{
                   					rb2.setDisable(true);
                   					rb2.setSelected(false);
                   				}
                   				if(curTime.compareTo("3")==0)
                   				{
                   					rb3.setDisable(true);
                   					rb3.setSelected(false);
                   				}
                   				if(curTime.compareTo("4")==0)
                   				{
                   					rb4.setDisable(true);
                   					rb4.setSelected(false);
                   				}
                   				if(curTime.compareTo("5")==0)
                   				{
                   					rb5.setDisable(true);
                   					rb5.setSelected(false);
                   				}
                            }
            }catch(SQLException ex){//try
                           ex.printStackTrace();
                    }finally{//catch
                           if(rs != null)
                           {
                                   rs.close();
                           }
                    }
            //System.out.println("Date selected" + datePick.getValue().toString());
        }//end method
        
        
    //this is the submit button where appointments are submitted
    @FXML
    void submitAppt(ActionEvent event) {

        //grab text fields and dates, time is handled in seperate method and passed into global variable
        String date = datePick.getValue().toString();
        String name = patientName.getText();//error checking needed
        String patientNum = PnumTF.getText();//error checking needed
        String phone = patientPhone.getText();
        
        
        //printing purpose 
        System.out.println("Date selected: " + date);
        System.out.println("Time selected: " + rg.getSelectedToggle().getUserData().toString());


        //create query to send appt in db
        String apptQuery = "INSERT INTO `Current_Appointment`(`Pnum`, `Pname`, `DocID`, `apDate`, `apTime`, pPhone) "
                        + "VALUES (?,?,?,?,?,?)";


        //try to connect to db
        try (
                               PreparedStatement createAppt = conn.prepareStatement(apptQuery,Statement.RETURN_GENERATED_KEYS);)
                {

        //set info into the query
        createAppt.setString(1, patientNum);
        createAppt.setString(2, name);
        createAppt.setString(3, "0");
        createAppt.setString(4, date);
        createAppt.setString(5, selectedTime);
        createAppt.setString(6, phone);

        //print query
        System.out.println("Query Sent" + createAppt.toString());

        //pass the query in
        int affectedRow  = createAppt.executeUpdate();

        //clear text fields
        if(affectedRow == 1)
        {
                patientName.clear();
                PnumTF.clear();
                patientPhone.clear();
                
                //can't clear date values yet, causes a null pointer error
               //datePick.getEditor().clear();
                //datePick.setValue(null);
                
                //now that you've submitted, disable everything
                submit.setDisable(true);
                
                rb8.setDisable(true);
                rb9.setDisable(true);
                rb10.setDisable(true);
                rb11.setDisable(true);
                rb12.setDisable(true);
                rb1.setDisable(true);
                rb2.setDisable(true);
                rb3.setDisable(true);
                rb4.setDisable(true);
                rb5.setDisable(true);
                
              //deselect time buttons
        		rb8.setSelected(false);
        		rb9.setSelected(false);
        		rb10.setSelected(false);
        		rb11.setSelected(false);
        		rb12.setSelected(false);
        		rb1.setSelected(false);
        		rb2.setSelected(false);
        		rb3.setSelected(false);
        		rb4.setSelected(false);
        		rb5.setSelected(false);
        }


                }catch(SQLException ex)//try
                {
                       DBConfig.displayException(ex);
                }
    }//end method
    
    
  //go back to main
    @FXML
        void ClickCancelButton(ActionEvent event) throws Exception {
       try{
        //finds what stage the button is in
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                //gets some fxml file
                root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
                //sets fxml file as a scene
                scene = new Scene(root);
                //loads the scene on top of whatever stage the button is in
                stage.setScene(scene);
        } catch (Exception e){
                e.printStackTrace();
                }
    }

}//end class
