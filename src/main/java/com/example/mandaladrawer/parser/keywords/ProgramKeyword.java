package com.example.mandaladrawer.parser.keywords;

import com.example.mandaladrawer.parser.ProgramData;

public abstract class ProgramKeyword {

    private final String keyword;

    public ProgramKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean match(String str) {
        return keyword.equals(str);
    }

    public abstract void parse(ProgramData data);
}
