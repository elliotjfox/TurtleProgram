package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.DrawingManager;
import javafx.animation.Timeline;

public class ForwardInstruction extends Instruction {

    private final double distance;

    public ForwardInstruction(double distance) {
        this.distance = distance;

        movesTurtle = true;
    }

    @Override
    public void execute(DrawingManager manager) {
        manager.moveForward(distance);
    }

    @Override
    public Timeline createTimeline(DrawingManager manager) {
        return null;
    }

    @Override
    public double getCost() {
        return distance;
    }
}
