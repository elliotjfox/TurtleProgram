package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.DrawingManager;
import javafx.animation.Timeline;

public class PenUpInstruction extends Instruction {

    @Override
    public void execute(DrawingManager manager) {
        manager.raisePen();
    }

    @Override
    public Timeline createTimeline(DrawingManager manager) {
        return null;
    }
}
