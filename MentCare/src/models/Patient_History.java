package models;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Patient_History {
	public Patient_History(Patient p) throws ClassNotFoundException, SQLException{
		// Load the JDBC driver
					//This is no longer needed  
				    Class.forName("com.mysql.jdbc.Driver");
				    System.out.println("Driver loaded");

				    // Connect to a database
				    java.sql.Connection connection = DriverManager.getConnection
							("jdbc:mysql://198.71.227.86:3306/mentcare_db", "TeamTigerWoods", "GOATGOAT");
				    System.out.println("Database connected");

				    // Create a statement
				    Statement statement = connection.createStatement();

				    // Execute a statement
				    ResultSet resultSet = statement.executeQuery
				      ("select * from patients_history where history_id = " + p.getPatient_history_id()) ;
				    //Initialize place holding variables for incoming data	
				    
				    String surgeries = null;
					String recovery_time = null;
					String prescription = null;
					String threat_level = null;
					String previous_harm_to_others = null;
					String previous_harm_to_self = null;
				    
				    
				    
				    // Iterate through the result and print the student names
				    while (resultSet.next()){
				      surgeries = resultSet.getString("surgeries");	
				      recovery_time = resultSet.getString("recovery_time");
				      prescription = resultSet.getString("prescription");
				      threat_level = resultSet.getString("threat_level");
				      previous_harm_to_self = resultSet.getString("previous_harm_to_self");
				      previous_harm_to_others = resultSet.getString("previous_harm_to_others");
				      
				      //Add the Patients history to the patient object
				      p.setSurgeries(surgeries);
				      p.setRecovery_time(recovery_time);
				      p.setPrescription(prescription);
				      p.setThreat_level(threat_level);
				      p.setPrevious_harm_to_others(previous_harm_to_others);
				      p.setPrevious_harm_to_self(previous_harm_to_self);
				    }
				    
				    // Close the connection
				    connection.close();
				  }
		

	
		
	}

