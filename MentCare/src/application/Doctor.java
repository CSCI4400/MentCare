package application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 3/3/2017.
 *
 */
public class Doctor {
    private String doctorFName,doctorLName,fullName,doctorSpecialty;

    List<Patient> doctorPatientList = new ArrayList<Patient>();

    Doctor (String fname, String lname , String specialty)
    {
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
    }





}
