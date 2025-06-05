package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.exception.IllegalTokenException;
import com.example.mandaladrawer.instruction.ForwardInstruction;
import com.example.mandaladrawer.parser.ProgramData;
import com.example.mandaladrawer.parser.ProgramVariable;

import java.util.List;

public class ForwardKeyword extends ProgramMethod {

    public ForwardKeyword() {
        super("forward", 1);
    }

    @Override
    protected void executeMethod(ProgramData data, List<Double> arguments) {
        double distance = arguments.getFirst();

        data.getProgram().addInstructions(new ForwardInstruction(distance));

        double heading = data.getVariables().get("heading").getValue();
        ProgramVariable xPos = data.getVariables().get("xPos");
        ProgramVariable yPos = data.getVariables().get("yPos");

        xPos.setValue(xPos.getValue() + distance * Math.cos(Math.toRadians(heading)));
        yPos.setValue(yPos.getValue() + distance * Math.sin(Math.toRadians(heading)));
    }
}
