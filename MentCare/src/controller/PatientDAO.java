package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Platform;
import model.InitialDBConnection;
import model.Patient;
import view.doctorview;
import view.viewMenu;

public class PatientDAO {
	public static Patient getPatientInfo(int patientnum, int accesslevel) {
		Patient a = new Patient();
		String selectPinfoStmt = "SELECT PNumber, LName, FName, BDate, Address, Sex, Phone_Number FROM mentcare.Personal_Info WHERE ? = mentcare.Personal_Info.PNumber";
		String selectMinfostmt = "SELECT * FROM mentcare.Medical_Info WHERE ? = mentcare.Medical_Info.PNum";
		int pnum = -1; //The variables passed to the 'patientrcords' method are initiated to blank values
			
		//Test code
		String abc = "hello world";
			Thread t = new Thread(abc);
			t.run();
		
			try {
				PreparedStatement pstmt = viewMenu.con.prepareStatement(selectPinfoStmt);
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
				}
				
				pstmt = viewMenu.con.prepareStatement(selectMinfostmt);
				pstmt.setInt(1, patientnum);
				rs = pstmt.executeQuery();
				
				while(rs.next()){ //Gets the information from the "Medical Info" table
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
					doctorview.patientrecords(a);
				}
			});
			return a;
	}
	public static void updatePatientInfo(Patient a) {
		System.out.println("Record updater starting");
		String updatePersonalInfo = "UPDATE mentcare.Personal_Info SET Fname = ? , Lname = ?, BDate = ?, Address = ?, Sex = ?, Phone_Number = ? WHERE PNumber = ? ";
		String updateMedicalInfo = "UPDATE mentcare.Medical_Info SET Ssn = ?, Last_Visit = ? WHERE PNum = ?";
		String updateDiagnosis = "UPDATE mentcare.Medical_Info SET Diagnosis = ? WHERE PNum = ?";
		String insertIntoDiagHistory = "INSERT INTO mentcare.Diagnosis_History VALUES ( ? , ?, ?, ? )";
		String selectCurrentDiag = "SELECT mentcare.Medical_Info.Diagnosis FROM mentcare.Medical_Info WHERE ? = PNum";
		
		try {
			Connection Con;
			PreparedStatement pstmt;
			
			Con = DriverManager.getConnection("jdbc:mysql://164.132.49.5:3306", "mentcare", InitialDBConnection.DBPASSWORD);
			pstmt = Con.prepareStatement(updatePersonalInfo);//Updates the personal info and medical info tables, excluding diagnosis
			pstmt.setString(1, a.getFirstname());
			pstmt.setString(2,  a.getLastname());
			pstmt.setObject(3, a.getBirthdate());
			pstmt.setString(4, a.getAddress());
			pstmt.setString(5,  a.getGender());
			pstmt.setString(6, a.getPhoneNumber());
			pstmt.setInt(7, a.getPatientnum());
			pstmt.executeUpdate();
			pstmt = Con.prepareStatement(updateMedicalInfo);
			pstmt.setString(1, a.getSsn());
			pstmt.setObject(2, a.getLastVisit());
			pstmt.setInt(3, a.getPatientnum());
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

