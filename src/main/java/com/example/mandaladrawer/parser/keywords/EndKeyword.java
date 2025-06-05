package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.parser.ProgramData;

public class EndKeyword extends ProgramKeyword {

    public EndKeyword() {
        super("end");
    }

    @Override
    public void parse(ProgramData data) {
        data.getTokens().clear();
    }
}
