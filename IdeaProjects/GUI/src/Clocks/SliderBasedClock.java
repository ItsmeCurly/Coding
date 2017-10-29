package Clocks;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SliderBasedClock extends AbstractClock implements ChangeListener {
    private JSlider js;
    private JLabel jl;
    private long selectedTimeValue;

    public SliderBasedClock() {
        super.time = new DateAndTime();
        createAndShowGUI();
        selectedTimeValue = js.getValue();
    }

    private void createAndShowGUI() {
        js = new JSlider(0, 86400000, (int) ((time.getTime() - 14400000) % 86400000));

        js.setMajorTickSpacing(3600000);
        js.setMinorTickSpacing(600000);
        js.setPaintTicks(true);
        js.addChangeListener(this);

        jl = new JLabel(time.getDateString());

        setLayout(new BorderLayout());

        add(js, "Center");
        add(jl, "South");

        setPreferredSize(new Dimension(600, 200));
    }

    @Override
    public void updateTime(int spacing) {
        super.updateTime(spacing);
        selectedTimeValue = selectedTimeValue + spacing;
        js.setValue((int) selectedTimeValue);
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
    public synchronized void stateChanged(ChangeEvent e) {
        time.addToTime(js.getValue() - selectedTimeValue);
        selectedTimeValue = js.getValue();
    }
}
