package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {

	private static final String USERNAME = "mentcare";
	private static final String PASSWORD = "mentcare1";
	private static final String CONN_STRING = "jdbc:mysql://164.132.49.5/mentcare";

	
	public static Connection getConnection() throws SQLException{
		
		return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
	}


	public static void displayException(SQLException ex){

		System.err.println("Error Message: " + ex.getMessage());
		System.err.println("Error Code: " + ex.getErrorCode());
		System.err.println("SQL Status: " + ex.getSQLState());

	}
}
