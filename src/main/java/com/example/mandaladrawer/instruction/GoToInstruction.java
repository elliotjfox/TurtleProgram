package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;
import com.example.mandaladrawer.SettingsManager;
import com.example.mandaladrawer.TurtlePosition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class GoToInstruction extends Instruction {

    private final double x;
    private final double y;

    public GoToInstruction(double x, double y) {
        this.x = x;
        this.y = y;

        movesTurtle = true;
    }

    @Override
    public void execute(DrawingManager manager, Path path) {
        manager.goTo(x, y);

        TurtlePosition position = manager.getPosition();
        if (manager.isPenDown()) {
            path.getElements().add(new LineTo(position.getLayoutX(), position.getLayoutY()));
        } else {
            path.getElements().add(new MoveTo(position.getLayoutX(), position.getLayoutY()));
        }
    }

    @Override
    public Animation createAnimation(DrawingManager manager, Path path) {
        TurtlePosition position = manager.getPosition();
        double initialX = position.getX();
        double initialY = position.getY();
        double distance = Math.sqrt(Math.pow(x - initialX, 2) + Math.pow(y - initialY, 2));

        DoubleProperty progress = new SimpleDoubleProperty();

        if (manager.isPenDown()) {
            LineTo lineTo = new LineTo();
            lineTo.xProperty().bind(position.layoutXProperty());
            lineTo.yProperty().bind(position.layoutYProperty());
            path.getElements().add(lineTo);
        } else {
            MoveTo moveTo = new MoveTo();
            moveTo.xProperty().bind(position.layoutXProperty());
            moveTo.yProperty().bind(position.layoutYProperty());
            path.getElements().add(moveTo);
        }

        position.xProperty().bind(Bindings.createDoubleBinding(
                () -> initialX + progress.get() * (x - initialX),
                progress
        ));

        position.yProperty().bind(Bindings.createDoubleBinding(
                () -> initialY + progress.get() * (y - initialY),
                progress
        ));

        return new Animation(progress, 1, Math.abs(distance) * SettingsManager.getMovementAnimationMultiplier());
    }
}
