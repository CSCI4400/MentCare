package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import controller.ViewMenuController;

public class DBConnection implements Runnable {
	public static final String DBPASSWORD = "mentcare1"; //make sure to add password
	Connection Con1;

	@Override
	public void run() {
		
		try{
			
			System.out.println("Establishing connection to database");
			
			Con1 = DriverManager.getConnection("jdbc:mysql://164.132.49.5:3306", "mentcare", DBPASSWORD); 
			
			System.out.println("Connection success");
			
			ViewMenuController.con = Con1;
			
	    } catch(Exception e){
	    	e.printStackTrace();
			}
		
		}	

}
