package user;

import core.Data;

import java.util.ArrayList;

public class Userdata implements Data {
    private String userName;
    private String password;

    private boolean online;

    private int userID;

    private ArrayList<Conversation> conversations;

    public Userdata() {
        this(null, null);
    }

    public Userdata(String userName, String password) {
        this.userName = userName;
        this.password = password;
        encrypt(password);
    }

    private void encrypt(String string) {

    }

    private boolean userDataExists(int id) {
        return false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
