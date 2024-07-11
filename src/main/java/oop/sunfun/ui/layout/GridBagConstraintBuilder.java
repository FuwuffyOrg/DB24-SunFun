package oop.sunfun.ui.layout;

import oop.sunfun.util.Pair;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public final class GridBagConstraintBuilder implements IGridBagConstraintBuilder {
    /**
     * The base weight all elements will have.
     */
    public static final double BASE_WEIGHT = 0.05d;

    /**
     * Holds the position (in the grid) of the element.
     */
    private Pair<Integer, Integer> position;

    /**
     * Holds the weight of the position of the element.
     */
    private Pair<Double, Double> weights;

    /**
     * Holds the dimensions of the element in grid cells.
     */
    private Pair<Integer, Integer> dimensions;

    /**
     * Holds the padding value within the element itself.
     */
    private Pair<Integer, Integer> padding;

    /**
     * Flag to check whether the element will take up all the space, and how.
     */
    private int fill;

    /**
     * Where the base element will be connected to.
     */
    private Anchors anchor;

    /**
     * Provides the margin from the other elements.
     */
    private final Insets inset;

    /**
     * Constructs a new builder with base values.
     */
    public GridBagConstraintBuilder() {
        this.position = new Pair<>(0, 0);
        this.dimensions = new Pair<>(1, 1);
        this.weights = new Pair<>(BASE_WEIGHT, BASE_WEIGHT);
        this.padding = new Pair<>(0, 0);
        this.anchor = Anchors.CENTER;
        this.inset = new Insets(0, 0, 0, 0);
    }

    @Override
    public IGridBagConstraintBuilder setRow(final int row) {
        this.position = new Pair<>(this.position.x(), row);
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setColumn(final int column) {
        this.position = new Pair<>(column, this.position.y());
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setWeightRow(final double weight) {
        this.weights = new Pair<>(this.weights.x(), weight);
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setWeightColumn(final double weight) {
        this.weights = new Pair<>(weight, this.weights.y());
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setWidth(final int width) {
        this.dimensions = new Pair<>(width, this.dimensions.y());
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setHeight(final int height) {
        this.dimensions = new Pair<>(this.dimensions.x(), height);
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setAnchor(final Anchors anchorIn) {
        this.anchor = anchorIn;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setMarginTop(final int pad) {
        this.inset.top = pad;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setMarginLeft(final int pad) {
        this.inset.left = pad;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setMarginBottom(final int pad) {
        this.inset.bottom = pad;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setMarginRight(final int pad) {
        this.inset.right = pad;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setMarginVertical(final int pad) {
        return this.setMarginVertical(pad, pad);
    }

    @Override
    public IGridBagConstraintBuilder setMarginVertical(final int padTop, final int padBottom) {
        return this.setMarginTop(padTop).setMarginBottom(padBottom);
    }

    @Override
    public IGridBagConstraintBuilder setMarginHorizontal(final int pad) {
        return this.setMarginHorizontal(pad, pad);
    }

    @Override
    public IGridBagConstraintBuilder setMarginHorizontal(final int padLeft, final int padRight) {
        return this.setMarginLeft(padLeft).setMarginRight(padRight);
    }

    @Override
    public IGridBagConstraintBuilder setMarginAll(final int pad) {
        return this.setMarginAll(pad, pad, pad, pad);
    }

    @Override
    public IGridBagConstraintBuilder setMarginAll(final int padTop, final int padBottom,
                                                  final int padLeft, final int padRight) {
        return this.setMarginHorizontal(padLeft, padRight).setMarginVertical(padTop, padBottom);
    }

    @Override
    public IGridBagConstraintBuilder setFillNone() {
        this.fill = GridBagConstraints.NONE;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setFillHorizontal() {
        this.fill = GridBagConstraints.HORIZONTAL;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setFillVertical() {
        this.fill = GridBagConstraints.VERTICAL;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setFillAll() {
        this.fill = GridBagConstraints.BOTH;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setPadHorizontal(final int pad) {
        this.padding = new Pair<>(pad, this.padding.y());
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setPadVertical(final int pad) {
        this.padding = new Pair<>(this.padding.x(), pad);
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setPadAll(final int pad) {
        return setPadAll(pad, pad);
    }

    @Override
    public IGridBagConstraintBuilder setPadAll(final int padHorizontal, final int padVertical) {
        return this.setPadHorizontal(padHorizontal).setPadVertical(padVertical);
    }

    @Override
    public GridBagConstraints build() {
        return new GridBagConstraints(
                this.position.x(), this.position.y(), this.dimensions.x(), this.dimensions.y(),
                this.weights.x(), this.weights.y(), this.anchor.getAnchorValue(), this.fill, this.inset,
                this.padding.x(), this.padding.y());
    }
}
