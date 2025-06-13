package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;
import com.example.mandaladrawer.SettingsManager;
import com.example.mandaladrawer.TurtlePosition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Path;

public class RotateInstruction extends Instruction {

    private final double angle;

    public RotateInstruction(double angle) {
        this.angle = angle;
        movesTurtle = true;
    }

    @Override
    public void execute(DrawingManager manager, Path path) {
        manager.rotate(angle);
    }

    @Override
    public Animation createAnimation(DrawingManager manager, Path path) {
        TurtlePosition position = manager.getPosition();
        double initialHeading = position.getHeading();

        DoubleProperty progress = new SimpleDoubleProperty();

        position.headingProperty().bind(Bindings.createDoubleBinding(
                () -> initialHeading + progress.get(),
                progress
        ));

        return new Animation(progress, angle, Math.abs(angle) * SettingsManager.getRotationAnimationMultiplier());
    }

    @Override
    public double getCost() {
        return angle;
    }
}
