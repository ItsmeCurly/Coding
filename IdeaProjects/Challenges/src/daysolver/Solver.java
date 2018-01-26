package daysolver;

import java.io.*;
import java.util.*;

public class Solver {
    private String [] dateMap;
    private ArrayList<String> days;

    Solver(String fileName) {
        days = new ArrayList<>();
        initMaps();
        solveDays(new File(fileName));
        System.out.println(this);
    }

    private void initMaps() {
        dateMap = new String []{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    }

    private void solveDays(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert br != null;
        String str;
        try {
            while ((str = br.readLine()) != null)
                try {
                    days.add(dateMap[(getDay(str.split("\\s")))]);
                } catch (IncorrectDateInputException e) {
                    e.printStackTrace();
                }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private int getDay(String[] results) throws IncorrectDateInputException {
        if(results.length > 3) throw new IncorrectDateInputException();
        int [] ymd = new int [3];

        for(int i = 0; i < 3; i++) ymd[i] = Integer.parseInt(results[i]);
        if(ymd[1] < 3) {
            ymd[0] -= 1;
            ymd[1] += 12;
        }
        return (int)(ymd[2]+Math.floor(13*(ymd[1]+1)/5)+ ymd[0] + Math.floor(ymd[0]/4) - Math.floor(ymd[0]/100) + Math.floor(ymd[0]/400))%7;
    }

    @Override
    public String toString() {
        return "Solver: " +
                "days = " + days;
    }
}
