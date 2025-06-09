package com.example.mandaladrawer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class TurtlePosition {

    private final double width;
    private final double height;

    private final DoubleProperty xPosition;
    private final DoubleProperty layoutXProperty;

    private final DoubleProperty yPosition;
    private final DoubleProperty layoutYProperty;

    private final DoubleProperty headingProperty;

    public TurtlePosition(double x, double y, double heading, double width, double height) {
        this.width = width;
        this.height = height;

        xPosition = new SimpleDoubleProperty(x);
        layoutXProperty = new SimpleDoubleProperty();
        layoutXProperty.bind(xPosition.add(width / 2));

        yPosition = new SimpleDoubleProperty(y);
        layoutYProperty = new SimpleDoubleProperty();
        layoutYProperty.bind(yPosition.multiply(-1).add(height / 2));

        headingProperty = new SimpleDoubleProperty(heading);
    }

    public double getX() {
        return xPosition.get();
    }

    public double getLayoutX() {
        return xPosition.get() + width / 2;
    }

    public double getY() {
        return yPosition.get();
    }

    public double getLayoutY() {
        return height / 2 - yPosition.get();
    }

    public double getHeading() {
        return headingProperty.get();
    }

    public DoubleProperty xProperty() {
        return xPosition;
    }

    public DoubleProperty yProperty() {
        return yPosition;
    }

    public DoubleProperty layoutXProperty() {
        return layoutXProperty;
    }

    public DoubleProperty layoutYProperty() {
        return layoutYProperty;
    }

    public DoubleProperty headingProperty() {
        return headingProperty;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    // TODO: Update math for this, and reduce repetition (also included in ForwardKeyword)
    public TurtlePosition moveForward(double distance) {
        return new TurtlePosition(
                getX() + distance * Math.cos(Math.toRadians(getHeading())),
                getY() + distance * Math.sin(Math.toRadians(getHeading())),
                getHeading(), width, height
        );
    }

    public TurtlePosition rotate(double angle) {
        return new TurtlePosition(
                getX(), getY(),
                getHeading() + angle, width, height
        );
    }

    public TurtlePosition copy() {
        return new TurtlePosition(getX(), getY(), getHeading(), width, height);
    }

//    public TurtlePosition copyLayout() {
//        return new TurtlePosition(getLayoutX(), getLayoutY(), getHeading(), width, height);
//    }
}
