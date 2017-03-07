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

         @FXML
        private TextField patientName;
        @FXML
        private DatePicker datePick;
        @FXML
        private Button submit;
        @FXML
        private Button cancel;
          @FXML
          private TextField PnumTF;

         @FXML private ToggleGroup rg = new ToggleGroup();

         //groups radio buttons into toggle
         public void initialize()
        {
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
                rb8.setToggleGroup(rg);
                rb9.setToggleGroup(rg);
                rb10.setToggleGroup(rg);
                rb11.setToggleGroup(rg);
                rb12.setToggleGroup(rg);

                 rb1.setToggleGroup(rg);
                rb2.setToggleGroup(rg);
                rb3.setToggleGroup(rg);
                rb4.setToggleGroup(rg);
                rb5.setToggleGroup(rg);

                 //disable the submit button until all dates are checked to prevent dumb users from fucking shit up
                submit.setDisable(true);
         }

        //always reference main method, and build constructor
        private MainFXApp main;
        public void setMain(MainFXApp mainIn)
        {
        main=mainIn;
        }


    //button to go back, not really using
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


    //this is the submit button where appointments are submitted
    @FXML
    void submitAppt(ActionEvent event) {

        //check for error--possibly check name and patient number
        String date = datePick.getValue().toString();
        String time = radioBttnSelect(event);
        String name = patientName.getText();
        String patientNum = PnumTF.getText();

        System.out.println("Date: " + date);
        System.out.println("Time: " + time);


        //create query to send appt in db
        String apptQuery = "INSERT INTO `Current_Appointment`(`Pnum`, `Pname`, `DocID`, `apDate`, `apTime`) "
                        + "VALUES (?,?,?,?,?)";


        //try to connect to db
        try (Connection conn = DBConfig.getConnection();
                               PreparedStatement createAppt = conn.prepareStatement(apptQuery,Statement.RETURN_GENERATED_KEYS);)
                {
        //System.out.println("Time" + time);

        //set info into the query
        createAppt.setString(1, patientNum);
        createAppt.setString(2, name);
        createAppt.setString(3, "0");
        createAppt.setString(4, date);
        createAppt.setString(5, time);

        //System.out.println("Query Sent" + apptQuery);


        //pass the query in
        int affectedRow  = createAppt.executeUpdate();

        //clear text fields
        /*if(affectedRow == 1)
        {
                //clear the text fields
        }*/


                }catch(SQLException ex)//try
                {
                       DBConfig.displayException(ex);
                }
    }


    //get the value from the radio button
    private String radioBttnSelect(ActionEvent action)
    {
        return rg.getSelectedToggle().getUserData().toString();
    }


    //this is where you want to connect to the database and check appointment times
    @FXML
    void checkDate (ActionEvent event) throws SQLException
    {
        //disable button every time they change dates because users are fucking idiots
        submit.setDisable(true);

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

        //make a time variable to catch current times from database
        String curTime;

        //create query to send appt in db
        String dateChecking = "SELECT apTime FROM `Current_Appointment` WHERE apDate = ?";
        ResultSet rs = null;

        //connect to database
        try(
                        Connection conn = DBConfig.getConnection();
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
                               //enable the submit after checking times
                               submit.setDisable(false);
                        }
        }catch(SQLException ex){//try
                       ex.printStackTrace();
                }finally{//catch
                       submit.setDisable(false);//if it made it through and there were no times at all, enables submit
                       if(rs != null)
                       {
                               rs.close();
                       }
                }
        System.out.println("Date selected" + datePick.getValue().toString());
    }//end method


}//end class
