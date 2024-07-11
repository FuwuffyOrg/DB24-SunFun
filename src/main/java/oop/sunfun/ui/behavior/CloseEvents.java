package oop.sunfun.ui.behavior;

import javax.swing.JFrame;

public enum CloseEvents {
    /**
     * The window will hide itself when closed.
     */
    HIDE(JFrame.HIDE_ON_CLOSE),

    /**
     * The window will destroy itself when closed.
     */
    DISPOSE(JFrame.DISPOSE_ON_CLOSE),

    /**
     * The window will close the entire program when closed.
     */
    EXIT_PROGRAM(JFrame.EXIT_ON_CLOSE),

    /**
     * The window will do nothing when closed.
     */
    NOTHING(JFrame.DO_NOTHING_ON_CLOSE);

    /**
     * The close event value from the swing package.
     */
    private final int eventValue;

    CloseEvents(final int value) {
        this.eventValue = value;
    }

    /**
     * Method to get the CloseEvents value for the window in swing terms.
     * @return The value from the JFrame class.
     */
    public int getEventValue() {
        return this.eventValue;
    }
}
