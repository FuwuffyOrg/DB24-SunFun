package oop.sunfun.ui;

import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.Anchors;
import oop.sunfun.ui.layout.GenericPage;
import oop.sunfun.ui.layout.GridBagConstraintBuilder;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Component;

public final class LoginPage extends GenericPage {

    public LoginPage(final String title, final CloseEvents closeEvent) {
        super(title, closeEvent);
        // Add two labels and text boxes for inputting username and password.
        final Component lblEmail = new JLabel("Email: ");
        final Component lblPassword = new JLabel("Password: ");
        final Component txtEmail = new JTextField();
        final Component txtPassword = new JPasswordField();
        final JButton btnLogin = new JButton("Login");
        final JButton btnRegister = new JButton("Goto Register");
        // Add all the components.
        this.addPanelComponent(lblEmail,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(0)
                        .setFillAll()
                        .setAnchor(Anchors.TOP_LEFT)
                        .build()
        );
        this.addPanelComponent(txtEmail,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(1)
                        .setFillAll()
                        .setAnchor(Anchors.TOP_RIGHT)
                        .build()
        );
        this.addPanelComponent(lblPassword,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(0)
                        .setFillAll()
                        .setAnchor(Anchors.CENTER_LEFT)
                        .build()
        );
        this.addPanelComponent(txtPassword,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(1)
                        .setFillAll()
                        .setAnchor(Anchors.CENTER_RIGHT)
                        .build()
        );
        this.addPanelComponent(btnLogin,
                new GridBagConstraintBuilder()
                        .setRow(2).setColumn(0)
                        .setAnchor(Anchors.BOTTOM_LEFT)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(btnRegister,
                new GridBagConstraintBuilder()
                        .setRow(2).setColumn(1)
                        .setAnchor(Anchors.BOTTOM_RIGHT)
                        .setFillAll()
                        .build()
        );
        // Add events
        btnRegister.addActionListener(e -> {
            final JFrame registerPage = new RegisterPage("SunFun Register", CloseEvents.EXIT_PROGRAM);
            registerPage.setVisible(true);
            LoginPage.this.dispose();
        });
        // Finish the window.
        this.buildWindow();
    }
}
