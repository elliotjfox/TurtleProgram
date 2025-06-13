package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.TurtlePosition;
import com.example.mandaladrawer.instruction.ArcInstruction;
import com.example.mandaladrawer.parser.ProgramData;

import java.util.List;

public class ArcKeyword extends ProgramMethod {

    public ArcKeyword() {
        super("arc", 2);
    }

    @Override
    protected void executeMethod(ProgramData data, List<Double> arguments) {
        double forwardDistance = arguments.getFirst();
        double lateralDistance = arguments.get(1);

        double heading = data.getVariables().get("heading").getValue();
        double xPos = data.getVariables().get("xPos").getValue();
        double yPos = data.getVariables().get("yPos").getValue();

        // TODO: Have width and height stored somewhere
        TurtlePosition initialPosition = new TurtlePosition(xPos, yPos, heading, 200, 200);
        TurtlePosition circleCenter = initialPosition
                .moveForward(forwardDistance / 2)
        // TODO: Should this be -90 or 90?
                .rotate(-90)
                .moveForward(lateralDistance);
        TurtlePosition finalPosition = initialPosition.moveForward(forwardDistance);

        double centerX = circleCenter.getX();
        double centerY = circleCenter.getY();

        double radius = Math.sqrt(Math.pow(xPos - centerX, 2) + Math.pow(yPos - centerY, 2));

        double initialHeading = Math.toDegrees(Math.atan2(
                initialPosition.getY() - circleCenter.getY(),
                initialPosition.getX() - circleCenter.getX()
        ));
        double finalHeading = Math.toDegrees(Math.atan2(
                finalPosition.getY() - circleCenter.getY(),
                finalPosition.getX() - circleCenter.getX()
        ));
        double arcLength = finalHeading - initialHeading;

        System.out.println("centerX: " + centerX);
        System.out.println("centerY: " + centerY);
        System.out.println("radius: " + radius);
        System.out.println("initialHeading: " + initialHeading);
        System.out.println("arcLength: " + arcLength);
        data.getProgram().addInstructions(new ArcInstruction(circleCenter, radius, initialHeading, arcLength));

        data.getVariables().get("xPos").setValue(finalPosition.getX());
        data.getVariables().get("yPos").setValue(finalPosition.getY());
    }
}
