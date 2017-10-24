import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Window extends JFrame implements WindowListener {
    final static Dimension SCREENSIZE = new Dimension(240, 80);

    public static void main(String[] args) {
        JFrame f = new JFrame();
        JPanel p = new Pane();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Records");

        Container cp = f.getContentPane();

        p.setPreferredSize(SCREENSIZE);
        cp.add(p);

        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        setVisible(false);
        dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent e) {
        dispose();
        System.exit(0);
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
