package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.DrawingManager;
import javafx.animation.Timeline;

public abstract class Instruction {

    protected boolean movesTurtle;

    public abstract void execute(DrawingManager manager);

    public abstract Timeline createTimeline(DrawingManager manager);

    public double getCost() {
        return 0;
    }

    public boolean movesTurtle() {
        return movesTurtle;
    }
}
