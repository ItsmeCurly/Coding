package Clocks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DigitalClock extends AbstractClock implements ActionListener {
    public DigitalClock() {
        super.time = new DateAndTime();
    }

    @Override
    public void displayTime() {

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
