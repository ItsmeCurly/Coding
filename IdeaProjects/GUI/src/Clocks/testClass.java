package Clocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testClass {
    public static void main(String[] args) {
        Date date = null;
        String dateStart = "10/27/2017 03:36:21";
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        try {
            date = format.parse(dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long startTime = date.getTime();
        System.out.println(format.get2DigitYearStart().toString());
    }
}
