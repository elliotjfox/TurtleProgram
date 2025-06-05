package com.example.mandaladrawer.parser;

import java.util.ArrayList;
import java.util.List;

public class MethodArguments {

    private List<String > arguments;

    public MethodArguments(String... arguments) {
        this.arguments = new ArrayList<>(List.of(arguments));
    }

    public int size() {
        return arguments.size();
    }
}
