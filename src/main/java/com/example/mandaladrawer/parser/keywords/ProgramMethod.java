package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.exception.IllegalTokenException;
import com.example.mandaladrawer.parser.ProgramData;

import java.util.List;

public abstract class ProgramMethod extends ProgramKeyword {

    // Number of parameters the method has, -1 (or any negative number) for any number of parameters
    private final int numParameter;

    public ProgramMethod(String name, int numParameter) {
        super(name);

        this.numParameter = numParameter;
    }

    @Override
    public void parse(ProgramData data) {
        List<Double> arguments = ProgramData.parseArguments(data);

        if (numParameter >= 0 && arguments.size() != numParameter) {
            throw new IllegalTokenException(
                    "Expected " + numParameter + (numParameter == 1 ? "argument" : "arguments") + ", but got " + arguments.size() +
                    " (" + arguments + ")"
            );
        }

//        System.out.println(getClass().getSimpleName() + ": " + arguments);

        executeMethod(data, arguments);
    }

    protected abstract void executeMethod(ProgramData data, List<Double> arguments);
}
