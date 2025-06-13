package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;
import com.example.mandaladrawer.SettingsManager;
import com.example.mandaladrawer.TurtlePosition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class ArcInstruction extends Instruction {

    private final double forwardDistance;
    private final double radius;
    private final boolean largeArc;
    private final boolean sweep;

    public ArcInstruction(double forwardDistance, double radius, boolean largeArc, boolean sweep) {
        this.forwardDistance = forwardDistance;
        this.radius = radius;
        this.largeArc = largeArc;
        this.sweep = sweep;
    }

    @Override
    public void execute(DrawingManager manager, Path path) {
        manager.moveForward(forwardDistance);

        if (manager.isPenDown()) {
            ArcTo arc = new ArcTo();
            arc.setRadiusX(radius);
            arc.setRadiusY(radius);
            arc.setX(manager.getPosition().getLayoutX());
            arc.setY(manager.getPosition().getLayoutY());
            arc.setLargeArcFlag(largeArc);
            arc.setSweepFlag(sweep);
            path.getElements().add(arc);
        } else {
            path.getElements().add(new MoveTo(manager.getPosition().getLayoutX(), manager.getPosition().getLayoutY()));
        }
    }

    @Override
    public Animation createAnimation(DrawingManager manager, Path path) {
        TurtlePosition position = manager.getPosition();
        double initialX = position.getX();
        double initialY = position.getY();
        double heading = position.getHeading();
        double finalX = position.getX() + forwardDistance * Math.cos(Math.toRadians(heading));
        double finalY = position.getY() + forwardDistance * Math.sin(Math.toRadians(heading));


        DoubleProperty progress = new SimpleDoubleProperty();

        if (manager.isPenDown()) {
            ArcTo arc = new ArcTo();
            arc.setRadiusX(radius);
            arc.setRadiusY(radius);
            arc.setLargeArcFlag(largeArc);
            arc.setSweepFlag(sweep);
            arc.xProperty().bind(position.layoutXProperty());
            arc.yProperty().bind(position.layoutYProperty());
            path.getElements().add(arc);
        } else {
            MoveTo moveTo = new MoveTo();
            moveTo.xProperty().bind(position.layoutXProperty());
            moveTo.yProperty().bind(position.layoutYProperty());
            path.getElements().add(moveTo);
        }

        // TODO: Make this curve around
        position.xProperty().bind(Bindings.createDoubleBinding(
                () -> initialX + progress.get() * Math.cos(Math.toRadians(heading)),
                progress
        ));

        position.yProperty().bind(Bindings.createDoubleBinding(
                () -> initialY + progress.get() * Math.sin(Math.toRadians(heading)),
                progress
        ));

        return new Animation(progress, forwardDistance, Math.abs(forwardDistance) * SettingsManager.getMovementAnimationMultiplier());
    }
}
