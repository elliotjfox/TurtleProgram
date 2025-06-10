package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.DrawingManager;
import com.example.mandaladrawer.SettingsManager;
import com.example.mandaladrawer.TurtlePosition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;

public class GoToInstruction extends Instruction {

    private final double x;
    private final double y;

    public GoToInstruction(double x, double y) {
        this.x = x;
        this.y = y;

        movesTurtle = true;
    }

    @Override
    public void execute(DrawingManager manager) {
        manager.goTo(x, y);

        manager.moved();
    }

    @Override
    public Timeline createTimeline(DrawingManager manager) {
        TurtlePosition position = manager.getPosition();

        // In cartesian
        double initialX = position.getX();
        double initialY = position.getY();

        double distance = Math.sqrt(Math.pow(x - initialX, 2) + Math.pow(y - initialY, 2));

        DoubleProperty xProgress = new SimpleDoubleProperty();
        DoubleProperty yProgress = new SimpleDoubleProperty();

        position.xProperty().bind(Bindings.createDoubleBinding(
                () -> initialX + xProgress.get(),
                xProgress
        ));

        position.yProperty().bind(Bindings.createDoubleBinding(
                () -> initialY + yProgress.get(),
                yProgress
        ));

        return new Timeline(
                new KeyFrame(
                        Duration.millis(
                                distance * SettingsManager.MOVEMENT_ANIMATION_COEFFICIENT * Math.pow(2, -SettingsManager.animationSpeed.getValue()) * Math.pow(2, -SettingsManager.movementAnimationSpeed.getValue())
                        ),
                        new KeyValue(xProgress, x - initialX),
                        new KeyValue(yProgress, y - initialY)
                )
        );
    }
}
