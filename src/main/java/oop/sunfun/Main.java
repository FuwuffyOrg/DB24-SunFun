package oop.sunfun;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;
import javax.swing.JFrame;

public final class Main {
    /**
     * The window's initial width.
     */
    private static final int WINDOW_WIDTH = 1200;

    /**
     * The window's initial height.
     */
    private static final int WINDOW_HEIGHT = 800;

    /**
     * Constructor used to block other classes to construct Main.
     */
    private Main() {
        // Useless method.
    }

    /**
     * The entry point of the program.
     * @param args The program's arguments.
     */
    public static void main(final String[] args) {
        FlatDarkLaf.setup();
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (final UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        final JFrame frame = new JFrame("SunFun");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
