package com.example.mandaladrawer.instruction;

import com.example.mandaladrawer.Animation;
import com.example.mandaladrawer.DrawingManager;
import com.example.mandaladrawer.TurtlePosition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;

public class ArcInstruction extends Instruction {

    private final TurtlePosition centerPosition;
    private final double radius;
    private final double initialHeading;
    private final double arcLength;


    public ArcInstruction(TurtlePosition centerPosition, double radius, double initialHeading, double arcLength) {
        this.centerPosition = centerPosition;
        this.radius = radius;
        this.initialHeading = initialHeading;
        this.arcLength = arcLength;
    }

    @Override
    public void execute(DrawingManager manager) {
        manager.moveForward(radius * 2);

//        ArcTo arc = new ArcTo();
//        arc.setX(centerPosition.getLayoutX());
//        arc.setY(centerPosition.getLayoutY());
//        arc.setRadiusX(radius);
//        arc.setRadiusY(radius);
//        arc.setStartAngle(initialHeading);
//        arc.setLength(arcLength);
//        arc.setType(ArcType.CHORD);

        manager.updatePosition();
//        manager.placeGraphic(arc);
    }

    @Override
    public Animation createAnimation(DrawingManager manager) {
        return null;
    }
}
