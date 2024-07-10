package oop.sunfun.ui.layout;

import java.awt.*;

public enum Anchors {
    TOP_LEFT,
    TOP,
    TOP_RIGHT,
    CENTER_LEFT,
    CENTER,
    CENTER_RIGHT,
    BOTTOM_LEFT,
    BOTTOM,
    BOTTOM_RIGHT;

    public int getAnchorValue() {
        return switch (this) {
            case TOP_LEFT -> GridBagConstraints.FIRST_LINE_START;
            case TOP -> GridBagConstraints.PAGE_START;
            case TOP_RIGHT -> GridBagConstraints.FIRST_LINE_END;
            case CENTER_LEFT -> GridBagConstraints.LINE_START;
            case CENTER -> GridBagConstraints.CENTER;
            case CENTER_RIGHT -> GridBagConstraints.LINE_END;
            case BOTTOM_LEFT -> GridBagConstraints.LAST_LINE_START;
            case BOTTOM -> GridBagConstraints.PAGE_END;
            case BOTTOM_RIGHT -> GridBagConstraints.LAST_LINE_END;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }
}
