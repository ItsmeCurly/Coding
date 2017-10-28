package Clocks;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window() {
        createAndShowGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
    }

    private void createAndShowGUI() {
        JPanel jp = new ClockGUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Clocks");

        Container cp = getContentPane();

        cp.add(jp);

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
