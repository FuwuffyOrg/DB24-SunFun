package oop.sunfun.ui;

import oop.sunfun.ui.behavior.CloseEvents;
import oop.sunfun.ui.layout.GenericPage;
import oop.sunfun.ui.layout.GridBagConstraintBuilder;

import javax.swing.*;

import java.awt.Component;

public final class RegisterPage extends GenericPage {

    public RegisterPage(final String title, final CloseEvents closeEvent) {
        super(title, closeEvent);
        // Add two labels and text boxes for inputting username and password.
        final Component lblName = new JLabel("Nome: ");
        final Component lblSurname = new JLabel("Cognome: ");
        final Component lblPhone = new JLabel("Numero di telefono: ");
        final Component lblEmail = new JLabel("Email: ");
        final Component lblPassword = new JLabel("Password: ");
        final Component lblPasswordConfirm = new JLabel("Conferma Password: ");
        final Component txtName = new JTextField();
        final Component txtSurname = new JTextField();
        final Component txtPhone = new JTextField();
        final Component txtEmail = new JTextField();
        final Component txtPassword = new JPasswordField();
        final Component txtPasswordConfirm = new JPasswordField();
        final AbstractButton btnRegister = new JButton("Register");
        final AbstractButton btnLogin = new JButton("Goto Login");
        // Add the parent type radio buttons
        final ButtonGroup parentTypeGroup = new ButtonGroup();
        final AbstractButton radPadre = new JRadioButton("Padre");
        radPadre.setSelected(true);
        final AbstractButton radMadre = new JRadioButton("Madre");
        final AbstractButton radNonno = new JRadioButton("Nonno");
        final AbstractButton radNonna = new JRadioButton("Nonna");
        final AbstractButton radZio = new JRadioButton("Zio");
        final AbstractButton radZia = new JRadioButton("Zia");
        final AbstractButton radFratello = new JRadioButton("Fratello");
        final AbstractButton radSorella = new JRadioButton("Sorella");
        parentTypeGroup.add(radPadre);
        parentTypeGroup.add(radMadre);
        parentTypeGroup.add(radNonno);
        parentTypeGroup.add(radNonna);
        parentTypeGroup.add(radZio);
        parentTypeGroup.add(radZia);
        parentTypeGroup.add(radFratello);
        parentTypeGroup.add(radSorella);
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
        this.addPanelComponent(radPadre,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(0)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(radMadre,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(1)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(radNonno,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(2)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(radNonna,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(3)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(radZio,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(4)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(radZia,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(5)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(radFratello,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(6)
                        .setFillAll()
                        .build()
        );
        this.addPanelComponent(radSorella,
                new GridBagConstraintBuilder()
                        .setRow(6).setColumn(7)
                        .setFillAll()
                        .build()
        );
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
            final JFrame loginPage = new LoginPage("SunFun Register", CloseEvents.EXIT_PROGRAM);
            loginPage.setVisible(true);
            RegisterPage.this.dispose();
        });
        // Finish the window.
        this.buildWindow();
    }
}
