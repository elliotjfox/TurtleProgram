package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;

public class PenDownInstruction extends Instruction {
    @Override
    public void execute(DrawingManager manager) {
        manager.lowerPen();
    }

    @Override
    public Animation createAnimation(DrawingManager manager) {
        return null;
    }
}
