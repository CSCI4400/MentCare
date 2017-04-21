//Team 5 Leonardo Mazuran

package model;



import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class delete {
private final StringProperty Fname;
private final StringProperty Lname;





public delete(String Fname, String Lname){
	this.Fname = new SimpleStringProperty(Fname);
	this.Lname = new SimpleStringProperty(Lname);
	
	
	
	
}
public String getFname(){
	return Fname.get();
	
}
public String getLname(){
	return Lname.get();
	
}


public void setFname(String value){
	Fname.set(value);
}
public void setLname(String value){
	Lname.set(value);
}


//Property values
public StringProperty FnameProperty(){
	return Fname;
}
public StringProperty LnameProperty(){
	return Lname;
	}



}
