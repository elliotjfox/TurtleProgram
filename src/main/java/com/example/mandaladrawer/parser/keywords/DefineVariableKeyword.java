package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.exception.IllegalTokenException;
import com.example.mandaladrawer.parser.ProgramData;
import com.example.mandaladrawer.parser.ProgramVariable;

public class DefineVariableKeyword extends ProgramKeyword {

    public DefineVariableKeyword() {
        super("var");
    }

    @Override
    public void parse(ProgramData data) {
        String variableName = data.getTokens().removeFirst();

        if (!ProgramData.validateVariableName(variableName)) {
            throw new IllegalTokenException("Illegal variable name, \"" + variableName + "\"");
        }

        if (data.getVariables().containsKey(variableName)) {
            throw new IllegalTokenException("Variable \"" + variableName + "\" is already defined");
        }

        double initialValue = ProgramData.parseExpression(data);
        ProgramVariable variable = new ProgramVariable(variableName, initialValue);

        data.getVariables().add(variable);
    }
}
