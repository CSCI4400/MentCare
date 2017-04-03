//add to model during integration
package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfigModel {
	Connection con;
	public DBConfigModel(){
		this.con = connectDB();
	}
	
	public Connection connectDB(){
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			String dbString = "jdbc:mysql://164.132.49.5:3306/mentcare2";
			String user = "mentcare";
			String password = "mentcare1";

			//connections
			Connection conn = (Connection) DriverManager.getConnection(dbString, user, password);
			return conn;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
