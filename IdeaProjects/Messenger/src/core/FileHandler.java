package core;

import org.json.simple.JSONArray;
import user.Userdata;

import java.io.FileWriter;
import java.io.IOException;

public class FileHandler implements Data {
    private static JSONArray userList;
    private FileWriter file;

    public FileHandler() {
        createFileIfNew();
        userList = new JSONArray();
    }

    public static void createNewUser(Userdata data) {
        userList.add(data.toString());

    }

    private void createFileIfNew() {
        try {
            FileWriter file = new FileWriter(userFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
