import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pane extends JPanel implements ActionListener {
    private final String ITEMS[] = {"c", "s", "e", "r", "d", "xs", "xh", "xp"};
    private JPanel lowerPane;
    private JPanel upperPane;
    private JComboBox<String> cb;
    private RecordManager rm;
    private JTextField jt;
    private JLabel com;
    private JLabel args;

    public Pane() {
        cb = new JComboBox<>(ITEMS);
        cb.addActionListener(this);

        rm = new RecordManager();

        jt = new JTextField();
        jt.addActionListener(this);

        com = new JLabel("Commands");
        args = new JLabel("Arguments");

        upperPane = new JPanel();
        lowerPane = new JPanel();

        upperPane.setLayout(new FlowLayout());
        lowerPane.setLayout(new FlowLayout());

        upperPane.add(com);
        upperPane.add(args);

        lowerPane.add(cb);

        setLayout(new BorderLayout());

        add(upperPane, "North");
        add(lowerPane, "South");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cb)) {
            if (cb.getSelectedItem().equals("c")) {
                //rm.makeNew(Integer.parseInt(st.nextToken()));
                //System.out.println();

            } else if (cb.getSelectedItem().equals("s")) {
//                int key = Integer.parseInt(st.nextToken());
//                String data = st.nextToken();
//                rm.store(new TreeNode(key, data));
//                System.out.println();
            } else if (cb.getSelectedItem().equals("e")) {
//                int key = Integer.parseInt(st.nextToken());
//                System.out.println(rm.search(key));
            } else if (cb.getSelectedItem().equals("r")) {
//                try {
//                    int key = Integer.parseInt(st.nextToken());
//                    TreeNode find = rm.searchNode(key);
//                    if (find != null) {
//                        String data = find.getData();
//                        System.out.print((data != null) ? data : "");
//                    }
//                } catch (NumberFormatException e) {
//                    System.err.print("key input not as integer");
//                } finally {
//                    System.out.println();
//                }
            } else if (cb.getSelectedItem().equals("d")) {
//                int key = Integer.parseInt(st.nextToken());
//                rm.delete(key);
            } else if (cb.getSelectedItem().equals("xs")) {
//                System.out.println(rm.size());
            } else if (cb.getSelectedItem().equals("xh")) {
//                System.out.println(rm.height());
            } else if (cb.getSelectedItem().equals("xp")) {
//                System.out.println(rm);
            }
        }
    }
}
