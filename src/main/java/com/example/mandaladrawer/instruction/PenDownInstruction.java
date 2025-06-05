package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.DrawingManager;
import javafx.animation.Timeline;

public class PenDownInstruction extends Instruction {
    @Override
    public void execute(DrawingManager manager) {
        manager.lowerPen();
    }

    @Override
    public Timeline createTimeline(DrawingManager manager) {
        return null;
    }
}
