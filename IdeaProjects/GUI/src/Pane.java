import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

public class Pane extends JPanel implements ActionListener {
    private final String spacing1 = " ";
    private final String spacing2 = "                 ";
    private final String ITEMS[] = {"<none>", "c", "s", "e", "r", "d", "xs", "xh", "xp"};
    private JPanel lowerPane;
    private JPanel upperPane;
    private JComboBox<String> cb;
    private RecordManager rm;
    private JTextField jt;
    private JLabel com;
    private JLabel args;
    private JButton jb;

    private int command;
    private String text;

    private StringTokenizer st;

    public Pane() {
        command = -1;

        cb = new JComboBox<>(ITEMS);
        cb.addActionListener(this);

        rm = new RecordManager();

        jt = new JTextField(2);
        jt.addActionListener(this);

        com = new JLabel("Commands" + spacing1);
        args = new JLabel("Args" + spacing2);

        upperPane = new JPanel();
        lowerPane = new JPanel();

        jb = new JButton("Run");

        upperPane.setLayout(new FlowLayout());
        lowerPane.setLayout(new FlowLayout());

        createUI();

        setLayout(new GridLayout(2, 3);

        add(upperPane, "North");
        add(lowerPane, "Center");
    }

    private void createUI() {
        upperPane.add(com);
        upperPane.add(args);

        args.setVisible(false);

        lowerPane.add(cb);
        lowerPane.add(jt);
        lowerPane.add(jb);

        jt.setVisible(false);
        jb.setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
        if (e.getSource().equals(cb)) {
            boolean show = false;
            boolean hide = true;
            if (cb.getSelectedItem().equals("<none>")) {
                reveal(false, false, false);
                command = -1;
            } else if (cb.getSelectedItem().equals("c")) {
                command = 0;
                reveal(true, true, true);
            } else if (cb.getSelectedItem().equals("s")) {
                command = 1;
                reveal(true, true, true);
            } else if (cb.getSelectedItem().equals("e")) {
                command = 2;
                reveal(true, true, true);
            } else if (cb.getSelectedItem().equals("r")) {
                command = 3;
                reveal(true, true, true);
            } else if (cb.getSelectedItem().equals("d")) {
                command = 4;
                reveal(true, true, true);
            } else if (cb.getSelectedItem().equals("xs")) {
                command = 5;
                reveal(false, false, true);
            } else if (cb.getSelectedItem().equals("xh")) {
                command = 6;
                reveal(false, false, true);
            } else if (cb.getSelectedItem().equals("xp")) {
                command = 7;
                reveal(false, false, true);
            }
        } else if (e.getSource().equals(jt)) {
            text = jt.getText();
        } else if (e.getSource().equals(jb)) {
            st = new StringTokenizer(text, " ");
            int key;
            String data;
            switch (command) {
                case 0:
                    rm.makeNew(Integer.parseInt(st.nextToken()));
                    break;
                case 1:
                    key = Integer.parseInt(st.nextToken());
                    data = st.nextToken();
                    rm.store(new TreeNode(key, data));
                    break;
                case 2:
                    key = Integer.parseInt(st.nextToken());
                    System.out.println(rm.search(key));
                    break;
                case 3:
                    key = Integer.parseInt(st.nextToken());
                    TreeNode find = rm.searchNode(key);
                    if (find != null) {
                        data = find.getData();
                        System.out.print((data != null) ? data : "");
                    }
                    break;
                case 4:
                    key = Integer.parseInt(st.nextToken());
                    rm.delete(key);
                    break;
                case 5:
                    System.out.println(rm.size());
                    break;
                case 6:
                    System.out.println(rm.height());
                    break;
                case 7:
                    System.out.println(rm);
                    break;

            }
            reveal(false, false, false);
        }
        repaint();
    }

    private void reveal(boolean arg, boolean text, boolean button) {
        args.setVisible(arg);
        jt.setVisible(text);
        jb.setVisible(button);
    }
}
