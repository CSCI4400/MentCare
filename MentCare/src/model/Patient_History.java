package model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Patient_History {
	public Patient_History(ListPatient patient) throws ClassNotFoundException, SQLException{
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
				      ("select * from patients_history where history_id = " + patient.getPatient_history_id()) ;
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
				      patient.setSurgeries(surgeries);
				      patient.setRecovery_time(recovery_time);
				      patient.setPrescription(prescription);
				      patient.setThreat_level(threat_level);
				      patient.setPrevious_harm_to_others(previous_harm_to_others);
				      patient.setPrevious_harm_to_self(previous_harm_to_self);
				    }

				    // Close the connection
				    connection.close();
				  }




	}