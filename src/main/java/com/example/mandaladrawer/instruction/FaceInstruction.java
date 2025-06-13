package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;
import com.example.mandaladrawer.SettingsManager;
import com.example.mandaladrawer.TurtlePosition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Path;

public class FaceInstruction extends Instruction {

    private final double heading;

    public FaceInstruction(double heading) {
        this.heading = heading;
    }

    @Override
    public void execute(DrawingManager manager, Path path) {
        manager.face(heading);
    }

    @Override
    public Animation createAnimation(DrawingManager manager, Path path) {
        TurtlePosition position = manager.getPosition();
        double initialHeading = position.getHeading();

        DoubleProperty progress = new SimpleDoubleProperty();

        position.headingProperty().bind(Bindings.createDoubleBinding(
                () -> initialHeading + progress.get() * (heading - initialHeading),
                progress
        ));

        return new Animation(progress, 1, Math.abs(heading - initialHeading) * SettingsManager.getRotationAnimationMultiplier());
    }
}
