package com.example.mandaladrawer.event;

import com.example.mandaladrawer.TurtlePosition;
import javafx.event.ActionEvent;

public class MoveEvent extends ActionEvent {

    private final TurtlePosition position;

    public MoveEvent(TurtlePosition position) {
        this.position = position;
    }

    public TurtlePosition getPosition() {
        return position;
    }
}
