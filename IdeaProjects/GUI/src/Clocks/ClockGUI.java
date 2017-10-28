package Clocks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockGUI extends JPanel implements ActionListener {
    private static final int DELAY = 1000 / 40;

    private AnalogClock anClock;
    private DigitalClock diClock;
    private SliderBasedClock sbClock;

    private Timer timer;

    public ClockGUI() {
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        setLayout(new BorderLayout());

        timer = new Timer(DELAY, this);
        timer.start();

        anClock = new AnalogClock();
        diClock = new DigitalClock();
        sbClock = new SliderBasedClock();

        add(anClock, "West");
        add(diClock, "Center");
        add(sbClock, "East");
    }

    public void actionPerformed(ActionEvent e) {
        anClock.updateTime(DELAY);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        anClock.displayTime();
        diClock.displayTime();
        sbClock.displayTime();
    }
}
