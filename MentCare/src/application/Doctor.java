package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



/**
 * Created by Matthew on 3/3/2017.
 *
 */
public class Doctor {

    public static final int MYSQL_DUPLICATE_PK = 1062;
    public static final int MYSQL_TABLE_DOES_NOT_EXIST = 1146;
    public static final int MYSQL_COLUMN_DOES_NOT_EXIST = 1054;

    Connection con = null;
    Statement stmt = null;
    String query;
    String table;
    Scanner scan = new Scanner(System.in);
    String values = "";

    Connection conn = DBConfig.getConnection();


    private String doctorFName,doctorLName,fullName,doctorSpecialty;



    List<Patient> doctorPatientList = new ArrayList<Patient>();

    Doctor (String fname, String lname , String specialty) throws SQLException {
        this.doctorFName = fname;
        this.doctorLName = lname;
        this.doctorSpecialty = specialty;

    }

    public String getDoctorName ()
    {
        fullName = doctorFName + " " + doctorLName; //if we have to have a method to return the full name
        return fullName;
    }

    public List<Patient> getDoctorPatientList()
    {
        return doctorPatientList; //we can use this to calculate how many patients a specific doctor has
    }

    public void addPatientToDoctor(Patient p)
    // works to add patients to a specific doctor such as:
    //  Doctor d = new Doctor("Jimmy","Brown" ) Patient p = new Patient("Esteban",22,"Male","Alzheimers"); d.addPatientToDoctor(p)

    {

        doctorPatientList.add(p);

        try {

            stmt = conn.createStatement();

            table = Personal_Info(PNumber,LName,FName,BDate,Address,Sex,Phone_number);

            System.out.println("Enter all applicable fields separated by commas and make sure VARCHAR variables are surrounded by single quotation marks.");

            values = scan.nextLine();
            query = "INSERT INTO " + table + " VALUES (" + values + ")";
            stmt.executeUpdate(query);

        }


        catch (SQLException e) {
            //Error codes are used to determine the cause of the failure and the user is notified
            if(e.getErrorCode() == MYSQL_DUPLICATE_PK )
                System.out.println("Insertion fail because a tuple with the same primary key already exists.");
            else if(e.getErrorCode() == MYSQL_TABLE_DOES_NOT_EXIST)
                System.out.println("The table you entered does not exist.");
            else if(e.getErrorCode() == MYSQL_COLUMN_DOES_NOT_EXIST)
                System.out.println("The column you attempted to insert data into does not exist. You may have formatted your values improperly.");
            else
                e.printStackTrace();
        }

        values = "";


    }

}






