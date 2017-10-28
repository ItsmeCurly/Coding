package Clocks;

import java.util.Calendar;
import java.util.Date;

public class DateAndTime {
    private static long startTime;

    private static Calendar calendar;
    private static Date date;

    public DateAndTime() {
        calendar = Calendar.getInstance();
        date = calendar.getTime();
        startTime = calendar.getTime().getTime();
    }

    public static long getStartTime() {
        return startTime;
    }

    public void updateTime(int spacing) {
        date.setTime(date.getTime() + spacing);
        calendar.setTime(date);
    }

    public long getTime() {
        return date.getTime();
    }

    public void setNewTime(int newTime) {
        updateTime(newTime);
    }

    public String getDateString() {
        return calendar. ();
    }
}
