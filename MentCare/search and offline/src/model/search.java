package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class search {
private final StringProperty Fname;
private final StringProperty Lname;
private final StringProperty Dob;
private final StringProperty Address;
private final StringProperty Sex;
private final StringProperty Phone;
private final StringProperty Dl;
private final StringProperty Pn;
private final StringProperty Lu;



public search(String Fname, String Lname, String Dob, String Address, String Sex, String Phone, String Dl, String Pn, String Lu){
	this.Fname = new SimpleStringProperty(Fname);
	this.Lname = new SimpleStringProperty(Lname);
	this.Dob = new SimpleStringProperty(Dob);
	this.Address = new SimpleStringProperty(Address);
	this.Sex = new SimpleStringProperty(Sex);
	this.Phone = new SimpleStringProperty(Phone);
	this.Dl = new SimpleStringProperty(Dl);
	this.Pn = new SimpleStringProperty(Pn);
	this.Lu = new SimpleStringProperty(Lu);
	
	
	
}
public String getFname(){
	return Fname.get();
	
}
public String getLname(){
	return Lname.get();
	
}
public String getDob(){
	return Dob.get();
	
}
public String getAddress(){
	return Address.get();
	
}
public String getSex(){
	return Sex.get();
	
}
public String getPhone(){
	return Phone.get();
	
}
public String getDl(){
	return Dl.get();
	
}
public String getPn(){
	return Pn.get();
	
}
public String getLu(){
	return Lu.get();
}

public void setFname(String value){
	Fname.set(value);
}
public void setLname(String value){
	Lname.set(value);
}
public void setDob(String value){
	Dob.set(value);
}
public void setAddress(String value){
	Address.set(value);
}
public void setSex(String value){
	Sex.set(value);
}
public void setPhone(String value){
	Phone.set(value);
}
public void setDl(String value){
	Dl.set(value);
}
public void setPn(String value){
	Pn.set(value);
}
public void setLu(String value){
	Lu.set(value);
}

//Property values
public StringProperty FnameProperty(){
	return Fname;
}
public StringProperty LnameProperty(){
	return Lname;
	}


public StringProperty getDobProperty() {
	return Dob;
}
public StringProperty getAddressProperty() {
	return Address;
}
public StringProperty getSexProperty() {
	return Sex;
}
public StringProperty getPhoneProperty() {
	return Phone;
}
public StringProperty getDlProperty() {
	return Dl;
}
public StringProperty getPnProperty() {
	return Pn;
}
public StringProperty getLuProperty() {
	return Lu;
}
}
