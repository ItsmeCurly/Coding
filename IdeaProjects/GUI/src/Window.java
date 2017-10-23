import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Window extends JFrame implements WindowListener {
    final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static void main() {
        JFrame f = new JFrame();
        JPanel p = new Pane();

        f.setTitle("K-Search Trees");
        Container cp = f.getContentPane();

        cp.add(p);

        f.pack();
        f.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

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
