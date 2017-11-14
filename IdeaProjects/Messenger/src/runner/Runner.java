package runner;

import core.Window;

import javax.swing.*;

public class Runner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
//        Server server = new Server();
    }
}


