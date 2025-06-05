package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.instruction.PrintInstruction;
import com.example.mandaladrawer.parser.ProgramData;

import java.util.Iterator;
import java.util.List;

public class PrintKeyword extends ProgramMethod {

    public PrintKeyword() {
        super("print", -1);
    }

    @Override
    protected void executeMethod(ProgramData data, List<Double> arguments) {
        StringBuilder builder = new StringBuilder();

        Iterator<Double> it = arguments.iterator();

        while (it.hasNext()) {
            builder.append(it.next());
            if (it.hasNext()) builder.append(", ");
        }

        System.out.println("Output: " + builder);
    }
}
