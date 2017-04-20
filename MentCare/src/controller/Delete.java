//Team 5 Leonardo Mazuran
package controller;

import model.delete;

import application.DBConfig;

import application.MainFXApp;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;



	
	public class Delete implements Initializable{
		
	    @FXML
	    private TableView<delete> table_delete;
	   
	    
	    @FXML
	    private TableColumn<delete, String> Column_UserId;
	    @FXML
	    private TableColumn<delete, String> Column_Name;
	    @FXML
	    private Button Button_search;
	    @FXML
	    private TextField delete_searchField;
	    @FXML
	    private Label ds_label_name;
	    @FXML private Button ds_ClickYes;
	    @FXML private Button ds_ClickNo;
	    @FXML private Button Button_back;
	   private static String data1,data2;
	    
	    
	  
	    
	    Stage stage;
	    Scene scene;
	    Parent root;
	    private MainFXApp main;

	    public void setMain(MainFXApp mainIn){
	        main = mainIn;
	    }
	       
	    

	    public static delete store;
	   
	    private ObservableList<delete> details;

	    private DBConfig dc;

	    @Override
	    public void initialize(URL url, ResourceBundle rb) {
	        // TODO
	        dc = new DBConfig();
	    }

	    @FXML
	    private void ClickDeleteSearch(ActionEvent event) {
	    	getdata();
	    }
	    	
public void getdata(){	    	
	String name = delete_searchField.getText();
	    	 try {
	             Connection conn = DBConfig.getConnection();
	             details = FXCollections.observableArrayList();
	            // Execute query and store result in a resultset
ResultSet rs = conn.createStatement().executeQuery("Select idNum, FullName from Users where idNum REGEXP '^"+name+ "'OR FullName REGEXP '^"+name+"'");
	    	//String query = "Select Fname, Lname FROM Personal_Info Where Fname =? OR Lname = ?";
	    	//ResultSet rs = null;
	    	//try(Connection conn= dc.getConnection()){
	    		
				
	    		//PreparedStatement authenticate = conn.prepareStatement(query);
	    		//authenticate.setString(1, firstname);
	    		//authenticate.setString(2, lastname);
	    	
	    	
	    		 //rs = authenticate.executeQuery();
	             while (rs.next()) {
	                 //get string from db,whichever way 
	            	
	                 
	               details.add(new delete(rs.getString(1), rs.getString(2)));
	                 
	                 
	             }

	        } catch (SQLException ex) {
	            System.err.println("Error"+ex);
	        }
	    	 
	        
	        //Set cell value factory to tableview.
	        //NB.PropertyValue Factory must be the same with the one set in model class.
	        Column_UserId.setCellValueFactory(new PropertyValueFactory<>("Fname"));
	        Column_Name.setCellValueFactory(new PropertyValueFactory<>("Lname"));
	        

	        table_delete.setItems(null);
	        table_delete.setItems(details);
	     click();
	    }
	        
	    
	    
	    
	
	    
	    
public void click(){
	      
	          
	          table_delete.setOnMouseClicked(event -> {
	              if (event.getClickCount() == 2 && (! event.isPrimaryButtonDown()) ) {
	            	  TablePosition pos = (TablePosition) table_delete.getSelectionModel().getSelectedCells().get(0);
	            	  int row = pos.getRow();
	            	  

	            	// Item here is the table view type:
	            	delete item = table_delete.getItems().get(row);

	            	

	            	// this gives the value in the selected cell in selected columns:
	            	 data1 = (String) Column_UserId.getCellObservableValue(item).getValue();
	            	 data2 = (String) Column_Name.getCellObservableValue(item).getValue();

	            	
	                  try {
						sure(data1,data2);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
	              }
	         
	         
	      });
	    }
	    
	  public void sure(String data1, String data2) throws Exception{
		 // ds_label_name.setText(data2);
		  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/deleteSure.fxml"));
		  fxmlLoader.setController(this);
			Parent root = (Parent) fxmlLoader.load();
			

			Stage stage = new Stage();
			stage.initOwner(MainFXApp.primaryStage);
			stage.initModality(Modality.WINDOW_MODAL);
			
			stage.setScene(new Scene(root));
			stage.show();
			ds_label_name.setText(data2);
			
			
		}
	  @FXML
	  protected void ClickYes(ActionEvent event) {
		 
		 String query = ("Delete from Users where idnum = ? ");
		 
				  try (Connection conn = DBConfig.getConnection();
			    			PreparedStatement statement = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);){
				  statement.setString(1, data1);
				  statement.execute();
				  stage = (Stage)((Button) event.getSource()).getScene().getWindow();
					stage.close(); 
					getdata();
				  } catch (SQLException e) {
					
					e.printStackTrace();
				}
	  }
	  @FXML
	  protected void ClickNo(ActionEvent event) {
		  stage = (Stage)((Button) event.getSource()).getScene().getWindow();
			stage.close();  
	  }
	  @FXML
	    private void ClickBack(ActionEvent event) throws Exception {
		  stage = (Stage)((Button) event.getSource()).getScene().getWindow();
	    	root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
	    	businessViewController act2 = new businessViewController();
	          act2.setMain(main);
	    	scene = new Scene(root);
	    	stage.setScene(scene);
	    	
	        
	    	
	    }
}
