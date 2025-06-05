package com.example.mandaladrawer;

public class TurtlePosition {

    private final double x;
    private final double y;
    private final double heading;

    public TurtlePosition(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeading() {
        return heading;
    }

    // TODO: Update math for this, and reduce repetition (also included in ForwardKeyword)
    public TurtlePosition moveForward(double distance) {
        return new TurtlePosition(x + distance * Math.cos(Math.toRadians(heading)), y + distance * Math.sin(Math.toRadians(heading)), heading);
    }

    public TurtlePosition rotate(double angle) {
        return new TurtlePosition(x, y, heading + angle);
    }
}
