package com.example.mandaladrawer.event;

import com.example.mandaladrawer.TurtlePosition;
import javafx.event.ActionEvent;

public class EndAnimationEvent extends ActionEvent {

    private final TurtlePosition position;
    private final boolean interrupted;

    public EndAnimationEvent(TurtlePosition position, boolean interrupted) {
        this.position = position;
        this.interrupted = interrupted;
    }

    public TurtlePosition getPosition() {
        return position;
    }

    public boolean isInterrupted() {
        return interrupted;
    }
}
