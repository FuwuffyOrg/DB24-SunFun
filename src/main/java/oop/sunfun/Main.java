package oop.sunfun;

import com.formdev.flatlaf.FlatDarkLaf;

import oop.sunfun.database.DatabaseConnection;
import oop.sunfun.database.IDatabaseConnection;
import oop.sunfun.ui.LandingPage;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.sql.SQLException;

public final class Main {
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // Use FlatDarkLaf for dark theme, or FlatLightLaf for light theme
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
            final IDatabaseConnection connection = new DatabaseConnection("sunfun", "jdbc:mysql://localhost:3306", "root" ,"");
        try {
            connection.getConnection();
            connection.closeConnection();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        final JFrame page = new LandingPage();
    }
}
