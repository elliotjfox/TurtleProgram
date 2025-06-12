package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;

public abstract class Instruction {

    protected boolean movesTurtle;

    public abstract void execute(DrawingManager manager);

    public abstract Animation createAnimation(DrawingManager manager);

    public double getCost() {
        return 0;
    }

    public boolean movesTurtle() {
        return movesTurtle;
    }
}
