package com.example.mandaladrawer;

import javafx.event.ActionEvent;

public class DrawEvent extends ActionEvent {
    private final TurtlePosition pos1;
    private final TurtlePosition pos2;

    public DrawEvent(TurtlePosition pos1, TurtlePosition pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public TurtlePosition getPos1() {
        return pos1;
    }

    public TurtlePosition getPos2() {
        return pos2;
    }
}
