/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.hawksoft;

/**
 *
 * @author sad2e_000
 */

//swiped from team Butterscotch

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {

    // connection  method that connects us to the MySQL database
    public static Connection getConnection() throws SQLException{
        //This is the syntax for connecting to the DB
	// identify DB 'iVoterDB' then use a username 'root' and for this user it
	//has no password
	return DriverManager.getConnection("jdbc:mysql://164.132.49.5:3306/mentcare","mentcare","mentcare1");
    }

    // method that displays our errors in more detail if connection fails
    //just basic errors that DB will throw back at you.
    //example try using a bad username/password combo
    public static void displayException(SQLException ex){
	System.out.println("Error Message: " + ex.getMessage());
	System.out.println("Error Code: " + ex.getErrorCode());
	System.out.println("SQL Status: " + ex.getSQLState());

    }
}