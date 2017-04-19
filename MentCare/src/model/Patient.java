package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Platform;

public class Patient{
	private static boolean update = false; //Used for multithreading
	private int patientnum=0;
	private String firstname = "Loading";
	private String lastname = "Loading";
	private String address = "Loading";
	private String gender = "";
	private String phonenumber = "";
	private LocalDate birthdate = LocalDate.now();
	private String diagnosis = "";
	private String ssn = "";
	private LocalDate lastvisit = LocalDate.now();
	private int PatientHistoryId = 0;

	public Patient() {

	}


	public void updateRecord(String fname, String lname, LocalDate birthday, String addr, String gendr, String phonenum, String social, LocalDate lastappt, String diag, int patientid) {

		firstname = fname; lastname = lname; birthdate=birthday;  address=addr; gender=gendr; phonenumber=phonenum; ssn=social; lastvisit=lastappt; diagnosis=diag; patientnum = patientid;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setPatientnum(int patientnum) {
		this.patientnum=patientnum;
	}
	public int getPatientnum() {
		return patientnum;
	}
	public void setFirstname(String firstname){
		this.firstname=firstname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setLastname(String lastname) {
		this.lastname=lastname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	public String getAddress() {
		return address;
	}
	public void setGender(String gender) {
		this.gender=gender;
	}
	public String getGender() {
		return gender;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate=birthdate;
	}
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis=diagnosis;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setPhoneNumber(String phonenumber) {
		this.phonenumber=phonenumber;
	}
	public String getPhoneNumber() {
		return phonenumber;
	}
	public void setLastVisit(LocalDate lastvisit) {
		this.lastvisit=lastvisit;
	}
	public LocalDate getLastVisit() {
		return lastvisit;
	}
	public void setSsn(String ssn) {
		this.ssn=ssn;
	}
	public String getSsn() {
		return ssn;
	}

	public void setPatientHistoryId(int PatientHistoryId) {
		this.PatientHistoryId = PatientHistoryId;
	}
	public int getPatientHistoryId() {
		return PatientHistoryId;
	}



}
