package Clocks;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Abstract clock.
 */
public abstract class AbstractClock extends JPanel implements ActionListener {
    /**
     * The Time.
     */
    DateAndTime time;

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    /**
     * Update time.
     *
     * @param spacing the spacing
     */
    public void updateTime(int spacing) {
        time.addToTime(spacing);
    }

    /**
     * Display time.
     */
    public abstract void displayTime();

    /**
     * Gets time.
     *
     * @return the time
     */
    public DateAndTime getTime() {
        return time;
    }
}
