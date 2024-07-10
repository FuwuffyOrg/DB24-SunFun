package oop.sunfun.ui.layout;

import java.awt.GridBagConstraints;

public interface IGridBagConstraintBuilder {
    IGridBagConstraintBuilder setRow(int row);

    IGridBagConstraintBuilder setColumn(int column);

    IGridBagConstraintBuilder setWeightRow(double weight);

    IGridBagConstraintBuilder setWeightColumn(double weight);

    IGridBagConstraintBuilder setWidth(int width);

    IGridBagConstraintBuilder setHeight(int height);

    IGridBagConstraintBuilder setAnchor(Anchors anchor);

    IGridBagConstraintBuilder setMarginTop(int margin);

    IGridBagConstraintBuilder setMarginLeft(int margin);

    IGridBagConstraintBuilder setMarginBottom(int margin);

    IGridBagConstraintBuilder setMarginRight(int margin);

    IGridBagConstraintBuilder setMarginVertical(int margin);

    IGridBagConstraintBuilder setMarginVertical(int marginTop, int marginBottom);

    IGridBagConstraintBuilder setMarginHorizontal(int margin);

    IGridBagConstraintBuilder setMarginHorizontal(int marginLeft, int marginRight);

    IGridBagConstraintBuilder setMarginAll(int margin);

    IGridBagConstraintBuilder setMarginAll(int marginTop, int marginBottom, int marginLeft, int marginRight);

    IGridBagConstraintBuilder setFillNone();

    IGridBagConstraintBuilder setFillHorizontal();

    IGridBagConstraintBuilder setFillVertical();

    IGridBagConstraintBuilder setFillAll();

    IGridBagConstraintBuilder setPadHorizontal(int pad);

    IGridBagConstraintBuilder setPadVertical(int pad);

    IGridBagConstraintBuilder setPadAll(int pad);

    IGridBagConstraintBuilder setPadAll(int padHorizontal, int padVertical);

    GridBagConstraints build();
}
