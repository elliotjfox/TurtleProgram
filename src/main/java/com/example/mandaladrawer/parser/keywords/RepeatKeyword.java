package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.Program;
import com.example.mandaladrawer.exception.IllegalTokenException;
import com.example.mandaladrawer.exception.UnknownTokenException;
import com.example.mandaladrawer.parser.ProgramData;
import com.example.mandaladrawer.parser.VariableScope;

import java.util.ArrayList;
import java.util.List;

public class RepeatKeyword extends ProgramKeyword {
    public RepeatKeyword() {
        super("repeat");
    }

    @Override
    public void parse(ProgramData data) {
        int argument = (int) ProgramData.parseExpression(data, "{");
        String second = data.getTokens().removeFirst();

        if (argument < 0) {
            throw new IllegalTokenException("Value must be greater or equal to 0, got \"" + argument + "\"");
        }

        if (!second.equals("{")) {
            throw new UnknownTokenException("Expected \"{\", got \"" + second + "\"");
        }

        List<String> subTokenList = getTokensToSameBracketLevel(data.getTokens());

        for (int i = 0; i < argument; i++) {
            ProgramData subProgramData = new ProgramData(data.getKeywords(), new VariableScope(data.getVariables()));
            Program subProgram = subProgramData.buildProgram(new ArrayList<>(subTokenList));
            data.getProgram().addInstructions(subProgram);
        }
    }

    private List<String> getTokensToSameBracketLevel(List<String> tokens) {
        List<String> list = new ArrayList<>();

        int depth = 1;

        while (!tokens.isEmpty()) {
            String token = tokens.removeFirst();
            if (token.equals("{")) {
                depth++;
                list.add(token);
            } else if (token.equals("}")) {
                depth--;
                if (depth == 0) {
                    return list;
                }
                list.add(token);
            } else {
                list.add(token);
            }
        }

        throw new UnknownTokenException("Expected to find \"}\", could not find");
    }
}
