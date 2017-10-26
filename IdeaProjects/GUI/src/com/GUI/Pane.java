package com.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringTokenizer;

public class Pane extends JPanel implements ActionListener, FocusListener {
    private RecordManager rm;

    private StateFrame sf;

    private JPanel[][] jp;

    private JComboBox<String> cb;
    private JTextField jt;
    private JLabel com, args;
    private JButton jb;
    private int command;
    private String commText, argsText;

    Pane() {
        command = -1;
        commText = argsText = "";

        Dimension sfSize = new Dimension(500, 400);
        sf = new StateFrame(new Point((int) (Window.SCREENSIZE.getWidth() * 3 / 4 - sfSize.getWidth() / 2),
                (int) (Window.SCREENSIZE.getHeight() / 2 - sfSize.getHeight() / 2)));

        rm = new RecordManager();

        updateStateFrame();
        createUI();
    }

    private void createUI() {
        setLayout(new GridLayout(2, 3));

        String[] ITEMS = {"<none>", "c", "s", "e", "r", "d", "xs", "xh", "xp"};
        cb = new JComboBox<>(ITEMS);
        cb.addActionListener(this);

        jt = new JTextField(2);
        jt.addActionListener(this);
        jt.addFocusListener(this);

        com = new JLabel("Commands");
        args = new JLabel("Args");

        jb = new JButton("Run");
        jb.addActionListener(this);

        jp = new JPanel[2][3];
        for (int i = 0; i < jp.length; i++) {
            for (int j = 0; j < jp[0].length; j++) {
                jp[i][j] = new JPanel();
                add(jp[i][j]);
            }
        }

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
                commText = "c";
                reveal(true, true, true);
            } else if (Objects.equals(cb.getSelectedItem(), "s")) {
                command = 1;
                commText = "s";
                reveal(true, true, true);
            } else if (Objects.equals(cb.getSelectedItem(), "e")) {
                command = 2;
                commText = "e";
                reveal(true, true, true);
            } else if (Objects.equals(cb.getSelectedItem(), "r")) {
                command = 3;
                commText = "r";
                reveal(true, true, true);
            } else if (Objects.equals(cb.getSelectedItem(), "d")) {
                command = 4;
                commText = "d";
                reveal(true, true, true);
            } else if (Objects.equals(cb.getSelectedItem(), "xs")) {
                command = 5;
                commText = "xs";
                reveal(false, false, true);
            } else if (Objects.equals(cb.getSelectedItem(), "xh")) {
                command = 6;
                commText = "xh";
                reveal(false, false, true);
            } else if (Objects.equals(cb.getSelectedItem(), "xp")) {
                command = 7;
                commText = "xp";
                reveal(false, false, true);
            }
        } else if (e.getSource().equals(jt)) {
            argsText = jt.getText();
        } else if (e.getSource().equals(jb)) {
            StringTokenizer st;
            int key;
            String data;
            boolean executed = false;

            switch (command) {
                case 0:
                    try {
                        if (argsText.equals("")) argsText = "2";
                        st = new StringTokenizer(argsText, " ");
                        key = Integer.parseInt(st.nextToken());
                        if (key < 2) throw new InvalidKValException();
                    } catch (NoSuchElementException | NumberFormatException err) {
                        displayCaption("Command requires args in form c k");
                        break;
                    } catch (InvalidKValException err) {
                        displayCaption("K Value for tree must be > 1");
                        break;
                    }
                    executed = true;
                    printCommand();

                    rm.makeNew(key);
                    sf.appendConsole("\n");
                    updateStateFrame();
                    resetText();
                    break;
                case 1:
                    try {
                        st = new StringTokenizer(argsText, " ");
                        key = Integer.parseInt(st.nextToken());
                        data = st.nextToken();
                        if (rm.getKst() == null)
                            throw new NullPointerException();
                    } catch (NoSuchElementException | NumberFormatException err) {
                        displayCaption("Command requires args in form s k d");
                        break;
                    } catch (NullPointerException err) {
                        displayCaption("Tree does not exist");
                        break;
                    }
                    executed = true;
                    printCommand();

                    rm.store(new TreeNode(key, data));
                    sf.appendConsole("\n");
                    updateStateFrame();
                    resetText();
                    break;
                case 2:
                    try {
                        st = new StringTokenizer(argsText, " ");
                        key = Integer.parseInt(st.nextToken());
                        if (rm.getKst() == null)
                            throw new NullPointerException();
                    } catch (NoSuchElementException err) {
                        displayCaption("Command requires args in form e k");
                        break;
                    } catch (NullPointerException err) {
                        displayCaption("Tree does not exist");
                        break;
                    }
                    executed = true;
                    printCommand();
                    sf.appendConsole(rm.search(key) + "\n\n");
                    updateStateFrame();
                    resetText();
                    break;
                case 3:
                    try {
                        st = new StringTokenizer(argsText, " ");
                        key = Integer.parseInt(st.nextToken());
                        if (rm.getKst() == null)
                            throw new NullPointerException();
                    } catch (NoSuchElementException | NumberFormatException err) {
                        displayCaption("Command requires args in form r k");
                        break;
                    } catch (NullPointerException err) {
                        displayCaption("Tree does not exist");
                        break;
                    }
                    executed = true;
                    TreeNode find = rm.searchNode(key);
                    if (find != null) {
                        data = find.getData();
                        printCommand();
                        sf.appendConsole((data != null) ? data + "\n\n" : "");
                    }
                    updateStateFrame();
                    resetText();
                    break;
                case 4:
                    try {
                        st = new StringTokenizer(argsText, " ");
                        key = Integer.parseInt(st.nextToken());
                        if (rm.getKst() == null)
                            throw new NullPointerException();
                    } catch (NoSuchElementException | NumberFormatException err) {
                        displayCaption("Command requires args in form d k");
                        break;
                    } catch (NullPointerException err) {
                        displayCaption("Tree does not exist");
                        break;
                    }
                    executed = true;

                    printCommand();
                    sf.appendConsole("\n");
                    rm.delete(key);

                    updateStateFrame();
                    resetText();
                    break;
                case 5:
                    executed = true;

                    printCommand();
                    sf.appendConsole(rm.size() + "\n\n");

                    updateStateFrame();
                    resetText();
                    break;
                case 6:
                    executed = true;

                    printCommand();
                    sf.appendConsole(rm.height() + "\n\n");

                    updateStateFrame();
                    resetText();
                    break;
                case 7:
                    executed = true;

                    printCommand();
                    sf.appendConsole(rm.toString() + "\n\n");

                    updateStateFrame();
                    resetText();
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

    private void resetText() {
        argsText = "";
        jt.setText(argsText);
    }

    private void updateStateFrame() {
        sf.deleteState();
        sf.setState(rm.toString());
    }

    private void reveal(boolean arg, boolean text, boolean button) {
        args.setVisible(arg);
        jt.setVisible(text);
        jb.setVisible(button);
    }

    private void displayCaption(String caption) {
        new CaptionFrame(caption, getParent());
    }

    private void printCommand() {
        sf.appendConsole(commText + " " + argsText + "\n");
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        argsText = jt.getText();
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

            setLocation((int) (getLocation().getX() - getWidth() / 2), (int) (getLocation().getY() - getHeight() / 2));
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
        private JTextArea console;
        private JTextArea treeState;
        private String text, writeText, treeText;

        StateFrame(Point loc) {
            JPanel jp = new JPanel(new GridLayout(1, 2));
            Border border = BorderFactory.createLineBorder(Color.black);

            text = writeText = "";

            console = new JTextArea("Console: \n", 20, 30);
            treeState = new JTextArea("Tree State: \n", 20, 30);

            console.setEditable(false);
            treeState.setEditable(false);

            console.setBorder(border);
            treeState.setBorder(border);

            jp.add(console);
            jp.add(treeState);

            Container c = getContentPane();
            c.add(jp);

            setResizable(false);
            setLocation(loc);

            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    setExtendedState(JFrame.ICONIFIED);
                }
            });
            pack();
            setVisible(true);
        }

        @SuppressWarnings("unused")
        void deleteConsole() {
            writeText = "";
            console.setText(writeText);
        }

        void deleteState() {
            treeText = "";
            treeState.setText(treeText);
        }

        void appendConsole(String aString) {
            text = aString;
            writeText += text;
            console.setText(writeText);
            text = "";
        }

        @SuppressWarnings("unused")
        void appendConsole(int aString) {
            appendConsole(String.valueOf(aString));
        }

        void setState(String aString) {
            treeText = aString;
            treeState.setText(treeText);
        }
    }
}