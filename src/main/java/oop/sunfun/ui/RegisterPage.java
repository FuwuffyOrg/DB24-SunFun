package oop.sunfun.ui;

import oop.sunfun.database.enums.ParentType;
import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;
import oop.sunfun.ui.layout.GridBagConstraintBuilder;

import javax.swing.*;

import java.awt.Component;
import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicInteger;

public final class RegisterPage extends GenericPage {
    final Component txtName;
    final Component txtSurname;
    final Component txtPhone;
    final Component txtEmail;
    final Component txtPassword;
    final Component txtPasswordConfirm;
    final ButtonGroup radParentTypeGroup;

    public RegisterPage(final String title, final CloseEvents closeEvent) {
        super(title, closeEvent);
        // Add two labels and text boxes for inputting username and password.
        final Component lblName = new JLabel("Nome: ");
        final Component lblSurname = new JLabel("Cognome: ");
        final Component lblPhone = new JLabel("Numero di telefono: ");
        final Component lblEmail = new JLabel("Email: ");
        final Component lblPassword = new JLabel("Password: ");
        final Component lblPasswordConfirm = new JLabel("Conferma Password: ");
        this.txtName = new JTextField();
        this.txtSurname = new JTextField();
        this.txtPhone = new JTextField();
        this.txtEmail = new JTextField();
        this.txtPassword = new JPasswordField();
        this.txtPasswordConfirm = new JPasswordField();
        this.radParentTypeGroup = new ButtonGroup();
        final AbstractButton btnRegister = new JButton("Register");
        final AbstractButton btnLogin = new JButton("Goto Login");
        // Add all the components.
        this.addPanelComponent(lblName,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(0)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtName,
                new GridBagConstraintBuilder()
                        .setRow(0).setColumn(4)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblSurname,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(0)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtSurname,
                new GridBagConstraintBuilder()
                        .setRow(1).setColumn(4)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblPhone,
                new GridBagConstraintBuilder()
                        .setRow(2).setColumn(0)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtPhone,
                new GridBagConstraintBuilder()
                        .setRow(2).setColumn(4)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblEmail,
                new GridBagConstraintBuilder()
                        .setRow(3).setColumn(0)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtEmail,
                new GridBagConstraintBuilder()
                        .setRow(3).setColumn(4)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblPassword,
                new GridBagConstraintBuilder()
                        .setRow(4).setColumn(0)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtPassword,
                new GridBagConstraintBuilder()
                        .setRow(4).setColumn(4)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(lblPasswordConfirm,
                new GridBagConstraintBuilder()
                        .setRow(5).setColumn(0)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(txtPasswordConfirm,
                new GridBagConstraintBuilder()
                        .setRow(5).setColumn(4)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        // Add the parent type radio buttons
        AtomicInteger index = new AtomicInteger(0);
        EnumSet.allOf(ParentType.class).forEach(p -> {
            final AbstractButton radio = new JRadioButton(p.getTextValue());
            radio.setSelected(true);
            this.radParentTypeGroup.add(radio);
            this.addPanelComponent(radio,
                    new GridBagConstraintBuilder()
                            .setRow(6).setColumn(index.get())
                            .setFillAll()
                            .build()
            );
            index.addAndGet(1);
        });
        this.addPanelComponent(btnRegister,
                new GridBagConstraintBuilder()
                        .setRow(7).setColumn(0)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(btnLogin,
                new GridBagConstraintBuilder()
                        .setRow(7).setColumn(4)
                        .setWidth(4)
                        .setFillAll()
                        .build()
        );
        // Add events
        btnLogin.addActionListener(e -> {
            final JFrame loginPage = new LoginPage("SunFun Register", CloseEvents.EXIT_PROGRAM);
            loginPage.setVisible(true);
            RegisterPage.this.dispose();
        });
        btnRegister.addActionListener(e -> {
            if (RegisterPage.this.isDataValid()) {
                final JFrame loginPage = new LoginPage("SunFun Register", CloseEvents.EXIT_PROGRAM);
                loginPage.setVisible(true);
                RegisterPage.this.dispose();
            }
        });
        // Finish the window.
        this.buildWindow();
    }

    private boolean isDataValid() {
        return false;
    }
}
