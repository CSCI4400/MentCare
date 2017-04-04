/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import application.DBConfig;
import application.MainFXApp;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import model.Patient;

/**
 *
 * @author sad2e
 */
public class UpdatePatientController implements Initializable {
    Stage stage;
    Scene scene;
    Parent root;
    
    private MainFXApp main;
    
    public void setMain(MainFXApp mainIn){
        main = mainIn;
    }
    
    @FXML private Button addButton;
    @FXML private Button backButton;
    @FXML private TextField firstField;
    @FXML private TextField lastField;
    @FXML private TextField birthField;
    @FXML private TextField addressField1;
    @FXML private ChoiceBox sexChoice;
    @FXML private TextField phoneField;
    @FXML private TextField soc;
    @FXML private TextField diagnosis;
    @FXML private ChoiceBox patients;
    @FXML private Button populate;
    
    List<String[]> peeps = new ArrayList<>();
    List<String> entry = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            String whoAreYou = ("SELECT LName,FName,BDate FROM Personal_Info");
            Connection connect = DBConfig.getConnection();
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
                    String whoAreYou = ("SELECT FName,LName,BDate,Ssn,Address,Sex,Phone_Number,Diagnosis FROM Personal_Info WHERE FName='"+entr[1]+"' AND LName='"+entr[0]+"' AND BDate='"+entr[2]+"'");
                    Connection connect = DBConfig.getConnection();
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
                        sexChoice.setValue(results.getString("Sex"));
                    }
                    
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    
    @FXML public void onAddPatient(ActionEvent click) throws Exception {
        try{
            stage = (Stage) ((Button) click.getSource()).getScene().getWindow();
        
            String source = ((Node) click.getSource()).getId();
            
            switch (source) {
		case "addButton":
                    try{
                        if(!firstField.getText().trim().equals("") &&
                                !lastField.getText().trim().equals("") &&
                                !birthField.getText().trim().equals("") &&
                                !addressField1.getText().trim().equals("") &&
                                !sexChoice.getValue().equals("") &&
                                !diagnosis.getText().trim().equals("") &&
                                soc.getText().length() == 9 &&
                                !(phoneField.getText().trim().equals("") ||
                                phoneField.getText().trim().length() != 12)){
                            String first = firstField.getText().trim();
                            String last = lastField.getText().trim();
                            String birth = birthField.getText().trim();
                            String addr = addressField1.getText().trim();
                            String sex = sexChoice.getValue().toString();
                            String phNum = phoneField.getText().trim();
                            String social = soc.getText().trim();
                            String diag = diagnosis.getText().trim();
                            
                            String copyQuery = "INSERT INTO Personal_History (Address, BDate, danger_lvl, FName, LName, Phone_Number, PNumber, Sex) SELECT Address, BDate, Danger_lvl, FName, LName, Phone_Number, PNumber, Sex FROM Personal_Info WHERE Phone_Number='"+phNum+"'";
                            
                            String patQuery = "UPDATE Personal_Info SET FName = '"+first+"', LName = '"+last+"', BDate = '"+birth+"', Address = '"+addr+"', Sex = '"+sex+"', Phone_Number = '"+phNum+"', Dead = 'no', Ssn = '"+social+"', Diagnosis = '"+diag+"' WHERE Phone_Number = '"+phNum+"'";
                            
                            Connection conn = DBConfig.getConnection();
                            PreparedStatement copyPat = conn.prepareStatement(copyQuery,Statement.RETURN_GENERATED_KEYS);
                            copyPat.execute();
                            PreparedStatement addPat = conn.prepareStatement(patQuery,Statement.RETURN_GENERATED_KEYS);
                            
                            /*
                            addPat.setString(1, first);
                            addPat.setString(2, last);
                            addPat.setDate(3, Date.valueOf(birth));
                            addPat.setString(4, addr);
                            addPat.setString(5, sex);
                            addPat.setString(6, phNum);
                            addPat.setString(7,"no");
                            addPat.setString(8, social);
                            addPat.setString(9, diag);
                            */
                            
                            System.out.println("Query Sent" + addPat.toString());
                            
                            int accepted  = addPat.executeUpdate();
                            
                            if(accepted == 1){
                                root = FXMLLoader.load(getClass().getResource("/view/AddPatient.fxml"));
                                AddPatientController act1 = new AddPatientController();
                                act1.setMain(main);
                                break;
                            }
                            else{
                                System.out.println("Query failed");
                                root = FXMLLoader.load(getClass().getResource("/view/AddPatient.fxml"));
                                AddPatientController act1 = new AddPatientController();
                                act1.setMain(main);
                                break;
                            }
                            
                        }
                        else{
                            break;
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
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
        }catch(IOException e){
        }
    }
}
