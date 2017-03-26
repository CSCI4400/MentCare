package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Platform;
import model.DBConnection;
import model.Patient;

public class PatientDAO {
	public static Patient getPatientInfo(int patientnum, int accesslevel) {
		Patient a = new Patient();
		String selectPinfoStmt = "SELECT PNumber, LName, FName, BDate, Address, Sex, Phone_Number, Danger_lvl, Diagnosis, Ssn, Last_Visit FROM mentcare.Patient_Info WHERE ? = mentcare.Patient_Info.PNumber";
		int pnum = -1; //The variables passed to the 'patientrcords' method are initiated to blank values
			
		//Test code
		String abc = "hello world";
			Thread t = new Thread(abc);
			t.run();
		
			try {
				PreparedStatement pstmt = ViewMenuController.con.prepareStatement(selectPinfoStmt);
				pstmt.setInt(1, patientnum);
				ResultSet rs = pstmt.executeQuery(); //ResultSet contains the results of the query
				while(rs.next()){ //Gets the information from the "Personal Info" table
					a.setPatientnum(rs.getInt("PNumber"));
					a.setFirstname(rs.getString("Fname"));
					a.setLastname(rs.getString("Lname"));
					a.setAddress(rs.getString("Address"));
					a.setGender(rs.getString("Sex"));
					a.setPhoneNumber(rs.getString("Phone_Number"));
					a.setBirthdate(LocalDate.parse((rs.getDate("BDate")).toString()));
					a.setDiagnosis(rs.getString("Diagnosis"));
					a.setLastVisit(LocalDate.parse((rs.getDate("Last_Visit")).toString()));
					a.setSsn(rs.getString("Ssn"));
				}
				pstmt.close();
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();//
			}
			Platform.runLater(new Runnable() {
				public void run() {
					DoctorViewController.patientrecords(a);
				}
			});
			return a;
	}
	public static void updatePatientInfo(Patient a) {
		System.out.println("Record updater starting");
		String updatePersonalInfo = "UPDATE mentcare.Patient_Info SET Fname = ? , Lname = ?, BDate = ?, Address = ?, Sex = ?, Phone_Number = ?, Ssn = ?, Last_Visit = ? WHERE PNumber = ? ";
		String updateDiagnosis = "UPDATE mentcare.Patient_Info SET Diagnosis = ? WHERE PNumber = ?";
		String insertIntoDiagHistory = "INSERT INTO mentcare.Diagnosis_History VALUES ( ? , ?, ?, ? )";
		String selectCurrentDiag = "SELECT mentcare.Patient_Info.Diagnosis FROM mentcare.Patient_Info WHERE ? = PNumber";
		
		try {
			Connection Con;
			PreparedStatement pstmt;
			
			Con = DriverManager.getConnection("jdbc:mysql://164.132.49.5:3306", "mentcare", DBConnection.DBPASSWORD);
			pstmt = Con.prepareStatement(updatePersonalInfo);//Updates the personal info and medical info tables, excluding diagnosis
			pstmt.setString(1, a.getFirstname());
			pstmt.setString(2,  a.getLastname());
			pstmt.setObject(3, a.getBirthdate());
			pstmt.setString(4, a.getAddress());
			pstmt.setString(5,  a.getGender());
			pstmt.setString(6, a.getPhoneNumber());
			pstmt.setString(7, a.getSsn());
			pstmt.setObject(8, a.getLastVisit());
			pstmt.setInt(9, a.getPatientnum());
			pstmt.executeUpdate();
			
			pstmt = Con.prepareStatement(selectCurrentDiag);
			pstmt.setInt(1, a.getPatientnum());
			ResultSet rs = pstmt.executeQuery();
			ArrayList<String> Diagnoses = new ArrayList<String>();
			while(rs.next()){
				Diagnoses.add(rs.getString("Diagnosis"));
			}
			if(!Diagnoses.get(0).equals(a.getDiagnosis())){
				pstmt = Con.prepareStatement(insertIntoDiagHistory);
				pstmt.setInt(1, a.getPatientnum());
				pstmt.setString(2, a.getDiagnosis());
				pstmt.setObject(3, LocalDate.now());
				pstmt.setObject(4, "Current Doctor");
				pstmt.executeUpdate();
				pstmt= Con.prepareStatement(updateDiagnosis);
				pstmt.setString(1, a.getDiagnosis());
				pstmt.setInt(2, a.getPatientnum());
				pstmt.executeUpdate();
			}
			
		pstmt.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

