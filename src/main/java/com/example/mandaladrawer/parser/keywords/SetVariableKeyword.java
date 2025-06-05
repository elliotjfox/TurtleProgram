package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.exception.UnknownTokenException;
import com.example.mandaladrawer.parser.ProgramData;

public class SetVariableKeyword extends ProgramKeyword {

    public SetVariableKeyword() {
        super("set");
    }

    @Override
    public void parse(ProgramData data) {
        String variableName = data.getTokens().removeFirst();

        if (data.getVariables().containsKey(variableName)) {
            double newValue = ProgramData.parseExpression(data);
            data.getVariables().get(variableName).setValue(newValue);
        } else {
            throw new UnknownTokenException("Unrecognized variable name, \"" + variableName + "\"");
        }
    }
}
