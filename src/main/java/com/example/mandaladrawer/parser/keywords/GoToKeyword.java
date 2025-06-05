package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.instruction.GoToInstruction;
import com.example.mandaladrawer.parser.ProgramData;

import java.util.List;

public class GoToKeyword extends ProgramMethod {

    public GoToKeyword() {
        super("goto", 2);
    }

    @Override
    protected void executeMethod(ProgramData data, List<Double> arguments) {
        double xPos = arguments.getFirst();
        double yPos = arguments.get(1);

        data.getProgram().addInstructions(new GoToInstruction(xPos, yPos));

        data.getVariables().get("xPos").setValue(xPos);
        data.getVariables().get("yPos").setValue(yPos);
    }
}
