package core;

import user.Userdata;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class Pane extends JPanel implements ActionListener {
    private Userdata user;

    public Pane() {
        createVars();
        createAndShowGui();
    }

    private void createVars() {
        user = new Userdata();
    }

    private void createAndShowGui() {
        showLogin();
        JTextField textBox = new TextBoxListener(new Dimension(0, 0));

        add(textBox);
    }

    private void showLogin() {
        add(new LoginScreen());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private class LoginScreen extends JPanel implements KeyListener {
        private JTextField userNameTextBox;
        private JPasswordField passwordTextBox;

        private JButton submitButton;
        private JButton newUserButton;

        public LoginScreen() {
            this.createVars();
            this.createAndShowGui();
        }

        private void createVars() {
            //TODO
            userNameTextBox = new TextBoxListener(new Dimension(0, 0));
            passwordTextBox = new JPasswordField();

            submitButton = new JButton("Login");
            newUserButton = new JButton("Create an Account");
        }

        private void createAndShowGui() {
            Border border = BorderFactory.createLineBorder(Color.BLACK);

            userNameTextBox.setEditable(true);
            passwordTextBox.setEditable(true);

            userNameTextBox.setBorder(border);
            passwordTextBox.setBorder(border);

            submitButton.addActionListener(new ButtonListener());
        }

        private void submitInfo() {
//            if(Server.checkLogin(userNameTextBox.getText(), Arrays.toString(passwordTextBox.getPassword()))) {
//                getRootPane().remove(this);
//            }
            user.setPassword(Arrays.toString(passwordTextBox.getPassword()));
            user.setUserName(userNameTextBox.getText());
        }

        private void setFocusToNextComponent() {
            if (userNameTextBox.hasFocus()) passwordTextBox.requestFocus();
            else if (passwordTextBox.hasFocus()) submitButton.requestFocus();
            else userNameTextBox.requestFocus();
        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) submitInfo();
            else if (e.getKeyCode() == KeyEvent.VK_TAB) setFocusToNextComponent();
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        private class ButtonListener implements ActionListener {

            public ButtonListener() {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(submitButton)) {
                    submitInfo();
                } else if (e.getSource().equals(newUserButton)) {
                    getParent().removeAll();
                    getParent().add(new CreateAccountPanel());
                }
            }
        }
    }

    private class CreateAccountPanel extends JPanel {
        private JTextField emailTextBox;
        private JTextField userNameTextBox;

        private JTextField passwordTextBox;
        private JTextField confirmPasswordTextBox;

        private Userdata user;

        public CreateAccountPanel() {
            this.createVars();
            this.createAndShowGUI();
        }

        private void createVars() {
            emailTextBox = new TextBoxListener(new Dimension(0, 0));
            userNameTextBox = new TextBoxListener(new Dimension(0, 0));
            passwordTextBox = new TextBoxListener(new Dimension(0, 0));
            confirmPasswordTextBox = new TextBoxListener(new Dimension(0, 0));
        }

        private void createAndShowGUI() {

        }
    }

    private class TextBoxListener extends JTextField implements KeyListener {
        private String currentText;

        private TextBoxListener(Dimension size) {
            this.createVars();
            this.createAndShowGui();
            this.setPreferredSize(size);
        }

        private void createVars() {
            currentText = "";
        }

        private void createAndShowGui() {
            setEditable(true);
            setVisible(true);
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            //DO NOTHING
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //DO NOTHING
        }
    }
}