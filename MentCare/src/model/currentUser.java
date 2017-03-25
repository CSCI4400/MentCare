//Created by Anna 3/25/2017


package model;

public class currentUser {
	
	String name;
	String role;
	String ID;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	@Override
	public String toString() {
		return "newUser name=" + name + ", role=" + role + ", ID=" + ID;
	}
	
	
	
	
	

}
