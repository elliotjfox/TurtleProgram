package com.example.mandaladrawer;

import javafx.event.ActionEvent;

import javax.swing.*;

public class MoveEvent extends ActionEvent {

    private final TurtlePosition position;

    public MoveEvent(TurtlePosition position) {
        this.position = position;
    }

    public TurtlePosition getPosition() {
        return position;
    }
}
