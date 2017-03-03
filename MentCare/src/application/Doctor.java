package application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 3/3/2017.
 *
 */
public class Doctor {
    private String doctorFName,doctorLName,fullName;
    List<Patient> doctorPatientList = new ArrayList<patient>();

    Doctor (String fname, String lname )
    {
        this.doctorFName = fname;
        this.doctorLName = lname;
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
    {
        doctorPatientList.add(p);
    }


}
