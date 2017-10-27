package Clocks;

import java.util.Date;

public class DateAndTime {
    private static long startTime;

    private Date date;

    public DateAndTime() {
        date = new Date();
        startTime = date.getTime();
    }

    public static long getStartTime() {
        return startTime;
    }

    public void updateTime(int spacing) {
        date.setTime(date.getTime() + spacing);
    }

    public long getTime() {
        return date.getTime();
    }

    public void setNewTime(int newTime) {
        updateTime(newTime);
    }

    public String getDateString() {
        return date.toString();
    }
}
