package application;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Matthew on 3/3/2017.
 */
public class BusinessManager {

    public static final int MYSQL_DUPLICATE_PK = 1062;
    public static final int MYSQL_TABLE_DOES_NOT_EXIST = 1146;
    public static final int MYSQL_COLUMN_DOES_NOT_EXIST = 1054;

    Connection con = null;
    Statement stmt = null;
    String query;
    String table;
    Scanner scan = new Scanner(System.in);
    String values = "";





    List<Doctor> doctorList = new ArrayList<Doctor>();
    List<Patient> patientList = new ArrayList<Patient>();
    String managerName;

    void addDoctor (Doctor d)
    {
        doctorList.add(d);
    }

    void addPatient(Patient p)
    {
        patientList.add(p);
    }

    BusinessManager(String name)
    {
        this.managerName = name;
    }

    public List <Doctor> showDoctors()
    {
        return doctorList;
    }

    public List<Patient> showPatients()
    {
        return patientList;
    }

    public void countPatients() // can be expanded for further use for day week year once db is setup
    {

        try {
            Connection conn = DBConfig.getConnection();

            stmt = conn.createStatement();

            table = "Personal_Info";
            String column = "PNumber";

            query = "SELECT COUNT" + column + "FROM"+table;

            //This can still be expanded as we get different times established.

           /*
            int count = 0; //initializes to 0

            for (Doctor i : doctorList) //runs through every doctor in doctor list
            {
                count += patientList.size(); // adds number of patients to to running count.
            }
            return count; // returns total count of patients
            */
        }
        catch (SQLException e)
        {
            //Error codes are used to determine the cause of the failure and the user is notified
                e.printStackTrace();
        }
    }


}
