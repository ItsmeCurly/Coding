package Clocks;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DigitalClock extends AbstractClock {
    private int todayTimeInMillis;
    private JTextArea jta;
    private NumberFormat fmt;
    private Font font;

    public DigitalClock(DateAndTime time) {
        super.time = time;
        createAndShowGUI();
        createFont();
        fmt = new DecimalFormat("00");
        todayTimeInMillis = time.getTimeOfDay();
    }

    private void createAndShowGUI() {
        setBackground(new Color(238, 238, 238));
        jta = new JTextArea();
        jta.setBackground(new Color(238, 238, 238));
        jta.setForeground(Color.RED);
        jta.setPreferredSize(new Dimension(640, 200));

        add(jta);
    }

    private void createFont() {
        jta.setFont(new Font("Monospaced", Font.BOLD + Font.ITALIC, 128));
    }

    @Override
    public void updateTime(int spacing) {
        super.updateTime(spacing);
        todayTimeInMillis = (todayTimeInMillis + spacing) % DateAndTime.MILLISINDAY;
    }

    @Override
    public void displayTime() {
        jta.setText(getTimeString());
    }

    private String getTimeString() {
        return fmt.format((time.hour() % 12 == 0) ? 12 : time.hour() % 12) + ":" + fmt.format(time.minute()) + ((time.hour() > 12) ? " PM" : " AM");
    }
}
