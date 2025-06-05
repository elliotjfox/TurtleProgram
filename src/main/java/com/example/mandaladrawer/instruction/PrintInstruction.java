package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.DrawingManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

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
    public Timeline createTimeline(DrawingManager manager) {
        return new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        _ -> System.out.println(message)
                )
        );
    }
}
