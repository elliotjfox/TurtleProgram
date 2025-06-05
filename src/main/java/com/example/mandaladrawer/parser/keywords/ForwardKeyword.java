package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.instruction.ForwardInstruction;
import com.example.mandaladrawer.parser.ProgramData;

public class ForwardKeyword extends ProgramKeyword {

    public ForwardKeyword() {
        super("forward");
    }

    @Override
    public void parse(ProgramData data) {
        double argument = ProgramData.parseExpression(data);
        data.getProgram().addInstructions(new ForwardInstruction(argument));
    }
}
