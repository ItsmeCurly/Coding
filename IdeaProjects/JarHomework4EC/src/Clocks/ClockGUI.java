package Clocks;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Clock gui.
 */
public class ClockGUI extends JPanel implements ActionListener {
    private static final int UPDATESPERSECOND = 40;
    private static final int DELAY = 1000 / UPDATESPERSECOND;

    private AnalogClock anClock;
    private DigitalClock diClock;
    private SliderBasedClock sbClock;

    /**
     * Instantiates a new Clock gui.
     */
    public ClockGUI() {
        createAndShowGUI();
    }

    /**
     * Create and show gui.
     */
    public void createAndShowGUI() {
        setLayout(new BorderLayout());

        Border border = BorderFactory.createLineBorder(Color.black);
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());

        Timer timer = new Timer(DELAY, this);
        timer.start();
        DateAndTime d_t = new DateAndTime();

        anClock = new AnalogClock(d_t);
        diClock = new DigitalClock(d_t);
        sbClock = new SliderBasedClock(d_t);

        anClock.setBorder(border);
        diClock.setBorder(border);
        sbClock.setBorder(border);

        jp.add(sbClock, "North");
        jp.add(diClock, "South");

        add(jp, "West");
        add(anClock, "East");
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        sbClock.updateTime(DELAY);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        anClock.displayTime();
        diClock.displayTime();
        sbClock.displayTime();
    }
}
