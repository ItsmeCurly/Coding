package com.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringTokenizer;

public class Pane extends JPanel implements ActionListener, FocusListener {
    private final Dimension SFSIZE = new Dimension(500, 400);
    private final String ITEMS[] = {"<none>", "c", "s", "e", "r", "d", "xs", "xh", "xp"};
    private RecordManager rm;

    private StateFrame sf1;
    //private StateFrame sf2;

    private JPanel[][] jp;

    private JComboBox<String> cb;
    private JTextField jt;
    private JLabel com, args;
    private JButton jb;

    private int command;
    private String text;

    public Pane() {
        command = -1;
        text = "";

        sf1 = new StateFrame(new Point((int) (Window.SCREENSIZE.getWidth() * 3 / 4 - SFSIZE.getWidth() / 2),
                (int) (Window.SCREENSIZE.getHeight() / 2 - SFSIZE.getHeight() / 2)));

//        sf2 = new StateFrame(new Point((int)(Window.SCREENSIZE.getWidth() / 4 - SFSIZE.getWidth() / 2),
//                (int) (Window.SCREENSIZE.getHeight() / 2 - SFSIZE.getHeight() / 2)));

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

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cb)) {
            if (Objects.equals(cb.getSelectedItem(), "<none>")) {
                command = -1;
                reveal(false, false, false);
            } else if (Objects.equals(cb.getSelectedItem(), "c")) {
                command = 0;
                reveal(true, true, true);
            } else if (Objects.equals(cb.getSelectedItem(), "s")) {
                command = 1;
                reveal(true, true, true);
            } else if (Objects.equals(cb.getSelectedItem(), "e")) {
                command = 2;
                reveal(true, true, true);
            } else if (Objects.equals(cb.getSelectedItem(), "r")) {
                command = 3;
                reveal(true, true, true);
            } else if (Objects.equals(cb.getSelectedItem(), "d")) {
                command = 4;
                reveal(true, true, true);
            } else if (Objects.equals(cb.getSelectedItem(), "xs")) {
                command = 5;
                reveal(false, false, true);
            } else if (Objects.equals(cb.getSelectedItem(), "xh")) {
                command = 6;
                reveal(false, false, true);
            } else if (Objects.equals(cb.getSelectedItem(), "xp")) {
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
                    try {
                        st = new StringTokenizer(text, " ");
                        key = Integer.parseInt(st.nextToken());
                    } catch (NoSuchElementException | NumberFormatException err) {
                        displayCaption("Command requires args in form c k");
                        break;
                    }
                    executed = true;

                    rm.makeNew(key);
                    sf1.append("New tree created with k value " + text + "\n\n");
                    updateStateFrame();
                    resetText();
                    break;
                case 1:
                    try {
                        st = new StringTokenizer(text, " ");
                        key = Integer.parseInt(st.nextToken());
                        data = st.nextToken();
                    } catch (NoSuchElementException | NumberFormatException err) {
                        displayCaption("Command requires args in form s k d");
                        break;
                    }
                    executed = true;

                    rm.store(new TreeNode(key, data));
                    sf1.append("Node successfully stored\n\n");
                    updateStateFrame();
                    resetText();
                    break;
                case 2:
                    try {
                        st = new StringTokenizer(text, " ");
                        key = Integer.parseInt(st.nextToken());
                    } catch (NoSuchElementException err) {
                        displayCaption("Command requires args in form e k");
                        break;
                    }
                    executed = true;

                    sf1.append(rm.search(key) + "\n\n");
                    updateStateFrame();
                    resetText();
                    break;
                case 3:
                    try {
                        st = new StringTokenizer(text, " ");
                        key = Integer.parseInt(st.nextToken());
                    } catch (NoSuchElementException | NumberFormatException err) {
                        displayCaption("Command requires args in form r k");
                        break;
                    }
                    executed = true;
                    TreeNode find = rm.searchNode(key);
                    if (find != null) {
                        data = find.getData();
                        sf1.append((data != null) ? data + "\n\n" : "");
                    }
                    updateStateFrame();
                    resetText();
                    break;
                case 4:
                    try {
                        st = new StringTokenizer(text, " ");
                        key = Integer.parseInt(st.nextToken());
                    } catch (NoSuchElementException | NumberFormatException err) {
                        displayCaption("Command requires args in form d k");
                        break;
                    }

                    executed = true;

                    if (rm.delete(key))
                        sf1.append("Node successfully deleted\n\n");
                    else sf1.append("Node not deleted\n\n");
                    updateStateFrame();
                    resetText();
                    break;
                case 5:
                    executed = true;
                    sf1.append("Size: " + rm.size() + "\n\n");
                    updateStateFrame();
                    resetText();
                    break;
                case 6:
                    executed = true;
                    sf1.append("Height: " + rm.height() + "\n\n");
                    updateStateFrame();
                    resetText();
                    break;
                case 7:
                    executed = true;
                    sf1.append(rm.toString() + "\n\n");
                    updateStateFrame();
                    resetText();
                    break;
            }
            if (executed) {
                sf1.setVisible(true);

                cb.setSelectedIndex(0);
                command = -1;
                reveal(false, false, false);
            }
            repaint();
        }

    }

    private void resetText() {
        text = "";
        jt.setText(text);
    }

    private void updateStateFrame() {
        //sf2.delete();
        //sf2.append(rm.toString());
    }

    private void reveal(boolean arg, boolean text, boolean button) {
        args.setVisible(arg);
        jt.setVisible(text);
        jb.setVisible(button);
    }

    private void displayCaption(String caption) {
        CaptionFrame cf = new CaptionFrame(caption, getParent());
    }

    @Override
    public void focusGained(FocusEvent e) {
        //DO NOTHING
    }

    @Override
    public void focusLost(FocusEvent e) {
        text = jt.getText();
    }

    private class CaptionFrame extends JFrame implements ActionListener {
        private JButton jb;

        CaptionFrame(String caption, Container parent) {
            setLayout(new BorderLayout());
            JLabel jl = new JLabel(caption);
            jb = new JButton("Ok");
            jb.addActionListener(this);

            Container c = getContentPane();

            c.add(jl, "Center");
            c.add(jb, "South");

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            setLocationRelativeTo(parent);

            pack();
            setVisible(true);
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(jb))
                dispose();
        }
    }

    private class StateFrame extends JFrame {

        public void del
        private JTextArea jta;
        private String text, writeText;

        StateFrame(Point loc) {
            text = writeText = "";
            jta = new JTextArea(15, 30);
            jta.setEditable(false);

            Container c = getContentPane();
            c.add(jta);

            setPreferredSize(SFSIZE);

            setLocation(loc);
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            pack();
        }

        ete() {
            writeText = "";
            jta.setText(writeText);
        }

        void append(String aString) {
            text = aString;
            writeText += text;
            jta.setText(writeText);
            text = "";
        }.

        void append(int aString) {
            append(String.valueOf(aString));
        }
    }
}