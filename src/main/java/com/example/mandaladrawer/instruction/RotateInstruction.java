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

public class RotateInstruction extends Instruction {

    private final double angle;

    public RotateInstruction(double angle) {
        this.angle = angle;
        movesTurtle = true;
    }

    @Override
    public void execute(DrawingManager manager) {
        manager.rotate(angle);

        manager.updatePosition();
    }

    @Override
    public Timeline createTimeline(DrawingManager manager) {
        TurtlePosition position = manager.getPosition();
        double initialHeading = position.getHeading();

        DoubleProperty progress = new SimpleDoubleProperty();

        position.headingProperty().bind(Bindings.createDoubleBinding(
                () -> initialHeading + progress.get(),
                progress
        ));

        return new Timeline(
                new KeyFrame(
                        Duration.millis(
                                Math.abs(angle) * SettingsManager.ROTATION_ANIMATION_COEFFICIENT * Math.pow(2, -SettingsManager.animationSpeed.getValue()) * Math.pow(2, -SettingsManager.rotationAnimationSpeed.getValue())
                        ),
                        new KeyValue(progress, angle)
                )
        );
    }

    @Override
    public double getCost() {
        return angle;
    }
}
