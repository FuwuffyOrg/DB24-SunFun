package oop.sunfun.ui.behavior;

import javax.swing.*;
import java.util.EnumMap;
import java.util.Map;

public enum CloseEvents {
    /**
     * The window will hide itself when closed.
     */
    HIDE,

    /**
     * The window will destroy itself when closed.
     */
    DISPOSE,

    /**
     * The window will close the entire program when closed.
     */
    EXIT_PROGRAM,

    /**
     * The window will do nothing when closed.
     */
    NOTHING;

    /**
     * Map for quick lookup from CloseEvents to proper swing value.
     */
    private static final Map<CloseEvents, Integer> EVENT_VALUES = new EnumMap<>(CloseEvents.class);

    static {
        EVENT_VALUES.put(HIDE, JFrame.HIDE_ON_CLOSE);
        EVENT_VALUES.put(DISPOSE, JFrame.DISPOSE_ON_CLOSE);
        EVENT_VALUES.put(EXIT_PROGRAM, JFrame.EXIT_ON_CLOSE);
        EVENT_VALUES.put(NOTHING, JFrame.DO_NOTHING_ON_CLOSE);
    }

    /**
     * Method to get the CloseEvents value for the window in swing terms.
     * @return The value from the JFrame class.
     */
    public int getEventValue() {
        final Integer value = EVENT_VALUES.get(this);
        if (value == null) {
            throw new IllegalStateException("Close event value does not exist.");
        }
        return value;
    }
}
