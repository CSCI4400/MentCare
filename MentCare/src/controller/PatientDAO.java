package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import application.MainFXApp;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.DBConnection;
import model.Patient;


public class PatientDAO {
	
	static Boolean noPatientFound = false;

	public static Patient getPatientInfo(int patientnum, Stage window) {
		Patient a = new Patient();
		//Query for getting the current patient info
		String selectPinfoStmtPnum = "SELECT PNumber, LName, FName, BDate, Address, Sex, Phone_Number, Danger_lvl, Diagnosis, Ssn, Last_Visit FROM mentcare2.Personal_Info WHERE ? = mentcare2.Personal_Info.PNumber";


			try {
				//queries the database for the current patient info
				PreparedStatement pstmt = MainFXApp.con.prepareStatement(selectPinfoStmtPnum);
				pstmt.setInt(1, patientnum);
				ResultSet rs = pstmt.executeQuery(); //ResultSet contains the results of the query
				if(!rs.isBeforeFirst()){
					//This means that there is no patient with the patient ID number entered
					System.out.println("No patient found");
					noPatientFound = true;
				}
				else{
					noPatientFound = false;
					while(rs.next()){ //Gets the information from the "Personal Info" table
						a.setPatientnum(rs.getInt("PNumber"));
						a.setFirstname(rs.getString("Fname"));
						a.setLastname(rs.getString("Lname"));
						a.setAddress(rs.getString("Address"));
						a.setGender(rs.getString("Sex"));
						a.setPhoneNumber(rs.getString("Phone_Number"));
						a.setBirthdate(LocalDate.parse((rs.getDate("BDate")).toString()));
						a.setDiagnosis(rs.getString("Diagnosis"));
						//a.setLastVisit(LocalDate.parse((rs.getDate("Last_Visit")).toString()));
						a.setSsn(rs.getString("Ssn"));
						}
				}
				pstmt.close();
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();//
			}
			Platform.runLater(new Runnable() {
				public void run() {
					if(noPatientFound){
						PatientRecordsController.NoPatientFound(a, window);
					}
					else{
						if(loginController.loggedOnUser.equals("Doctor")){
							//Goes to the patient records screen for a Doctor
							PatientRecordsController.ViewPatientRecordsDoc(a, window);
						}
						if(loginController.loggedOnUser.equals("Receptionist")){
							//Goes to the patient records screen for a receptionist
							PatientRecordsController.ViewPatientRecordsRecep(a, window);
						}
						else{
							//fall back case for testing. Remove once login system is implemented
							PatientRecordsController.ViewPatientRecordsDoc(a, window);
						}
					}

				}
			});
			return a;
	}

	public static void updatePatientInfo(Patient a, int DiagnosisCode) {
		//DiagnosisCode indicates whether diagnosis is permanent or temporary
		System.out.println("Record updater starting");
		//Query for updating patient info in the database
		String updatePersonalInfo = "UPDATE mentcare2.Personal_Info SET Fname = ? , Lname = ?, BDate = ?, Address = ?, Sex = ?, Phone_Number = ?, Ssn = ?, Last_Visit = ? WHERE PNumber = ? ";
		//Query for updating diagnosis in the database. Needs to be a separate query to handle diagnosis history
		String updateDiagnosis= "UPDATE mentcare2.Personal_Info SET Diagnosis = ? WHERE PNumber = ?";
		//Query for adding a new entry to the diagnosis history
		String insertIntoDiagHistory = "INSERT INTO mentcare2.Diagnosis_History VALUES ( ?, ?, ?, ?, ? )";
		//Query for getting the current diagnosis for a patient
		String selectCurrentDiag = "SELECT mentcare2.Personal_Info.Diagnosis FROM mentcare2.Personal_Info WHERE ? = PNumber";
		//Query for checking if a patient is dead
		String checkDeath = "SELECT Dead FROM mentcare2.Personal_Info WHERE ? = PNumber";

		try {
			Connection Con;
			PreparedStatement pstmt;

			//Sets up a connection to the database
			Con = DriverManager.getConnection("jdbc:mysql://164.132.49.5:3306", "mentcare", DBConnection.DBPASSWORD);
			pstmt = Con.prepareStatement(checkDeath);
			pstmt.setInt(1, a.getPatientnum());
			ResultSet rt = pstmt.executeQuery();
			ArrayList<String> Dead = new ArrayList<String>();
			while(rt.next()){
				Dead.add(rt.getString("Dead"));
			}

			if("no".equals(Dead.get(0))){
				//Only updates if a patient is dead

				pstmt = Con.prepareStatement(updatePersonalInfo);//Updates the patient info table
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
						//Updates diagnosis history table if diagnosis has been changed
						pstmt = Con.prepareStatement(insertIntoDiagHistory);
						pstmt.setInt(1, a.getPatientnum());
						pstmt.setString(2, a.getDiagnosis());
						pstmt.setObject(3, LocalDate.now());
						pstmt.setObject(4, loginController.loggedOnUser.getName());
						pstmt.setInt(5, DiagnosisCode);
						pstmt.executeUpdate();
						pstmt= Con.prepareStatement(updateDiagnosis);
						pstmt.setString(1, a.getDiagnosis());
						pstmt.setInt(2, a.getPatientnum());
						pstmt.executeUpdate();
					}

					pstmt.close();
			}
			else{
				//Alert box. This is a fall back that confirms that an update did not occur since
				//a patient is dead. This code should not be reachable if the database is working
				//correctly because the update button will be disabled for a dead patient
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Alert");
				alert.setHeaderText("Update did not occur");
				alert.setContentText("This patient is dead. Information cannot be updated.");
				alert.showAndWait();

				pstmt.close();
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

