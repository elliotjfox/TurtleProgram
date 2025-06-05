package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.parser.ProgramData;

import java.util.function.Consumer;

public class SimpleKeyword extends ProgramKeyword {

    private final Consumer<ProgramData> onParse;

    public SimpleKeyword(String keyword, Consumer<ProgramData> onParse) {
        super(keyword);
        this.onParse = onParse;
    }

    @Override
    public void parse(ProgramData data) {
        onParse.accept(data);
    }
}
