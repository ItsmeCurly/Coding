package Clocks;

import javax.swing.*;

public class Window extends JFrame {

    public Window() {
        createAndShowGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
    }

    public void createAndShowGUI() {
        JPanel jp = new ClockGUI();
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setResizable(false);
//        f.setTitle("Records");
        add(jp);

    }
}
