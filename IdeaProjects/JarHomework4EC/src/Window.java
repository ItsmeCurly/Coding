import javax.swing.*;
import java.awt.*;

/**
 * The type Window.
 */
public class Window extends JFrame {

    /**
     * Instantiates a new Window.
     */
    public Window() {
        createAndShowGUI();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
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
