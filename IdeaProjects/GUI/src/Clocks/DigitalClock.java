package Clocks;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DigitalClock extends AbstractClock implements ActionListener {
    private int todayTimeInMillis;
    private JTextPane jtp;
    private NumberFormat fmt;

    public DigitalClock(DateAndTime time) {
        super.time = time;
        fmt = new DecimalFormat("00");
        jtp = new JTextPane();
        todayTimeInMillis = time.getTimeOfDay();
    }

    @Override
    public void updateTime(int spacing) {
        super.updateTime(spacing);
        todayTimeInMillis = (todayTimeInMillis + spacing) % DateAndTime.MILLISINDAY;
    }

    @Override
    public void displayTime() {
        System.out.println(getTimeString());
        StyledDocument doc = jtp.getStyledDocument();

        Style style = jtp.addStyle("ConsoleStyle1", null);

        StyleConstants.setForeground(style, Color.GREEN);

        try {
            doc.insertString(doc.getLength(), getTimeString(), style);
        } catch (BadLocationException err) {
            err.printStackTrace();
        }
    }

    private String getTimeString() {

        return fmt.format(time.hour()) + ":" + fmt.format(time.minute());
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
