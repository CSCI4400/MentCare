package application;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 3/3/2017.
 */
public class BusinessManager {
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

    public int countPatients() // can be expanded for further use for day week year once db is setup
    {
        int count = 0; //initializes to 0

        for (Doctor i: doctorList) //runs through every doctor in doctor list
        {
           count +=  patientList.size(); // adds number of patients to to running count.
        }
        return count; // returns total count of patients
    }

}
