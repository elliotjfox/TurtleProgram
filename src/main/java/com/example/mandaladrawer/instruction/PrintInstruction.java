package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;

public class PrintInstruction extends Instruction {

    private final String message;

    public PrintInstruction(String message) {
        this.message = message;
    }

    @Override
    public void execute(DrawingManager manager) {
        System.out.println(message);
    }

    @Override
    public Animation createAnimation(DrawingManager manager) {
        return null;
    }
}
