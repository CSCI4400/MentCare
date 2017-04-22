// based on the https://github.com/HackeaMesta/Game-Store
/**
 * Another area where the DB config is stored (also kept in MainFXApp and Model.DBConnection)
 */
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {//

	Connection connection = null;
	private static final String USERNAME = "mentcare";
	private static final String PASSWORD = "mentcare1";
	private static final String CONN = ("jdbc:mysql://164.132.49.5/");
	private static final String DB = "mentcare2?autoReconnect=true&useSSL=false";
	/**
	 * Method that connects us to the SQL database
	 * @return the database connection if connection is successful
	 * @throws SQLException SQL error
	 */
	// connection method that connects us to the MySQL database
	public static Connection getConnection() throws SQLException {
		// This is the syntax for connecting to the DB
		// identify DB 'iVoterDB' then use a username 'root' and for this user
		// it
		// has no password
		System.out.println("Establishing connection to database");
		Connection conn = DriverManager.getConnection((CONN + DB), USERNAME, PASSWORD);
		System.out.println("Connection success");
		return conn;
	}
	/**
	 * Method that displays errors if the connection fails, like if you use the wrong username/password
	 * @param ex The exception that was thrown by SQL
	 */
	public static void displayException(SQLException ex) {
		System.out.println("Error Message: " + ex.getMessage());
		System.out.println("Error Code: " + ex.getErrorCode());
		System.out.println("SQL Status: " + ex.getSQLState());

	}
}
