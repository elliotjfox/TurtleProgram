package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.instruction.RotateInstruction;
import com.example.mandaladrawer.parser.ProgramData;
import com.example.mandaladrawer.parser.ProgramVariable;

import java.util.List;

public class RotateKeyword extends ProgramMethod {

    public RotateKeyword() {
        super("rotate", 1);
    }

    @Override
    protected void executeMethod(ProgramData data, List<Double> arguments) {
        data.getProgram().addInstructions(new RotateInstruction(arguments.getFirst()));

        // Also update the heading variable
        ProgramVariable heading = data.getVariables().get("heading");
        heading.setValue(heading.getValue() + arguments.getFirst());
    }
}
