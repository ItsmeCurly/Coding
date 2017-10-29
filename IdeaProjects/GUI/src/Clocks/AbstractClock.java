package Clocks;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractClock extends JPanel implements ActionListener {
    DateAndTime time;

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public void updateTime(int spacing) {
        time.addToTime(spacing);
    }

    public abstract void displayTime();

    public DateAndTime getTime() {
        return time;
    }
}
