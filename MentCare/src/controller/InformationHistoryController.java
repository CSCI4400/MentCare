/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import application.DBConfig;
import application.MainFXApp;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author sad2e
 */
public class InformationHistoryController implements Initializable {
    Stage stage;
    Scene scene;
    Parent root;
    
    //Connection to DB
    Connection connect = MainFXApp.con;
    
    private MainFXApp main;
    
    public void setMain(MainFXApp mainIn){
        main = mainIn;
    }
    
    @FXML private Button backButton;
    @FXML private Button populateButton;
    @FXML private Button dateChanged;
    @FXML private Label firstField;
    @FXML private Label lastField;
    @FXML private Label birthField;
    @FXML private Label addressField1;
    @FXML private Label sexChoice;
    @FXML private Label phoneField;
    @FXML private Label soc;
    @FXML private Label diagnosis;
    @FXML private ChoiceBox patients;
    @FXML private ChoiceBox dateModified;
    
    List<String[]> peeps = new ArrayList<>();
    List<String> entry = new ArrayList<>();
    List<String> dates = new ArrayList<>();
    
    String pNum;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            String whoAreYou = ("SELECT LName,FName,BDate FROM Personal_Info");
            PreparedStatement ps = connect.prepareStatement(whoAreYou);
            ResultSet results = ps.executeQuery();
            
            String lname, fname;
            Date bdate;
            
            while(results.next()){
                lname = results.getString("LName");
                fname = results.getString("FName");
                bdate = results.getDate("BDate");
                
                LocalDate date = bdate.toLocalDate();
                
                String[] tempArr = new String[4];
                tempArr[1]=fname;
                tempArr[0]=lname;
                tempArr[2]=date.toString();
                tempArr[3]=lname+", "+fname+" "+date.toString();
                
                peeps.add(tempArr);
            }
            peeps.forEach((entr) -> {
                entry.add(entr[3]);
            });
            Collections.sort(entry);
            patients.setItems(FXCollections.observableArrayList(entry));
            
            final List options = patients.getItems();
            patients.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    
                }
            });
            
        }catch(SQLException e){
        }
    }
    
    @FXML public void populateFromSelection(ActionEvent click) {
        String selection = patients.getValue().toString();
        peeps.forEach((entr) -> {
            if(selection.equals(entr[3])){
                try{
                    String whoAreYou = "SELECT FName,LName,BDate,Ssn,Address,Sex,Phone_Number,Diagnosis,time_update,PNumber FROM Personal_Info WHERE FName='"+entr[1]+"' AND LName='"+entr[0]+"' AND BDate='"+entr[2]+"'";
                    PreparedStatement ps = connect.prepareStatement(whoAreYou);
                    ResultSet results = ps.executeQuery();
                    
                    if(results.first()){
                        firstField.setText(results.getString("FName"));
                        lastField.setText(results.getString("LName"));
                        birthField.setText(results.getString("BDate"));
                        addressField1.setText(results.getString("Address"));
                        phoneField.setText(results.getString("Phone_Number"));
                        soc.setText(results.getString("Ssn"));
                        diagnosis.setText(results.getString("Diagnosis"));
                        sexChoice.setText(results.getString("Sex"));
                        String dateMod = results.getString("time_update");
                        dates.add(dateMod);
                        pNum = results.getString("PNumber");
                    
                    
                        String whenAreYou = "SELECT Date_Modified FROM Personal_History WHERE PNumber = '"+pNum+"'";
                        ps = connect.prepareStatement(whenAreYou);
                        ResultSet dateResults = ps.executeQuery();
                        
                        while(dateResults.next()){
                            //"SELECT FName,LName,BDate,Address,Sex,Phone_Number,Diagnosis,Date_Modified FROM Personal_History WHERE PNumber = '"+pNum+"'";
                            String tempDate = dateResults.getString("Date_Modified");
                            if(!dates.contains(tempDate))
                                dates.add(tempDate);
                        }
                        Collections.sort(dates,Collections.reverseOrder());
                        dateModified.setItems(FXCollections.observableArrayList(dates));
                        
                        final List dateOptions = dateModified.getItems();
                        dateModified.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
                            @Override
                            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                                
                            }
                        });
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    
    @FXML public void dateChange(ActionEvent click) {
        String selection = dateModified.getValue().toString();
        String whenAreYouNot = "SELECT FName,LName,BDate,Address,Sex,Phone_Number FROM Personal_History WHERE PNumber = '"+pNum+"' AND Date_Modified = '"+dateModified.getValue().toString()+"'";
        try {
            PreparedStatement ps = connect.prepareStatement(whenAreYouNot);
            ResultSet results = ps.executeQuery();
            if(results.first()){
                firstField.setText(results.getString("FName"));
                lastField.setText(results.getString("LName"));
                birthField.setText(results.getString("BDate"));
                addressField1.setText(results.getString("Address"));
                phoneField.setText(results.getString("Phone_Number"));
                sexChoice.setText(results.getString("Sex"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InformationHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML public void buttonClicked(ActionEvent click) throws Exception {
        try{
            stage = (Stage) ((Button) click.getSource()).getScene().getWindow();
        
            String source = ((Node) click.getSource()).getId();
            
            switch (source) {
                case "backButton":
                    root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
                    patientViewController act2 = new patientViewController();
                    act2.setMain(main);
                    break;
		default:
                    break;
            }
            //sets fxml file as a scene
            scene = new Scene(root);
            //loads the scene on top of whatever stage the button is in
            stage.setScene(scene);
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
}

