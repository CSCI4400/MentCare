//Made by: Leo

package controller;

import model.search;
import model.search1;
import application.DbConfig;
import application.Main;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MainController implements Initializable{
	
	    @FXML
	    private TableView<search> tableP;
	    @FXML
	    private TableColumn<search, String> columnfn;
	    @FXML
	    private TableColumn<search, String> columnln;
	    @FXML
	    private TableColumn<search, String> columnDob;
	    @FXML
	    private TableColumn<search, String> columnAddress;
	    @FXML
	    private TableColumn<search, String> columnSex;
	    @FXML
	    private TableColumn<search, String> columnPhone;
	    
	    @FXML
	    private TableColumn<search, String> columnDl;
	    @FXML
	    private TableColumn<search, String> columnPn;
	    @FXML
	    private TableColumn<search, String> columnLu;
	    @FXML
	    private TableView<search1> tableD;
	    @FXML
	    private TableColumn<search1, String> dc_columnAt;
	    @FXML
	    private TableColumn<search1, String> dc_columnAd;
	    @FXML
	    private TableColumn<search1, String> dc_columnfn;
	    @FXML
	    private TableColumn<search1, String> dc_columnPn;
	    @FXML
	    private TableColumn<search1, String> dc_columnDid;
	    @FXML
	    private TextField ps_tf_n;
	    @FXML
	    private TextField ps_tf_id;
	    @FXML
	    private Button ps_b;
	    @FXML
	    private Button dc_b_cu;
	    @FXML
	    private Label dc_lb_cu;
	    @FXML private Button dc_b_gb;

	    
	    @FXML private Button ps_b_Doc;
	    @FXML private Button dc_b_refresh;
	   
	    
	    Stage stage;
	    Scene scene;
	    Parent root;

	    //Initialize observable list to hold out database data
	    private ObservableList<search> data;
	    private ObservableList<search1> details;

	    private DbConfig dc;

	    @Override
	    public void initialize(URL url, ResourceBundle rb) {
	        // TODO
	        dc = new DbConfig();
	    }

	    @FXML
	    private void ClickSearch(ActionEvent event) {
	    	
	    	String name = ps_tf_id.getText();
	    	
	    	
    	
	    	 try {
	             Connection conn = DbConfig.getConnection();
	             data = FXCollections.observableArrayList();
	            // Execute query and store result in a resultset
ResultSet rs = conn.createStatement().executeQuery("SELECT Fname, Lname, Bdate, Address, Sex, Phone_number, Danger_lvl, Pnumber, time_update from Personal_Info Where Pnumber REGEXP '^"+name+ "'OR Fname REGEXP '^"+name+"' OR Lname REGEXP'^"+name+ "'");
	    	//String query = "Select Fname, Lname FROM Personal_Info Where Fname =? OR Lname = ?";
	    	//ResultSet rs = null;
	    	//try(Connection conn= dc.getConnection()){
	    		
				
	    		//PreparedStatement authenticate = conn.prepareStatement(query);
	    		//authenticate.setString(1, firstname);
	    		//authenticate.setString(2, lastname);
	    	
	    	
	    		 //rs = authenticate.executeQuery();
	             while (rs.next()) {
	                 //get string from db,whichever way 
	            	
	                 
	                data.add(new search(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
	                		rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
	                 
	                 
	             }

	        } catch (SQLException ex) {
	            System.err.println("Error"+ex);
	        }
	    	 
	        
	        //Set cell value factory to tableview.
	        //NB.PropertyValue Factory must be the same with the one set in model class.
	        columnfn.setCellValueFactory(new PropertyValueFactory<>("Fname"));
	        columnln.setCellValueFactory(new PropertyValueFactory<>("Lname"));
	        columnDob.setCellValueFactory(new PropertyValueFactory<>("Dob"));
	        columnAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
	        columnSex.setCellValueFactory(new PropertyValueFactory<>("Sex"));
	        columnPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
	        columnDl.setCellValueFactory(new PropertyValueFactory<>("Dl"));
	        columnPn.setCellValueFactory(new PropertyValueFactory<>("Pn"));
	        columnLu.setCellValueFactory(new PropertyValueFactory<>("Lu"));

	        
	       
	        
	        tableP.setItems(null);
	        tableP.setItems(data);
	        columnDl.setCellFactory(column -> {
	            return new TableCell<search, String>() {
	                @Override
	                protected void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);

	                    setText(empty ? "" : getItem().toString());
	                    setGraphic(null);

	                    TableRow<search> currentRow = getTableRow();

	                    if (!isEmpty()) {

	                        if(item.equals("4")|| item.equals("5")) 
	                            currentRow.setStyle("-fx-background-color:lightcoral");
	                        else if (item.equals("2")|| item.equals("3"))
	                            currentRow.setStyle("-fx-background-color:yellow");
	                        else 
	                            currentRow.setStyle("-fx-background-color:lightgreen");

	                    }
	                }
	            };
	        });
	        
	        

	    }
		
	public void PullPatient(ActionEvent event) throws Exception {
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
    			Appointment temp = new Appointment(AppNum, Pnum, Pname, DocID, apDate, apTime, passed, missed);

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
            patientTable.setItems(appList);
		    }
			} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
	}
	    
		public void ClickDoctorMode(ActionEvent event) throws Exception {
	    	
	    	stage = (Stage)((Button) event.getSource()).getScene().getWindow();
	    	root = FXMLLoader.load(getClass().getResource("/view/Doctor.fxml"));
	    	scene = new Scene(root);
	    	stage.setScene(scene);
	    	
	    	
	    	
	    } public void ClickGoBack(ActionEvent event) throws Exception {
	    	stage = (Stage)((Button) event.getSource()).getScene().getWindow();
	    	root = FXMLLoader.load(getClass().getResource("/view/Search.fxml"));
	    	scene = new Scene(root);
	    	stage.setScene(scene);
	    	
	    	
	    }
	   public void ClickUpdate(ActionEvent event) throws Exception{
		   offlinecheck();
	   }
	    
	    public void ClickRefresh(ActionEvent event) throws Exception {
	    	
	    Gettxtdata();
	       
	    } 
	    public void offlinecheck(){
	    	String query = "Select PI.Pnumber, PI.FName, PI.Lname, PH.Prescription, PI.Danger_lvl FROM Personal_Info AS PI, Prescription_History AS PH Where PI.PNumber = PH.Personid";
	    	List data = new ArrayList();
			try (Connection conn = DbConfig.getConnection();
					Statement sm = conn.createStatement();
					ResultSet rs = sm.executeQuery(query);) {
				while (rs.next()) {
					String AppId = rs.getString("PI.Pnumber");
					String Pnum = rs.getString("PI.Fname");
					String Pname = rs.getString("PI.Lname");
					String DocId = rs.getString("PH.Prescription");
					String apDate = rs.getString("PI.Danger_lvl");
					
					data.add(AppId + "/" +Pnum+ "/"+Pname +"/"+DocId+"/"+apDate);
					
				}
				File file = new File ("C:\\test\\Current_Appointment.txt");
				file.delete();
				writeToFile(data, "C:\\test\\Current_Appointment.txt");
				
				rs.close();
				sm.close();
				
				dc_lb_cu.setText("Successfully Updated!");
				dc_lb_cu.setTextFill(Color.GREEN);
				
				
			} catch (SQLException ex) {
				System.out.println(ex);
				dc_lb_cu.setText("Failed To Update! Check Connection");
				dc_lb_cu.setTextFill(Color.RED);
				
			}
			
	    
	    }
	    private static void writeToFile(java.util.List list, String path){
	    	BufferedWriter out = null;
	    	try{
	    		File file = new File(path);
	    		out = new BufferedWriter (new FileWriter(file, true));
	    		for(Object s : list){
	    			out.write((String)s);
	    			out.newLine();
	    			
	    		}
	              
	    		
	    		out.close();
	    	}catch (IOException e){
	    		System.out.println(e);
	    		
	    	}
	    	
	    	
	    }
	    
	   
public void Gettxtdata() throws Exception{
	Collection<search1> list1 = Files.readAllLines(new File("C:\\test\\Current_Appointment.txt").toPath()).stream().map(line -> {
        String[] details = line.split("/");
        search1 cd = new search1();
        cd.setA(details[0]);
        cd.setB(details[1]);
        cd.setC(details[2]);
        cd.setD(details[3]);
        cd.setE(details[4]);
        return cd;
    })
    .collect(Collectors.toList());




ObservableList<search1> details = FXCollections.observableArrayList(list1);




dc_columnAt.setCellValueFactory(data -> data.getValue().AProperty());
dc_columnAd.setCellValueFactory(data -> data.getValue().BProperty());
dc_columnfn.setCellValueFactory(data -> data.getValue().CProperty());
dc_columnPn.setCellValueFactory(data -> data.getValue().DProperty());
dc_columnDid.setCellValueFactory(data -> data.getValue().EProperty());


tableD.setItems(null);
tableD.setItems(details);
System.out.println("done");
dc_columnDid.setCellFactory(column -> {
    return new TableCell<search1, String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            setText(empty ? "" : getItem().toString());
            setGraphic(null);

            TableRow<search1> currentRow = getTableRow();

            if (!isEmpty()) {

                if(item.equals("4")|| item.equals("5")) 
                    currentRow.setStyle("-fx-background-color:lightcoral");
                else if (item.equals("2")|| item.equals("3"))
                    currentRow.setStyle("-fx-background-color:lightorange");
                else 
                    currentRow.setStyle("-fx-background-color:lightgreen");

            }
        }
    };
});



}


}
	    
