package application;

/**
 * Created by sad2e_000 on 3/4/2017.
 */

public class Nurse {

    private String fName, lName, ID;

    public Nurse(String fName, String lName, String ID) {
        this.fName = fName;
        this.lName = lName;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public String getfName() {

        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
}
