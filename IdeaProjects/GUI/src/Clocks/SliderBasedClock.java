package Clocks;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SliderBasedClock extends AbstractClock implements ChangeListener {
    private JSlider js;
    private JLabel jl;

    public SliderBasedClock() {
        super.time = new DateAndTime();
    }

    public void createAndShowGUI() {
        js = new JSlider(0, 86400, (int) (super.time.getTime() - DateAndTime.getStartTime()));
        js.setMajorTickSpacing(3600);
        js.setMinorTickSpacing(600);
        js.addChangeListener(this);

        jl = new JLabel(time.getDateString());

        setLayout(new BorderLayout());

        add(js, "North");
        add(jl, "South");
    }

    @Override
    public void displayTime() {
        jl.setText(time.getDateString());
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
