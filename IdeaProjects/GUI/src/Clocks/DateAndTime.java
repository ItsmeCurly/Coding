package Clocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndTime {
    static long startTime;

    private static Date date;
    private final String dateStart = "10/27/2017 03:36:21";
    private SimpleDateFormat format;

    public DateAndTime() {
        format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        try {
            date = format.parse(dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long startTime = date.getTime();
    }

    public static long getStartTime() {
        return startTime;
    }

    public void updateTime(int spacing) {
        date.setTime(getTime() + spacing);
    }

    public long getTime() {
        return date.getTime();
    }

    public void setNewTime(int newTime) {
        date.setTime(getTime() + newTime);
    }

    public String getDate() {
        return format.get2DigitYearStart().toString();
    }
}
