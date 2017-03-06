package common;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class Patient {
	String id = null;
	String first_name = null;
	String last_name = null;
    String email_address = null;
    String home_address = null;
    String last_visit = null;
    String next_visit = null;
    String ssn = null;
    String photo = null;

    public Patient(String id, String first_name, String last_name, String email_address, String home_address, String last_visit,
			String next_visit, String ssn, String photo) {
		super();
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
    public Patient(String first_name, String last_name, String home_address) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.home_address = home_address;

	}
    public void updateAddress(String newAddress) throws ClassNotFoundException, SQLException{
    	this.home_address = newAddress;
    	// Load the JDBC driver
		//This is no longer needed
//	    Class.forName("com.mysql.jdbc.Driver");
	    System.out.println("Driver loaded");

	    // Connect to a database
	    java.sql.Connection connection = DriverManager.getConnection
				("jdbc:mysql://198.71.227.86:3306/mentcare_db", "TeamTigerWoods", "GOATGOAT");
	    //Create query
	    Statement stmt = connection.createStatement();
	      String sql = "UPDATE patients " +
	                   "SET home_address = " + this.home_address +" WHERE id = ("+ this.id +")";
	      stmt.executeUpdate(sql);

    }

	//Start Initialize getters and Setters for private variables***********************************
    /**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}
	/**
	 * @param first_name the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}
	/**
	 * @param last_name the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	/**
	 * @return the email_address
	 */
	public String getEmail_address() {
		return email_address;
	}
	/**
	 * @param email_address the email_address to set
	 */
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}
	/**
	 * @return the home_address
	 */
	public String getHome_address() {
		return home_address;
	}
	/**
	 * @param home_address the home_address to set
	 */
	public void setHome_address(String home_address) {
		this.home_address = home_address;
	}
	/**
	 * @return the last_visit
	 */
	public String getLast_visit() {
		return last_visit;
	}
	/**
	 * @param last_visit the last_visit to set
	 */
	public void setLast_visit(String last_visit) {
		this.last_visit = last_visit;
	}
	/**
	 * @return the next_visit
	 */
	public String getNext_visit() {
		return next_visit;
	}
	/**
	 * @param next_visit the next_visit to set
	 */
	public void setNext_visit(String next_visit) {
		this.next_visit = next_visit;
	}
	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}
	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	/**
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}
	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	//Start Initialize getters and Setters for private variables***********************************

}
