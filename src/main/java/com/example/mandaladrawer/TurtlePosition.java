package com.example.mandaladrawer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class TurtlePosition {

    private final DoubleProperty xPosition;
    private final DoubleProperty yPosition;
    private final DoubleProperty headingProperty;

    private final double x;
    private final double y;
    private final double heading;

    private final double width;
    private final double height;

    public TurtlePosition(double x, double y, double heading, double width, double height) {
        this.x = x;
        this.y = y;
        this.heading = heading;

        xPosition = new SimpleDoubleProperty(x);
        yPosition = new SimpleDoubleProperty(y);
        headingProperty = new SimpleDoubleProperty(heading);

        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getLayoutX() {
        return x + width / 2;
    }

    public double getY() {
        return y;
    }

    public double getLayoutY() {
        return height / 2 - y;
    }

    public double getHeading() {
        return heading;
    }

    // TODO: Update math for this, and reduce repetition (also included in ForwardKeyword)
    public TurtlePosition moveForward(double distance) {
        return new TurtlePosition(x + distance * Math.cos(Math.toRadians(heading)), y + distance * Math.sin(Math.toRadians(heading)), heading, width, height);
    }

    public TurtlePosition rotate(double angle) {
        return new TurtlePosition(x, y, heading + angle, width, height);
    }

    public TurtlePosition copy() {
        return new TurtlePosition(x, y, heading, width, height);
    }

    public DoubleProperty xProperty() {
        return xPosition;
    }

    public DoubleProperty yProperty() {
        return yPosition;
    }

    public DoubleProperty headingProperty() {
        return headingProperty;
    }
}
