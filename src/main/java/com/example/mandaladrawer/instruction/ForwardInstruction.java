package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;
import com.example.mandaladrawer.SettingsManager;
import com.example.mandaladrawer.TurtlePosition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ForwardInstruction extends Instruction {

    private final double distance;

    public ForwardInstruction(double distance) {
        this.distance = distance;

        movesTurtle = true;
    }

    @Override
    public void execute(DrawingManager manager) {
        manager.moveForward(distance);

        manager.moved();
    }

    @Override
    public Animation createAnimation(DrawingManager manager) {
        TurtlePosition position = manager.getPosition();
        double initialX = position.getX();
        double initialY = position.getY();
        double heading = position.getHeading();

        DoubleProperty progress = new SimpleDoubleProperty();

        position.xProperty().bind(Bindings.createDoubleBinding(
                () -> initialX + progress.get() * Math.cos(Math.toRadians(heading)),
                progress
        ));

        position.yProperty().bind(Bindings.createDoubleBinding(
                () -> initialY + progress.get() * Math.sin(Math.toRadians(heading)),
                progress
        ));

        return new Animation(progress, distance, Math.abs(distance) * SettingsManager.getMovementAnimationMultiplier());
    }

    @Override
    public double getCost() {
        return distance;
    }
}
