package gui.admin;

import javax.swing.*;

public class AdminGui extends JFrame {
        public AdminGui() {
            setTitle("New Frame");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Use DISPOSE_ON_CLOSE to avoid memory leaks
            setLocationRelativeTo(null); // Center the window
        }
}