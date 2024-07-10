package oop.sunfun.ui;

import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

public class LandingPage extends GenericPage {
    private static final String WINDOW_TITLE = "SunFun Landing Page";
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 900;

    public LandingPage() {
        super(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT);
        // Create the grid layout
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        IntStream.range(0, 3).forEach(i -> {
            IntStream.range(0, 3).forEach(j -> {
                panel.add(new JButton("test_" + i + "_" + j));
            });
        });
        this.setContentPane(panel);
        // Display the window
        this.setVisible(true);
    }
}
