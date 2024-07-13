package oop.sunfun.ui.util.layout;

import java.awt.GridBagConstraints;

public enum Anchors {
    /**
     * Anchors at the top left side of the screen.
     */
    TOP_LEFT(GridBagConstraints.FIRST_LINE_START),

    /**
     * Anchors at the top center side of the screen.
     */
    TOP(GridBagConstraints.PAGE_START),

    /**
     * Anchors at the top right side of the screen.
     */
    TOP_RIGHT(GridBagConstraints.FIRST_LINE_END),

    /**
     * Anchors at the center left side of the screen.
     */
    CENTER_LEFT(GridBagConstraints.LINE_START),

    /**
     * Anchors at the center side of the screen.
     */
    CENTER(GridBagConstraints.CENTER),

    /**
     * Anchors at the center right side of the screen.
     */
    CENTER_RIGHT(GridBagConstraints.LINE_END),

    /**
     * Anchors at the bottom left side of the screen.
     */
    BOTTOM_LEFT(GridBagConstraints.LAST_LINE_START),

    /**
     * Anchors at the bottom center side of the screen.
     */
    BOTTOM(GridBagConstraints.PAGE_END),

    /**
     * Anchors at the bottom right side of the screen.
     */
    BOTTOM_RIGHT(GridBagConstraints.LAST_LINE_END);

    /**
     * The anchor value from the GridBagConstraints class.
     */
    private final int anchorValue;

    Anchors(final int value) {
        this.anchorValue = value;
    }

    /**
     * Method to get the anchor value for the layout from the enum.
     * @return The value from the GridBagConstraints class.
     */
    public int getAnchorValue() {
        return  this.anchorValue;
    }
}
