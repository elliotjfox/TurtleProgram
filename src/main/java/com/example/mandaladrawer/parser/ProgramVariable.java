package com.example.mandaladrawer.parser;

public class ProgramVariable {

    private final String name;
    private double value;

    public ProgramVariable(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
