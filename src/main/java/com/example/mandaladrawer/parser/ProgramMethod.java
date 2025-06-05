package com.example.mandaladrawer.parser;

import com.example.mandaladrawer.exception.IncorrectNumberOfArgumentsException;

import java.util.List;

public abstract class ProgramMethod {

    protected String methodName;
    protected int numParameters;

    public ProgramMethod(String methodName) {
        this.methodName = methodName;
    }

    public void parseMethod(MethodArguments arguments) {
        if (arguments.size() != numParameters) {
            throw new IncorrectNumberOfArgumentsException("Expected " + numParameters + " arguments, but received " + arguments.size() + " instead");
        }

        validateArguments(arguments);

        execute(arguments);
    }

    protected abstract void validateArguments(MethodArguments arguments);

    protected abstract void execute(MethodArguments arguments);

}
