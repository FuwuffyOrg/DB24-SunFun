package oop.sunfun;

import com.formdev.flatlaf.FlatDarkLaf;

import oop.sunfun.ui.LandingPage;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public final class Main {
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // Use FlatDarkLaf for dark theme, or FlatLightLaf for light theme
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        final JFrame page = new LandingPage();
    }
}
