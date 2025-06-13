package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;
import javafx.scene.shape.Path;

public class PenDownInstruction extends Instruction {

    @Override
    public void execute(DrawingManager manager, Path path) {
        manager.lowerPen();
    }

    @Override
    public Animation createAnimation(DrawingManager manager, Path path) {
        return null;
    }
}
