package oop.sunfun.ui.util.layout;

import java.awt.GridBagConstraints;

public interface IGridBagConstraintBuilder {
    /**
     * Sets the row index within the layout.
     * @param row The row to set the value to.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setRow(int row);

    /**
     * Sets the column index within the layout.
     * @param column The column to set the value to.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setColumn(int column);

    /**
     * Sets the weight value for the row within the layout to space around stuff.
     * @param weight The weight to set the value to.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setWeightRow(double weight);

    /**
     * Sets the weight value for the column within the layout to space around stuff.
     * @param weight The weight to set the value to.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setWeightColumn(double weight);

    /**
     * Sets the width (in columns) of the element to display.
     * @param width The column count to set the value to.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setWidth(int width);

    /**
     * Sets the height (in rows) of the element to display.
     * @param height The row count to set the value to.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setHeight(int height);

    /**
     * Sets the margin from the top from the other elements of the element to display.
     * @param margin The amount of the margin to set.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setMarginTop(int margin);

    /**
     * Sets the margin from the left from the other elements of the element to display.
     * @param margin The amount of the margin to set.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setMarginLeft(int margin);

    /**
     * Sets the margin from the bottom from the other elements of the element to display.
     * @param margin The amount of the margin to set.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setMarginBottom(int margin);

    /**
     * Sets the margin from the right from the other elements of the element to display.
     * @param margin The amount of the margin to set.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setMarginRight(int margin);

    /**
     * Sets the vertical margin from the other elements of the element to display.
     * @param margin The amount of the margin to set.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setMarginVertical(int margin);

    /**
     * Sets the vertical margin from the other elements of the element to display.
     * @param marginTop The amount of the margin to set for the top side.
     * @param marginBottom The amount of the margin to set for the bottom side.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setMarginVertical(int marginTop, int marginBottom);

    /**
     * Sets the horizontal margin from the other elements of the element to display.
     * @param margin The amount of the margin to set.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setMarginHorizontal(int margin);

    /**
     * Sets the horizontal margin from the other elements of the element to display.
     * @param marginLeft The amount of the margin to set from the left.
     * @param marginRight The amount of the margin to set from the right.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setMarginHorizontal(int marginLeft, int marginRight);

    /**
     * Sets all the margins from the other elements of the element to display.
     * @param margin The amount of the margin to set.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setMarginAll(int margin);

    /**
     * Sets all the margins from the other elements of the element to display.
     * @param marginTop The amount of the margin to set for the top side.
     * @param marginBottom The amount of the margin to set for the bottom side.
     * @param marginLeft The amount of the margin to set from the left.
     * @param marginRight The amount of the margin to set from the right.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setMarginAll(int marginTop, int marginBottom, int marginLeft, int marginRight);

    /**
     * Sets the element not to take up all the space available.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setFillNone();

    /**
     * Sets the element to only take up the horizontal available space.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setFillHorizontal();

    /**
     * Sets the element to only take up the vertical available space.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setFillVertical();

    /**
     * Sets the element to only take up all the available space.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setFillAll();

    /**
     * Sets the element's horizontal padding (the space within the element itself).
     * @param pad The amount to set the padding to.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setPadHorizontal(int pad);

    /**
     * Sets the element's vertical padding (the space within the element itself).
     * @param pad The amount to set the padding to.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setPadVertical(int pad);

    /**
     * Sets all the element's padding values (the space within the element itself).
     * @param pad The amount to set the padding to.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setPadAll(int pad);

    /**
     * Sets all the element's padding values (the space within the element itself).
     * @param padHorizontal The amount to set the padding to on the horizontal axis.
     * @param padVertical The amount to set the padding to on the vertical axis.
     * @return The current builder.
     */
    IGridBagConstraintBuilder setPadAll(int padHorizontal, int padVertical);

    /**
     * Completes the building process of the GridBagConstraint to use in the layout.
     * @return The GridBagConstraint requested.
     */
    GridBagConstraints build();
}
