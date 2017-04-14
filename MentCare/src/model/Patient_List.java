package model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Patient;




public class Patient_List  {


	 private List <ListPatient> patient_list = new ArrayList<>();
	 private ObservableList<ListPatient> patient_observable_list = FXCollections.observableArrayList();

	 public Patient_List() throws SQLException, ClassNotFoundException{
		// Load the JDBC driver
			//This is no longer needed
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded");

		    // Connect to a database
		    java.sql.Connection connection = DriverManager.getConnection
					("jdbc:mysql://164.132.49.5:3306/mentcare2", "mentcare", "mentcare1");
		    System.out.println("Database connected");

		    // Create a statement
		    Statement statement = connection.createStatement();

		    // Execute a statement
		    ResultSet resultSet = statement.executeQuery
		      ("select * from Personal_Info");
		    //Initialize place holding variables for incoming data
		    int id = 0;
		    int patient_history_id;
		    String first_name = null;
		    String last_name = null;
		    String email_address = null;
		    String home_address = null;
		    String last_visit = null;
		    String next_visit = null;
		    String phone_number = null;
		    String photo = null;
		    String last_changed_by = null;



		    // Iterate through the result and print the student names
		    while (resultSet.next()){

		      id = resultSet.getInt("PNumber");
		      //patient_history_id = resultSet.getInt("");
		      first_name = resultSet.getString("FName");
		      last_name = resultSet.getString("LName");
		      //email_address = resultSet.getString("email_address");

		      home_address = resultSet.getString("Address");
		      //last_visit = resultSet.getString("last_visit");
		      //next_visit = resultSet.getString("next_visit");

		      //photo = resultSet.getString("photo");
		      phone_number = resultSet.getString("Phone_Number");
		      last_changed_by = resultSet.getString("last_changed_by");

		      ListPatient newPatient = new ListPatient(id, first_name, last_name, home_address, phone_number, last_changed_by);
		      patient_list.add(newPatient);
		      patient_observable_list.add(newPatient);

		    }
		    // Close the connection
		    connection.close();
		  }




	//returns overall size of patient list
	public int size(){

		return patient_list.size();
	}


	//returns patient object at given index
	public ListPatient getPatient(int index){
		return patient_list.get(index);

	}





	public List<ListPatient> getList(){
		return patient_list;
	}
	public ObservableList<ListPatient> getObservableList(){
		return patient_observable_list;
	}

}
