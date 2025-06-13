package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;
import javafx.scene.shape.Path;

public class PenUpInstruction extends Instruction {

    @Override
    public void execute(DrawingManager manager, Path path) {
        manager.raisePen();
    }

    @Override
    public Animation createAnimation(DrawingManager manager, Path path) {
        return null;
    }
}
