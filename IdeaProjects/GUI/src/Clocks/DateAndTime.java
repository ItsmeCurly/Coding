package Clocks;

import java.util.Calendar;

public class DateAndTime {

    static final int OFFSET = 14400000;
    static final int MILLISINDAY = 86400000;

    private static Calendar calendar;

    public DateAndTime() {
        calendar = Calendar.getInstance();
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

    /**
     * Gets calendar.
     *
     * @return Value of calendar.
     */
    public static Calendar getCalendar() {
        return calendar;
    }

    /**
     * Sets new calendar.
     *
     * @param c New value of calendar.
     */
    public static void setCalendar(Calendar c) {
        calendar = c;
    }

    public int getTimeOfDay() {
        return (int) ((getTime() - DateAndTime.OFFSET) % DateAndTime.MILLISINDAY);
    }

    public double hour() {
        return getTimeOfDay() / 3600000.0;
    }

    public String getDateString() {
        return calendar.getTime().toString();
    }

    public double minute() {
        return (getTimeOfDay() / 60000.0) % 60;
    }

    public double second() {
        return (getTimeOfDay() / 1000.0) % 36000;
    }

    public void resetTimeToNormal() {
        calendar = Calendar.getInstance();
    }

    @Override
    public String toString() {
        return getDateString();
    }
}
