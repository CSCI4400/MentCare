package application;

public class PersonalInfoList {

	private String PNumber;
	private String FName;
	private String LName;
	private String BDate;
	private String Sex;
	private String Address;
	private String Phone_Number;
	private String Danger_lvl;
	
	public PersonalInfoList(String pNumber, String fName, String lName, String bDate, String sex, String address, String phone_Number, String danger_Lvl) {
		PNumber = pNumber;
		FName = fName;
		LName = lName;
		BDate = bDate;
		Sex = sex;
		Address = address;
		Phone_Number = phone_Number;
		Danger_lvl = danger_Lvl;
	}

	public String getPNumber() {
		return PNumber;
	}

	public void setPNumber(String pNumber) {
		PNumber = pNumber;
	}

	public String getFName() {
		return FName;
	}

	public void setFName(String fName) {
		FName = fName;
	}

	public String getLName() {
		return LName;
	}

	public void setLName(String lName) {
		LName = lName;
	}

	public String getBDate() {
		return BDate;
	}

	public void setBDate(String bDate) {
		BDate = bDate;
	}
	
	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getPhone_Number() {
		return Phone_Number;
	}

	public void setPhone_Number(String phone_Number) {
		Phone_Number = phone_Number;
	}
	
	public String getDanger_lvl() {
		return Danger_lvl;
	}

	public void setDanger_lvl(String danger_lvl) {
		Danger_lvl = danger_lvl;
	}
	
}