import javax.swing.*;

public class Window extends JFrame {
    public static void main() {
        JFrame f = new JFrame();
        JPanel p = new Pane();

        f.add(p);
        f.pack();

    }
}
