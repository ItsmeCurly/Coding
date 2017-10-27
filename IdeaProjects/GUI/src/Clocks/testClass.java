package Clocks;

import java.text.SimpleDateFormat;
import java.util.Date;

public class testClass {
    public static void main(String[] args) {
        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        System.out.println(date.toString());
    }
}
