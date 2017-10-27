package Clocks;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderBasedClock extends AbstractClock implements ChangeListener {
    private JSlider js;

    public SliderBasedClock() {
        super.time = new DateAndTime();
    }

    public void createAndShowGUI() {
        js = new JSlider(0, 86400, (int) (super.time.getTime() - DateAndTime.getStartTime()));
        js.setMajorTickSpacing(3600);
        js.setMinorTickSpacing(600);
        js.addChangeListener(this);
    }

    @Override
    public void displayTime() {

    }

    /**
     * Invoked when the target of the listener has changed its state.
     *
     * @param e a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        time.setNewTime(js.getValue());
    }
}
