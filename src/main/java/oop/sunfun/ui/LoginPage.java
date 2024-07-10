package oop.sunfun.ui;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends GenericPage {
    private static final String WINDOW_TITLE = "SunFun Login";
    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 100;

    public LoginPage() {
        super(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT);
        // Add two labels and text boxes for inputting username and password
        final Component label_email = new JLabel("Email: ");
        final Component label_password = new JLabel("Password: ");
        final Component textfield_email = new JTextField();
        final Component textfield_password = new JPasswordField();
        final Component button_confirm = new JButton("Login");

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        this.addPanelComponent(label_email, gbc);
        gbc.gridx = 1;
        this.addPanelComponent(textfield_email, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.addPanelComponent(label_password, gbc);
        gbc.gridx = 1;
        this.addPanelComponent(textfield_password, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        this.addPanelComponent(button_confirm, gbc);
        // Display the window
        this.setVisible(true);
    }
}
