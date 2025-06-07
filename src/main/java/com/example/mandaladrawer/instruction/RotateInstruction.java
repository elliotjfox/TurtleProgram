package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.DrawingManager;
import javafx.animation.Timeline;

public class RotateInstruction extends Instruction {

    private final double angle;

    public RotateInstruction(double angle) {
        this.angle = angle;
        movesTurtle = true;
    }

    @Override
    public void execute(DrawingManager manager) {
        manager.rotate(angle);

        manager.updatePosition();
    }

    @Override
    public Timeline createTimeline(DrawingManager manager) {
        return null;
    }

    @Override
    public double getCost() {
        return angle;
    }
}
