package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import controller.ViewMenuController;

public class InitialDBConnection implements Runnable {
	public static final String DBPASSWORD = ""; //make sure to add password
	Connection Con1;

	@Override
	public void run() {
		
		try{
			
			System.out.println("Establishing connection to database");
			
			Con1 = DriverManager.getConnection("jdbc:mysql://164.132.49.5:3306", "mentcare", DBPASSWORD); 
			
			System.out.println("Connection success");
			
			ViewMenuController.con = Con1;
			
			//Test statement
			
			/*String selstmt = "SELECT * FROM mentcare.Personal_Info, mentcare.Medical_Info WHERE mentcare.Personal_Info.PNumber = mentcare.Medical_Info.PNum";
			
			Statement stmt = Con1.createStatement();
			
			ResultSet rs = stmt.executeQuery(selstmt);
			
			while(rs.next()){
				System.out.println(rs.getInt("PNumber"));
				System.out.println(rs.getString("FName"));
			}
			
			//Closing resultSet after reading results into array list
			stmt.close();
			rs.close();
			*/
	    } catch(Exception e){
	    	e.printStackTrace();
			}
		
		}	

}
