package Clocks;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockGUI extends JPanel implements ActionListener {
    private static final int UPDATESPERSECOND = 40;
    private static final int DELAY = 1000 / UPDATESPERSECOND;

    private AnalogClock anClock;
    private DigitalClock diClock;
    private SliderBasedClock sbClock;

    public ClockGUI() {
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        setLayout(new BorderLayout());

        Border border = BorderFactory.createLineBorder(Color.black);

        Timer timer = new Timer(DELAY, this);
        timer.start();
        DateAndTime d_t = new DateAndTime();

        anClock = new AnalogClock(d_t);
        diClock = new DigitalClock(d_t);
        sbClock = new SliderBasedClock(d_t);

        anClock.setBorder(border);
        diClock.setBorder(border);
        sbClock.setBorder(border);

        add(sbClock, "North");
        add(diClock, "Center");
        add(anClock, "South");
    }

    public void actionPerformed(ActionEvent e) {
        sbClock.updateTime(DELAY);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        anClock.displayTime();
        diClock.displayTime();
        sbClock.displayTime();
    }
}
