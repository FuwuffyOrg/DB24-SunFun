package oop.sunfun.ui;

import javax.swing.*;
import java.awt.event.ComponentAdapter;

public abstract class GenericPage extends JFrame {

    public GenericPage(final String title, final int width, final int height) {
        // Add a close operation
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Sets the size of the window
        this.setSize(width, height);
        // Set the window title
        this.setTitle(title);
        // Center the window to the screen
        this.setLocationRelativeTo(null);
    }
}
