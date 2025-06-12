package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;

public class PenUpInstruction extends Instruction {

    @Override
    public void execute(DrawingManager manager) {
        manager.raisePen();
    }

    @Override
    public Animation createAnimation(DrawingManager manager) {
        return null;
    }
}
