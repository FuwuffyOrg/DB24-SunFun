package oop.sunfun.ui;

import oop.sunfun.ui.layout.GridBagConstraintBuilder;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends GenericPage {
    private static final String WINDOW_TITLE = "SunFun Login";

    public LoginPage() {
        super(WINDOW_TITLE);
        // Add two labels and text boxes for inputting username and password.
        final Component label_email = new JLabel("Email: ");
        final Component label_password = new JLabel("Password: ");
        final Component textfield_email = new JTextField();
        final Component textfield_password = new JPasswordField();
        final Component button_confirm = new JButton("Login");
        // Add all the components.
        this.addPanelComponent(label_email, 0, 0);
        this.addPanelComponent(textfield_email, 0, 1);
        this.addPanelComponent(label_password, 1, 0);
        this.addPanelComponent(textfield_password, 1, 1);
        this.addPanelComponent(button_confirm, 2, 0,
                GridBagConstraintBuilder.BASE_WEIGHT, 1.0d);
        // Finish the window.
        this.buildWindow();
    }
}
