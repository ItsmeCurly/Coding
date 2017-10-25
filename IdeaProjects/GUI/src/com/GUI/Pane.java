package com.GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;

public class Pane extends JPanel implements ActionListener, FocusListener {
    private final String ITEMS[] = {"<none>", "c", "s", "e", "r", "d", "xs", "xh", "xp"};
    private RecordManager rm;

    private ResultFrame rf;

    private JPanel[][] jp;

    private JComboBox<String> cb;
    private JTextField jt;
    private JLabel com;
    private JLabel args;
    private JButton jb;

    private int command;
    private String text;

    public Pane() {
        command = -1;
        text = "";
        rf = new ResultFrame(getParent());
        cb = new JComboBox<>(ITEMS);
        cb.addActionListener(this);

        rm = new RecordManager();

        jt = new JTextField(2);
        jt.addActionListener(this);
        jt.addFocusListener(this);

        com = new JLabel("Commands");
        args = new JLabel("Args");

        jb = new JButton("Run");
        jb.addActionListener(this);

        setLayout(new GridLayout(2, 3));

        jp = new JPanel[2][3];
        for (int i = 0; i < jp.length; i++) {
            for (int j = 0; j < jp[0].length; j++) {
                jp[i][j] = new JPanel();
                add(jp[i][j]);
            }
        }

        createUI();
    }

    private void createUI() {
        jp[0][0].add(com);
        jp[0][1].add(args);

        args.setVisible(false);

        jp[1][0].add(cb);
        jp[1][1].add(jt);
        jp[1][2].add(jb);

        jt.setVisible(false);
        jb.setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cb)) {
            if (cb.getSelectedItem().equals("<none>")) {
                command = -1;
                reveal(false, false, false);
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
                rf.append("xs\n\n");
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
            StringTokenizer st;

            int key;
            String data;

            boolean executed = false;

            switch (command) {
                case 0:
                    if (text.equals("")) {
                        displayCaption("Command requires args in form c k");
                        break;
                    }
                    executed = true;
                    st = new StringTokenizer(text, " ");

                    rm.makeNew(Integer.parseInt(st.nextToken()));
                    rf.append("New tree created with k value " + text);
                    break;
                case 1:
                    if (text.equals("")) {
                        displayCaption("Command requires args in form s k d");
                        break;
                    }
                    executed = true;
                    st = new StringTokenizer(text, " ");

                    key = Integer.parseInt(st.nextToken());
                    data = st.nextToken();
                    rm.store(new TreeNode(key, data));
                    rf.append("Node successfully stored\n");
                    break;
                case 2:
                    if (text.equals("")) {
                        displayCaption("Command requires args in form e k");
                        break;
                    }
                    executed = true;
                    st = new StringTokenizer(text, " ");

                    key = Integer.parseInt(st.nextToken());

                    rf.append((rm.search(key)));
                    break;
                case 3:
                    if (text.equals("")) {
                        displayCaption("Command requires args in form d k");
                        break;
                    }
                    executed = true;
                    st = new StringTokenizer(text, " ");

                    key = Integer.parseInt(st.nextToken());
                    TreeNode find = rm.searchNode(key);
                    if (find != null) {
                        data = find.getData();
                        rf.append((data != null) ? data : "");
                    }
                    break;
                case 4:
                    if (text.equals("")) {
                        displayCaption("Command requires args in form d k");
                        break;
                    }
                    executed = true;
                    st = new StringTokenizer(text, " ");

                    key = Integer.parseInt(st.nextToken());
                    rm.delete(key);
                    break;
                case 5:
                    executed = true;
                    rf.append(rm.size());
                    break;
                case 6:
                    executed = true;
                    rf.append(rm.height());
                    break;
                case 7:
                    executed = true;
                    rf.append(rm.toString());
                    break;
            }
            if (executed) {
                cb.setSelectedIndex(0);
                command = -1;
                reveal(false, false, false);
            }
            repaint();
        }

    }

    private void reveal(boolean arg, boolean text, boolean button) {
        args.setVisible(arg);
        jt.setVisible(text);
        jb.setVisible(button);
    }

    private void displayCaption(String caption) {
        CaptionFrame cf = new CaptionFrame(caption, getParent());
        cf.setLocationRelativeTo(null);
    }

    @Override
    public void focusGained(FocusEvent e) {
        //DO NOTHING
    }

    @Override
    public void focusLost(FocusEvent e) {
        text = jt.getText();
    }

    private class CaptionFrame extends JFrame implements WindowListener, WindowFocusListener, ActionListener {
        private JButton jb;

        public CaptionFrame(String caption, Container parent) {
            JLabel jl = new JLabel(caption);
            jb = new JButton("Ok");
            jb.addActionListener(this);

            Container cp = getContentPane();

            cp.add(jl, "Center");
            cp.add(jb, "South");

            pack();
            setVisible(true);
        }

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            dispose();
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

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(jb)) {
                dispose();
            }
        }

        @Override
        public void windowGainedFocus(WindowEvent e) {
        }

        @Override
        public void windowLostFocus(WindowEvent e) {
            dispose();
        }
    }

    private class ResultFrame extends JFrame implements ActionListener {
        private final Dimension SIZE = new Dimension(500, 400);
        private String text;
        private JTextArea jta;
        private String writeText;

        public ResultFrame(Container parent) {
            JPanel jp = new JPanel();
            jp.setLayout(new BorderLayout());
            text = "";
            writeText = "";
            jta = new JTextArea(15, 30);
            jp.add(jta);

            Container c = getContentPane();
            c.add(jp);

            jp.setPreferredSize(SIZE);

            setLocation((int) ((Window.SCREENSIZE.getWidth() * 3 / 4) - SIZE.getWidth() / 2), (int) (Window.SCREENSIZE.getHeight() / 2 - SIZE.getHeight() / 2));

            pack();
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }

        public void append(String aString) {
            text = aString;
            writeText += text;
            jta.setText(writeText);
            text = "";
        }

        public void append(int aString) {
            text += aString;
            writeText += text;
            jta.setText(writeText);
            text = "";
        }

        public void delete() {
            writeText = "";
            jta.setText(writeText);
        }
    }
}
