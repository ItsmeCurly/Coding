package Clocks;

import java.awt.*;
import java.awt.event.*;

/**
 * The type Analog clock.
 */
public class AnalogClock extends AbstractClock implements ActionListener, MouseListener, MouseMotionListener {
    private boolean secondsHandHeld, minutesHandHeld, hoursHandHeld;

    private final int CLOCKRADIUS = 250;
    /**
     * The Font.
     */
    Font font;
    /**
     * The FontMetrics.
     */
    FontMetrics fm;
    private int center_x;
    private int center_y;

    /**
     * Instantiates a new Analog clock.
     *
     * @param time the time
     */
    public AnalogClock(DateAndTime time) {
        super.time = time;
        createAndShowGUI();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void createAndShowGUI() {
        setPreferredSize(new Dimension(550, 550));

        center_x = (int) getPreferredSize().getWidth() / 2;
        center_y = (int) getPreferredSize().getHeight() / 2;

        font = new Font("Monospaced", Font.BOLD, 48);
    }

    private double getHourDeg() {
        return time.hour() * 30 - 90;
    }

    private double getMinuteDeg() {
        return time.minute() * 6 - 90;
    }

    private double getSecondDeg() {
        return time.second() * 6 - 90;
    }

//    private double getHoursWithDeg() {
//        //TODO
//        return 0.0;
//    }
//    private double getMinutesWithDeg() {
//        //TODO
//        return 0.0;
//    }
//    private double getSecondsWithDeg() {
//        //TODO
//        return 0.0;
//    }
//
//    private boolean withinRange(double toBeChecked, double actual, int variance) {
//        return ((toBeChecked <= (actual + variance)) && (toBeChecked >= (actual - variance)));
//    }
//
//    private boolean hoursHandClickedWithinRange(double clickDegOnClock) {
//        //System.out.println(getHourDeg());
//        return withinRange(clickDegOnClock, getHourDeg(), 2);
//    }
//
//    private boolean minutesHandClickedWithinRange(double clickDegOnClock) {
//        //System.out.println(getMinuteDeg());
//
//        return withinRange(clickDegOnClock, getMinuteDeg(), 2);
//    }
//
//    private boolean secondsHandClickedWithinRange(double clickDegOnClock) {
//        //System.out.println(getSecondDeg());
//        return withinRange(clickDegOnClock, getSecondDeg(), 2);
//    }

    @Override
    public void displayTime() {
        repaint();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(10));
        g2.fillOval(center_x - 10, center_y - 10, 20, 20);
        g2.drawOval(center_x - CLOCKRADIUS, center_y - CLOCKRADIUS, CLOCKRADIUS * 2, CLOCKRADIUS * 2);

        g2.setStroke(new BasicStroke(6));
        g2.drawLine(center_x, center_y,
                (int) (CLOCKRADIUS * Math.cos(Math.toRadians(getHourDeg()))) + center_x,
                (int) (CLOCKRADIUS * Math.sin(Math.toRadians(getHourDeg()))) + center_y);
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(center_x, center_y,
                (int) (CLOCKRADIUS * Math.cos(Math.toRadians(getMinuteDeg()))) + center_x,
                (int) (CLOCKRADIUS * Math.sin(Math.toRadians(getMinuteDeg()))) + center_y);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(center_x, center_y,
                (int) (CLOCKRADIUS * Math.cos(Math.toRadians(getSecondDeg()))) + center_x,
                (int) (CLOCKRADIUS * Math.sin(Math.toRadians(getSecondDeg()))) + center_y);
        g2.setStroke(new BasicStroke(2));

        g2.setFont(font);
        fm = g2.getFontMetrics();

        g2.drawString("12", center_x - fm.stringWidth("12") / 2, 60 + fm.getAscent() / 2);
        g2.drawString("3", 485 - fm.stringWidth("3") / 2, center_y - 10 + fm.getAscent() / 2);
        g2.drawString("6", center_x - fm.stringWidth("6") / 2, 475 + fm.getAscent() / 2);
        g2.drawString("9", 65 - fm.stringWidth("9") / 2, center_y - 10 + fm.getAscent() / 2);

    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
//        int quadrant = 0;
//        double graphical_X = e.getX() - CLOCKRADIUS - 25;
//        double graphical_Y = e.getY() - CLOCKRADIUS - 25;
//
//        double clickDeg = -Math.tan(Math.atan(graphical_Y/graphical_X));
//        double hourDeg = Math.tan(Math.toRadians(getHourDeg()));
//        System.out.println("Deg " + clickDeg);
//        System.out.println("HDeg " + hourDeg);
//
//        if(hoursHandClickedWithinRange(clickDeg))
//            hoursHandHeld = true;
//        else if(minutesHandClickedWithinRange(clickDeg))
//            minutesHandHeld = true;
//        else if(secondsHandClickedWithinRange(clickDeg))
//            secondsHandHeld = true;
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (hoursHandHeld || minutesHandHeld || secondsHandHeld)
            hoursHandHeld = minutesHandHeld = secondsHandHeld = false;
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        //DO NOTHING
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        //DO NOTHING
    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  {@code MOUSE_DRAGGED} events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * {@code MOUSE_DRAGGED} events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
//        int x = e.getX();
//        int y = e.getY();
//        if (hoursHandHeld) System.out.println("hour");
//        if (minutesHandHeld) System.out.println("minute");
//        if (secondsHandHeld) System.out.println("second");
    }
    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        //DO NOTHING
    }
}
