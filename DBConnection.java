package application;


import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DBConnection {
	Connection conn = null;
	public static Connection dbConnection(){
		String url = "jdbc:mysql://164.132.49.5:3306/mentcare2?autoReconnect=true&useSSL=false";
		String user = "mentcare";
		String pass = "mentcare1";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection Successful!");
			return conn;
		}catch(Exception ex){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Connection Not Successful!");
			alert.showAndWait();
			return null;
		}
	}
}