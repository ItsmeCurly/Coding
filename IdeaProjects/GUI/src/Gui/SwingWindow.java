package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * The type Swing window.
 */
public class SwingWindow extends JFrame implements WindowListener {
    private final static Dimension SIZE = new Dimension(240, 60);
    /**
     * The constant SCREENSIZE.
     */
    final static Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Instantiates a new Swing window.
     */
    public SwingWindow() {
        createAndShowGui();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingWindow::new);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    private void createAndShowGui() {
        JPanel p = new Pane();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Records");

        Container cp = getContentPane();

        p.setPreferredSize(SIZE);
        cp.add(p);

        addWindowListener(this);

        pack();
        setLocation((int) (SCREENSIZE.getWidth() / 2 - SIZE.getWidth() / 2),
                (int) (SCREENSIZE.getHeight() / 2 - SIZE.getHeight() / 2));
        setVisible(true);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        setVisible(false);
        dispose();
        System.exit(0);
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //DO NOTHING
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //DO NOTHING
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //DO NOTHING
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //DO NOTHING
    }
}