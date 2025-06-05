package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.instruction.RotateInstruction;
import com.example.mandaladrawer.parser.ProgramData;

public class RotateKeyword extends ProgramKeyword {

    public RotateKeyword() {
        super("rotate");
    }

    @Override
    public void parse(ProgramData data) {
        double argument = ProgramData.parseExpression(data);
        data.getProgram().addInstructions(new RotateInstruction(argument));
    }
}
