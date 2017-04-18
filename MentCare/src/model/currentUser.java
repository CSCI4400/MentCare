//Created by Anna 3/25/2017
//modified by Anna 3/28/17 at 10:16pm, 1 updates 
/**
 * Currently logged in user info
 */


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
	
	//---Begin Anna 1-----------------------------------------------------------------------------------------------
		@Override
		public String toString() {
			return "currentUser [name=" + name + ", role=" + role + ", ID=" + ID + "]";
		}
	//---End Anna 1-----------------------------------------------------------------------------------------------
	
	
	
	
	

}
