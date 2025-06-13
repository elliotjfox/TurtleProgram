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
        double radius = arguments.get(1);

        double heading = data.getVariables().get("heading").getValue();
        double xPos = data.getVariables().get("xPos").getValue();
        double yPos = data.getVariables().get("yPos").getValue();

        // TODO: Have width and height stored somewhere
        TurtlePosition finalPosition = new TurtlePosition(xPos, yPos, heading, 200, 200).moveForward(forwardDistance);

        // TODO: Have better arguments
        data.getProgram().addInstructions(new ArcInstruction(forwardDistance, radius, false, false));

        data.getVariables().get("xPos").setValue(finalPosition.getX());
        data.getVariables().get("yPos").setValue(finalPosition.getY());
    }
}
