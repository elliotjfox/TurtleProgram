package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.instruction.FaceInstruction;
import com.example.mandaladrawer.parser.ProgramData;

import java.util.List;

public class FaceKeyword extends ProgramMethod {

    public FaceKeyword() {
        super("face", 1);
    }

    @Override
    protected void executeMethod(ProgramData data, List<Double> arguments) {
        double heading = arguments.getFirst();

        data.getProgram().addInstructions(new FaceInstruction(heading));

        data.getVariables().get("heading").setValue(heading);
    }
}
