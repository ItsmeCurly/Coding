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

    public synchronized void updateTime(long spacing) {
        calendar.setTimeInMillis(calendar.getTimeInMillis() + spacing);
    }

    public long getTime() {
        return calendar.getTimeInMillis();
    }

    public synchronized void addToTime(long newTime) {
        updateTime(newTime);
    }

    public String getDateString() {
        return calendar.getTime().toString();
    }
}
