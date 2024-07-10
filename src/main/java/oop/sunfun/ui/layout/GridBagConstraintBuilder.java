package oop.sunfun.ui.layout;

import java.awt.*;

public final class GridBagConstraintBuilder implements IGridBagConstraintBuilder {
    private int row;
    private int column;

    private double weightRow;
    private double weightColumn;

    private int width;
    private int height;

    private int padColumn;
    private int padRow;

    private int fill;
    private Anchors anchor;

    private final Insets inset;

    public GridBagConstraintBuilder() {
        this.row = 0;
        this.column = 0;
        this.width = 1;
        this.height = 1;
        this.weightRow = 0.05d;
        this.weightColumn = 0.05d;
        this.padColumn = 0;
        this.padRow = 0;
        this.anchor = Anchors.CENTER;
        this.inset = new Insets(0, 0, 0, 0);
    }

    @Override
    public IGridBagConstraintBuilder setRow(final int row) {
        this.row = row;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setColumn(final int column) {
        this.column = column;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setWeightRow(final double weight) {
        this.weightRow = weight;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setWeightColumn(final double weight) {
        this.weightColumn = weight;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setWidth(final int width) {
        this.width = width;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setHeight(final int height) {
        this.height = height;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setAnchor(final Anchors anchor) {
        this.anchor = anchor;
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
    public IGridBagConstraintBuilder setMarginAll(final int padTop, final int padBottom, final int padLeft, final int padRight) {
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
        this.padColumn = pad;
        return this;
    }

    @Override
    public IGridBagConstraintBuilder setPadVertical(final int pad) {
        this.padRow = pad;
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
                this.column, this.row, this.width,
                this.height, this.weightColumn, this.weightRow,
                this.anchor.getAnchorValue(), this.fill, this.inset,
                this.padColumn, this.padRow);
    }
}
