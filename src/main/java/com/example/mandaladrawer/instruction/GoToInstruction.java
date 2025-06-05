package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.DrawingManager;
import com.example.mandaladrawer.parser.keywords.GoToKeyword;
import javafx.animation.Timeline;

public class GoToInstruction extends Instruction {

    private final double x;
    private final double y;

    public GoToInstruction(double x, double y) {
        this.x = x;
        this.y = y;

        movesTurtle = true;
    }

    @Override
    public void execute(DrawingManager manager) {
        manager.goTo(x, y);
    }

    @Override
    public Timeline createTimeline(DrawingManager manager) {
        return null;
    }
}
