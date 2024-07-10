package oop.sunfun.ui.layout;

import java.awt.GridBagConstraints;
import java.util.EnumMap;
import java.util.Map;

public enum Anchors {
    /**
     * Anchors at the top left side of the screen.
     */
    TOP_LEFT,

    /**
     * Anchors at the top center side of the screen.
     */
    TOP,

    /**
     * Anchors at the top right side of the screen.
     */
    TOP_RIGHT,

    /**
     * Anchors at the center left side of the screen.
     */
    CENTER_LEFT,

    /**
     * Anchors at the center side of the screen.
     */
    CENTER,

    /**
     * Anchors at the center right side of the screen.
     */
    CENTER_RIGHT,

    /**
     * Anchors at the bottom left side of the screen.
     */
    BOTTOM_LEFT,

    /**
     * Anchors at the bottom center side of the screen.
     */
    BOTTOM,

    /**
     * Anchors at the bottom right side of the screen.
     */
    BOTTOM_RIGHT;

    /**
     * Map for quick lookup from Anchors enum to value in GridBagConstraints.
     */
    private static final Map<Anchors, Integer> ANCHOR_VALUES = new EnumMap<>(Anchors.class);

    static {
        ANCHOR_VALUES.put(TOP_LEFT, GridBagConstraints.FIRST_LINE_START);
        ANCHOR_VALUES.put(TOP, GridBagConstraints.PAGE_START);
        ANCHOR_VALUES.put(TOP_RIGHT, GridBagConstraints.FIRST_LINE_END);
        ANCHOR_VALUES.put(CENTER_LEFT, GridBagConstraints.LINE_START);
        ANCHOR_VALUES.put(CENTER, GridBagConstraints.CENTER);
        ANCHOR_VALUES.put(CENTER_RIGHT, GridBagConstraints.LINE_END);
        ANCHOR_VALUES.put(BOTTOM_LEFT, GridBagConstraints.LAST_LINE_START);
        ANCHOR_VALUES.put(BOTTOM, GridBagConstraints.PAGE_END);
        ANCHOR_VALUES.put(BOTTOM_RIGHT, GridBagConstraints.LAST_LINE_END);
    }

    /**
     * Method to get the anchor value for the layout from the enum.
     * @return The value from the GridBagConstraints class.
     */
    public int getAnchorValue() {
        final Integer value = ANCHOR_VALUES.get(this);
        if (value == null) {
            throw new IllegalStateException("Anchor value does not exist.");
        }
        return value;
    }
}
