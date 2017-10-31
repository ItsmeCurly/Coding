package Clocks;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Slider based clock.
 */
public class SliderBasedClock extends AbstractClock implements ActionListener, ChangeListener {
    private JSlider js;
    private JLabel jl;
    private JButton jb;
    private long selectedTimeValue;

    /**
     * Instantiates a new Slider based clock.
     *
     * @param time the time
     */
    public SliderBasedClock(DateAndTime time) {
        super.time = time;
        createAndShowGUI();
        selectedTimeValue = js.getValue();
    }

    private void createAndShowGUI() {
        js = new JSlider(0, DateAndTime.MILLISINDAY, (int) ((time.getTime() - DateAndTime.OFFSET) % DateAndTime.MILLISINDAY));

        js.setMajorTickSpacing(3600000);
        js.setMinorTickSpacing(1800000);
        js.setPaintTicks(true);
        js.addChangeListener(this);

        jl = new JLabel(time.getDateString());

        jb = new JButton("Current Time");
        jb.addActionListener(this);

        setLayout(new BorderLayout());

        JPanel holder = new JPanel();

        holder.add(jl);
        holder.add(jb);

        add(js, "Center");
        add(holder, "South");

        setPreferredSize(new Dimension(640, 350));
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(jb)) {
            time.resetTimeToNormal();
            int newTime = (int) ((time.getTime() - DateAndTime.OFFSET) % DateAndTime.MILLISINDAY);
            selectedTimeValue = newTime;
            js.setValue(newTime);
        }
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
    public void stateChanged(ChangeEvent e) {
        time.addToTime(js.getValue() - selectedTimeValue);
        selectedTimeValue = js.getValue();
    }
}
