package Gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringTokenizer;

public class Pane extends JPanel implements ActionListener, FocusListener {
    private RecordManager rm;

    private StateFrame sf;

    private JComboBox<String> cb;
    private JTextField jt;
    private JButton jb;
    private JLabel args;
    private int command;
    private String commText, argsText;

    private Font f1;

    private Color c1, c2;

    Pane() {
        command = -1;
        commText = argsText = "";

        f1 = new Font("TimesRoman", Font.PLAIN, 12);

        c1 = Color.RED;
        c2 = Color.BLACK;

        Dimension sfSize = new Dimension(500, 400);
        sf = new StateFrame(new Point((int) (Gui.SwingWindow.SCREENSIZE.getWidth() * 3 / 4 - sfSize.getWidth() / 2),
                (int) (Gui.SwingWindow.SCREENSIZE.getHeight() / 2 - sfSize.getHeight() / 2)),
                f1);

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

        JLabel com = new JLabel("Commands");
        args = new JLabel("Args");

        jb = new JButton("Run");
        jb.addActionListener(this);

        JPanel[][] jp = new JPanel[2][3];
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
                    rm.makeNew(key);

                    printCommand();
                    printOutput("\n");

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
                    rm.store(new TreeNode(key, data));

                    printCommand();
                    printOutput("\n");

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
                    printOutput(rm.search(key) + "\n\n");

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
                        printOutput((data != null) ? data + "\n\n" : "");
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
                    printOutput("\n");

                    rm.delete(key);

                    updateStateFrame();
                    resetText();
                    break;
                case 5:
                    executed = true;

                    printCommand();
                    printOutput(rm.size() + "\n\n");

                    updateStateFrame();
                    resetText();
                    break;
                case 6:
                    executed = true;

                    printCommand();
                    printOutput(rm.height() + "\n\n");

                    updateStateFrame();
                    resetText();
                    break;
                case 7:
                    executed = true;

                    printCommand();
                    printOutput(rm.toString() + "\n\n");

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
        sf.appendConsole(commText + " " + argsText + "\n", c1);
    }

    private void printOutput(String msg) {
        sf.appendConsole(msg, c2);
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

        private CaptionFrame(String caption, Container parent) {
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

    private class StateFrame extends JFrame implements WindowListener {
        private JTextPane console;
        private JTextArea treeState;
        private String text, writeText, treeText;
        private Font font;

        private StateFrame(Point loc, Font font) {
            JPanel jp = new JPanel(new GridLayout(1, 2));
            this.font = font;

            Border border = BorderFactory.createLineBorder(Color.black);
            treeText = "TreeState: ";

            console = new JTextPane();
            treeState = new JTextArea(treeText, 20, 15);

            appendConsole("Console: \n", c2);

            JScrollPane jsp1 = new JScrollPane(console);
            JScrollPane jsp2 = new JScrollPane(treeState);

            console.setEditable(false);
            treeState.setEditable(false);

            console.setBorder(border);
            treeState.setBorder(border);

            console.setPreferredSize(new Dimension(250, 150));
            treeState.setPreferredSize(new Dimension(250, 150));

            jp.add(jsp1);
            jp.add(jsp2);

            Container c = getContentPane();
            c.add(jp);

            setResizable(false);
            setLocation(loc);

            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(this);

            pack();
            setVisible(true);
        }

        void deleteState() {
            treeText = "TreeState: \n";
            treeState.setText(treeText);
        }

        void appendConsole(String aString, Color c) {
            StyledDocument doc = console.getStyledDocument();

            Style style = console.addStyle("ConsoleStyle1", null);

            StyleConstants.setForeground(style, c);

            try {
                doc.insertString(doc.getLength(), aString, style);
            } catch (BadLocationException err) {
                err.printStackTrace();
            }
        }

        void appendConsole(int aString, Color c) {
            appendConsole(String.valueOf(aString), c);
        }

        void setState(String aString) {
            treeText = "TreeState: \n" + aString;
            treeState.setText(treeText);
        }

        void setColor(Color c) {
            console.setForeground(c);
        }

        /**
         * Invoked the first time a window is made visible.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowOpened(WindowEvent e) {
            //DO NOTHING
        }

        /**
         * Invoked when the user attempts to close the window
         * from the window's system menu.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowClosing(WindowEvent e) {
            setExtendedState(JFrame.ICONIFIED);
        }

        /**
         * Invoked when a window has been closed as the result
         * of calling dispose on the window.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowClosed(WindowEvent e) {
            //DO NOTHING
        }

        /**
         * Invoked when a window is changed from a normal to a
         * minimized state. For many platforms, a minimized window
         * is displayed as the icon specified in the window's
         * iconImage property.
         *
         * @param e the event to be processed
         * @see Frame#setIconImage
         */
        @Override
        public void windowIconified(WindowEvent e) {
            //DO NOTHING
        }

        /**
         * Invoked when a window is changed from a minimized
         * to a normal state.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowDeiconified(WindowEvent e) {
            //DO NOTHING
        }

        /**
         * Invoked when the Window is set to be the active Window. Only a Frame or
         * a Dialog can be the active Window. The native windowing system may
         * denote the active Window or its children with special decorations, such
         * as a highlighted title bar. The active Window is always either the
         * focused Window, or the first Frame or Dialog that is an owner of the
         * focused Window.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowActivated(WindowEvent e) {
            //DO NOTHING
        }

        /**
         * Invoked when a Window is no longer the active Window. Only a Frame or a
         * Dialog can be the active Window. The native windowing system may denote
         * the active Window or its children with special decorations, such as a
         * highlighted title bar. The active Window is always either the focused
         * Window, or the first Frame or Dialog that is an owner of the focused
         * Window.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowDeactivated(WindowEvent e) {
            //DO NOTHING
        }
    }
}