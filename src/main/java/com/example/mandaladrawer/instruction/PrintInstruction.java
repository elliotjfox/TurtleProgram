package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;
import javafx.scene.shape.Path;

public class PrintInstruction extends Instruction {

    private final String message;

    public PrintInstruction(String message) {
        this.message = message;
    }

    @Override
    public void execute(DrawingManager manager, Path path) {
        System.out.println(message);
    }

    @Override
    public Animation createAnimation(DrawingManager manager, Path path) {
        return null;
    }
}
