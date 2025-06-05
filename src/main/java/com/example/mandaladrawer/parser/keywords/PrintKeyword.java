package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.instruction.PrintInstruction;
import com.example.mandaladrawer.parser.ProgramData;

public class PrintKeyword extends ProgramKeyword {

    public PrintKeyword() {
        super("print");
    }

    @Override
    public void parse(ProgramData data) {
        double value = ProgramData.parseExpression(data);
        data.getProgram().addInstructions(new PrintInstruction("Output: " + value));
    }
}
