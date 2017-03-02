package mentcare;

import java.sql.Connection;
import java.sql.DriverManager;

public class InitialDBConnection implements Runnable {
	
	Connection Con1;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			
			System.out.println("Establishing connection to database");
			
			Con1 = DriverManager.getConnection("jdbc:mysql://164.132.49.5:3306", "mentcare", "mentcare1");
			
			System.out.println("Connection success");
			
			viewMenu.con = Con1;
			
	    } catch(Exception e){
	    	e.printStackTrace();
			}
		
		}	

}
