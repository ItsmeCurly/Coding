import java.util.ArrayList;

public class Student {
    private String ID;
    private String lastName;
    private String firstName;
    private int mtScore;
    private int fnScore;

    public Student() {
        ID = "-1";
        lastName = "";
        firstName = "";
        mtScore = 0;
        fnScore = 0;
    }

    public Student(String ID, String lastName, String firstName, int mtScore, int fnScore) {
        this.ID = ID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.mtScore = mtScore;
        this.fnScore = fnScore;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getMtScore() {
        return mtScore;
    }

    public void setMtScore(int mtScore) {
        this.mtScore = mtScore;
    }

    public int getFnScore() {
        return fnScore;
    }

    public void setFnScore(int fnScore) {
        this.fnScore = fnScore;
    }

    public String getCode() {
        return firstName.substring(0,1) + lastName.substring(0, 1) + ID.substring(ID.length() - 2, ID.length());
    }
}
