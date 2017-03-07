package application;

/**
 * Created by Matthew on 3/3/2017.
 */
public class Patient {

    private String FName;
    private String LName;
    private String Address;
    private String BDate;
    private String Phone_Number;
    private String PNumber;
    private char sex;
    private int danger_lvl;


    public Patient(String fName, String lName, String addr, String bDate, String phone_Number, String pNumber, char gen, int danger)
    {
        this.FName = fName;
        this.LName = lName;
        this.Address = addr;
        this.BDate = bDate;
        this.danger_lvl = danger;
        this.Phone_Number = phone_Number;
        this.PNumber = pNumber;
        this.sex = gen;
    }

    //basic patient constructor
    public Patient(String fNameIn, String lNameIn)
    {
        FName = fNameIn;
        LName = lNameIn;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setBDate(String BDate) {
        this.BDate = BDate;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public void setPNumber(String PNumber) {
        this.PNumber = PNumber;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setDanger_lvl(int danger_lvl) {
        this.danger_lvl = danger_lvl;
    }

    public String getFName() {

        return FName;
    }

    public String getLName() {
        return LName;
    }

    public String getAddress() {
        return Address;
    }

    public String getBDate() {
        return BDate;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public String getPNumber() {
        return PNumber;
    }

    public char getSex() {
        return sex;
    }

    public int getDanger_lvl() {
        return danger_lvl;
    }
}
