package Clocks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DigitalClock extends AbstractClock implements ActionListener {
    private int todayTimeInMillis;
    private JTextArea jta;
    private NumberFormat fmt;
    private Font font;

    public DigitalClock(DateAndTime time) {
        super.time = time;
        createAndShowGUI();
        try {
            createFont();
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        fmt = new DecimalFormat("00");
        todayTimeInMillis = time.getTimeOfDay();
    }

    private void createAndShowGUI() {
        setBackground(new Color(238, 238, 238));
        jta = new JTextArea();
        jta.setBackground(new Color(238, 238, 238));
        jta.setForeground(Color.RED);
        //jta.setFont(new Font("TimesRoman", Font.BOLD, 144));
        setPreferredSize(new Dimension(640, 200));

        add(jta);
    }

    private void createFont() throws IOException, FontFormatException {
        File fontFile = new File("digital-7.regular.ttf");

        font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        font.deriveFont(Font.BOLD, 144);
        jta.setFont(font);

    }

    @Override
    public void updateTime(int spacing) {
        super.updateTime(spacing);
        todayTimeInMillis = (todayTimeInMillis + spacing) % DateAndTime.MILLISINDAY;
    }

    @Override
    public void displayTime() {
        jta.setText(getTimeString());
    }

    private String getTimeString() {
        return fmt.format(time.hour() % 12) + ":" + fmt.format(time.minute()) + ((time.hour() > 12) ? " AM" : " PM");
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
