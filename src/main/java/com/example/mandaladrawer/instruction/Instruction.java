package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;
import javafx.scene.shape.Path;

public abstract class Instruction {

    protected boolean movesTurtle;

    public abstract void execute(DrawingManager manager, Path path);

    public abstract Animation createAnimation(DrawingManager manager, Path path);

    public double getCost() {
        return 0;
    }

    public boolean movesTurtle() {
        return movesTurtle;
    }
}
