package model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class ListPatient {
	//Variables that are initialized for data coming from patients history table
	int history_id;
	String surgeries;
	String recovery_time;
	String prescription;
	String threat_level;

	String previous_harm_to_others;
	String previous_harm_to_self;


	//Variables that are initialized for data coming from patients table
	int id;
	String first_name = null;
	String last_name = null;
    String email_address = null;
    String home_address = null;
    String last_visit = null;
    String next_visit = null;
    String ssn = null;
    String photo = null;
    String phone_number = null;
    String last_changed_by = null;



    // Constructor with all of the patient information
    public ListPatient(int id, int patient_history_id, String first_name, String last_name, String email_address, String home_address, String last_visit,
			String next_visit, String ssn, String photo) {
		super();
		this.history_id = patient_history_id;
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email_address = email_address;
		this.home_address = home_address;
		this.last_visit = last_visit;
		this.next_visit = next_visit;
		this.ssn = ssn;
		this.photo = photo;
	}

    public ListPatient(int id, String first_name, String last_name, String home_address, String phone_number, String last_changed_by) {

		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.home_address = home_address;
		this.phone_number = phone_number;
		this.last_changed_by = last_changed_by;

	}



	//Patient constructor with little information for testing
    public ListPatient(String first_name, String last_name, String home_address) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.home_address = home_address;

	}

    public ListPatient(){

    }
    public ListPatient(String first_name) {
		super();
		this.first_name = first_name;


	}

	//******************Getters and Setters for everything except id which will not be able to be changed
	public int getId() {
		return id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getPhone_Number() {
		return phone_number;
	}

	public String getLast_Changed_By() {
		return last_changed_by;
	}
	public void setLast_Changed_By(String last_changed_by) {
		this.last_changed_by = last_changed_by;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail_address() {
		return email_address;
	}
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}
	public String getHome_address() {
		return home_address;
	}
	public void setHome_address(String home_address) {
		this.home_address = home_address;
	}
	public String getLast_visit() {
		return last_visit;
	}
	public void setLast_visit(String last_visit) {
		this.last_visit = last_visit;
	}
	public String getNext_visit() {
		return next_visit;
	}
	public void setNext_visit(String next_visit) {
		this.next_visit = next_visit;
	}
	public String getssn() {
		return ssn;
	}
	public void setssn(String ssn) {
		this.ssn = ssn;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getPatient_history_id() {
		return history_id;
	}
	public void setPatient_history_id(int patient_history_id) {
		this.history_id = patient_history_id;
	}

	public String getSurgeries() {
		return surgeries;
	}
	public void setSurgeries(String surgeries) {
		this.surgeries = surgeries;
	}
	public String getRecovery_time() {
		return recovery_time;
	}
	public void setRecovery_time(String recovery_time) {
		this.recovery_time = recovery_time;
	}
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public String getThreat_level() {
		return threat_level;
	}
	public void setThreat_level(String threat_level) {
		this.threat_level = threat_level;
	}

	public String getPrevious_harm_to_others() {
		return previous_harm_to_others;
	}
	public void setPrevious_harm_to_others(String previous_harm_to_others) {
		this.previous_harm_to_others = previous_harm_to_others;
	}
	public String getPrevious_harm_to_self() {
		return previous_harm_to_self;
	}
	public void setPrevious_harm_to_self(String previous_harm_to_self) {
		this.previous_harm_to_self = previous_harm_to_self;
	}








	//****************************end getters and setters





	//To string method to print patient information
	public String toString(){
		String result;
		result =  "First Name: " + getFirst_name() + "\n" +
				  "Last Name: " + getLast_name()  + "\n" +
				  "Home Address: " + getHome_address() + "\n" +
				  "Email : " + getEmail_address()  + "\n" +
				  "Last Visit: " + getLast_visit() + "\n" +
				  "Next Visit: " + getNext_visit()  + "\n" +
				  "Socical Security Number: " + getssn() + "\n";

				  return result;


	}


    //********************************update methods for database****************************************
	public void Update_Patient_Address(String newAddress) throws SQLException, ClassNotFoundException{
		//This is no longer needed
	    Class.forName("com.mysql.jdbc.Driver");
	    System.out.println("Driver loaded");

	    // Connect to a database
	    java.sql.Connection connection = DriverManager.getConnection
				("jdbc:mysql://164.132.49.5:3306/mentcare2", "mentcare", "mentcare1");
	    System.out.println("Database connected");

	    // Create a statement
	    //Statement statement = connection.createStatement();

	    // Execute a statement
	   // ResultSet resultSet = statement.executeQuery
	     // ("update patients set home_address = " + newAddress + " where id = " + p.getPatient_history_id()) ;

	    String query = "update patients set home_address = ?  where id = ?";
	      java.sql.PreparedStatement preparedStmt = connection.prepareStatement(query);
	      preparedStmt.setString  (1, newAddress);
	      preparedStmt.setInt(2, this.id);

	      // execute the java preparedstatement
	      preparedStmt.executeUpdate();
	    // Close the connection
	    connection.close();
	    this.home_address = newAddress;
	  }
	public void Update_Patient_Danger_level(String newdanger) throws SQLException, ClassNotFoundException {
		//This is no longer needed
	    Class.forName("com.mysql.jdbc.Driver");
	    System.out.println("Driver loaded");

	    // Connect to a database
	    java.sql.Connection connection = DriverManager.getConnection
				("jdbc:mysql://164.132.49.5:3306/mentcare2", "mentcare", "mentcare1");
	    System.out.println("Database connected");

	    // Create a statement
	    //Statement statement = connection.createStatement();

	    // Execute a statement
	   // ResultSet resultSet = statement.executeQuery
	     // ("update patients set home_address = " + newAddress + " where id = " + p.getPatient_history_id()) ;

	    String query = "update patients_history set threat_level = ?  where history_id = ?";
	      java.sql.PreparedStatement preparedStmt = connection.prepareStatement(query);
	      preparedStmt.setString  (1, newdanger);
	      preparedStmt.setInt(2, this.history_id);

	      // execute the java preparedstatement
	      preparedStmt.executeUpdate();
	    // Close the connection
	    connection.close();
	    this.threat_level = newdanger;

	}


}
