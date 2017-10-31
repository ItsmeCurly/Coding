package Clocks;

import java.util.Calendar;

/**
 * The type Date and time.
 */
public class DateAndTime {

    /**
     * The Offset.
     */
    static final int OFFSET = 14400000;
    /**
     * The Millisinday.
     */
    static final int MILLISINDAY = 86400000;

    private static Calendar calendar;

    /**
     * Instantiates a new Date and time.
     */
    public DateAndTime() {
        calendar = Calendar.getInstance();
    }

    /**
     * Update time.
     *
     * @param spacing the spacing
     */
    public synchronized void updateTime(long spacing) {
        calendar.setTimeInMillis(calendar.getTimeInMillis() + spacing);
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public long getTime() {
        return calendar.getTimeInMillis();
    }

    /**
     * Add to time.
     *
     * @param newTime the new time
     */
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

    /**
     * Gets time of day.
     *
     * @return the time of day
     */
    public int getTimeOfDay() {
        return (int) ((getTime() - DateAndTime.OFFSET) % DateAndTime.MILLISINDAY);
    }

    /**
     * Hour double.
     *
     * @return the hours in the day
     */
    public double hour() {
        return getTimeOfDay() / 3600000.0;
    }

    /**
     * Gets date string.
     *
     * @return the date string
     */
    public String getDateString() {
        return calendar.getTime().toString();
    }

    /**
     * Minute double.
     *
     * @return the minutes in the day
     */
    public double minute() {
        return (getTimeOfDay() / 60000.0) % 60;
    }

    /**
     * Second double.
     *
     * @return the seconds in the day
     */
    public double second() {
        return (getTimeOfDay() / 1000.0) % 60;
    }

    /**
     * Reset time to normal.
     */
    public void resetTimeToNormal() {
        calendar = Calendar.getInstance();
    }

    @Override
    public String toString() {
        return getDateString();
    }
}
