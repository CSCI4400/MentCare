package mentcare;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Platform;

public class Patient implements Runnable {
	public final Runnable recordupdater;
	private static boolean update = false; //Used for multithreading
	private static int patientnum=0;
	private static String firstname = "Loading";
	private static String lastname = "Loading";
	private static String address = "Loading";
	private static String gender = "";
	private static String phonenumber = "";
	private static LocalDate birthdate = LocalDate.now();
	private static String diagnosis = "";
	private static String ssn = "";
	private static LocalDate lastvisit = LocalDate.now();
	
	public Patient(int patientnum) {
		this.patientnum = patientnum;
		recordupdater = new Runnable() {
			public void run() {
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
					pstmt.setString(1, firstname);
					pstmt.setString(2,  lastname);
					pstmt.setObject(3, birthdate);
					pstmt.setString(4, address);
					pstmt.setString(5,  gender);
					pstmt.setString(6, phonenumber);
					pstmt.setInt(7, patientnum);
					pstmt.executeUpdate();
					pstmt = Con.prepareStatement(updateMedicalInfo);
					pstmt.setString(1, ssn);
					pstmt.setObject(2, lastvisit);
					pstmt.setInt(3, patientnum);
					pstmt.executeUpdate();
					
					pstmt = Con.prepareStatement(selectCurrentDiag);
					pstmt.setInt(1, patientnum);
					ResultSet rs = pstmt.executeQuery();
					ArrayList<String> Diagnoses = new ArrayList<String>();
					while(rs.next()){
						Diagnoses.add(rs.getString("Diagnosis"));
					}
					if(!Diagnoses.get(0).equals(diagnosis)){
						pstmt = Con.prepareStatement(insertIntoDiagHistory);
						pstmt.setInt(1, patientnum);
						pstmt.setString(2, diagnosis);
						pstmt.setObject(3, LocalDate.now());
						pstmt.setObject(4, "Current Doctor");
						pstmt.executeUpdate();
						pstmt= Con.prepareStatement(updateDiagnosis);
						pstmt.setString(1, diagnosis);
						pstmt.setInt(2, patientnum);
						pstmt.executeUpdate();
					}
					
					pstmt.close();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
	}
	public static void updateRecord(String fname, String lname, LocalDate birthday, String addr, String gendr, String phonenum, String social, LocalDate lastappt, String diag, int patientid) {
		firstname = fname; lastname = lname; birthdate=birthday;  address=addr; gender=gendr; phonenumber=phonenum; ssn=social; lastvisit=lastappt; diagnosis=diag; patientnum = patientid;
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
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
				pnum = rs.getInt("PNumber");
				firstname = rs.getString("Fname");
				lastname = rs.getString("Lname");
				address = rs.getString("Address");
				gender = rs.getString("Sex");
				phonenumber = rs.getString("Phone_Number");
				birthdate = LocalDate.parse((rs.getDate("BDate")).toString());
			}
			
			pstmt = viewMenu.con.prepareStatement(selectMinfostmt);
			pstmt.setInt(1, patientnum);
			rs = pstmt.executeQuery();
			
			while(rs.next()){ //Gets the information from the "Medical Info" table
				diagnosis = rs.getString("Diagnosis");
				lastvisit = LocalDate.parse((rs.getDate("Last_Visit")).toString());
				ssn = rs.getString("Ssn");
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(update);
		update=!update;		
		System.out.println("patient info2");
		System.out.println(patientnum);
		System.out.println(firstname);
		System.out.println(lastname);
		System.out.println(birthdate.toString());
		System.out.println(update);
		Platform.runLater(new Runnable() {
			public void run() {
				doctorview.patientrecords(patientnum, firstname, lastname, birthdate.toString(), address, gender, phonenumber, diagnosis, ssn, lastvisit.toString());
			}
		});
	}
	public static boolean isUpdate() {
		return update;
	}
	public static int getPatientnum() {
		return patientnum;
	}
	public static String getFirstname() {
		return firstname;
	}
	public static String getLastname() {
		return lastname;
	}
	public static String getAddress() {
		return address;
	}
	public static String getGender() {
		return gender;
	}
	public static LocalDate getBirthdate() {
		return birthdate;
	}
	public static String getDiagnosis() {
		return diagnosis;
	}
	public static String getPhoneNumber() {
		return phonenumber;
	}
	public static LocalDate getLastVisit() {
		return lastvisit;
	}
	public static String getSsn() {
		return ssn;
	}
	
}
