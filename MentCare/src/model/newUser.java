//Created by Anna 3/25/2017


package model;

public class newUser {
	
	String name;
	String role;
	String ID;
	String password;
	String createdBy;
	String createDate;
	
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "newUser [name=" + name + ", role=" + role + ", ID=" + ID + ", password=" + password + ", createdBy="
				+ createdBy + ", createDate=" + createDate + "]";
	}
	
	
	
	
	

}
