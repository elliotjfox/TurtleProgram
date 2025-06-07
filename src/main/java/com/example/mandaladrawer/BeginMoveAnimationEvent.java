package com.example.mandaladrawer;

import javafx.event.ActionEvent;

public class BeginMoveAnimationEvent extends ActionEvent {

    private final TurtlePosition previousPosition;
    private final TurtlePosition position;

    public BeginMoveAnimationEvent(TurtlePosition previousPosition, TurtlePosition animatedPosition) {
        this.previousPosition = previousPosition;
        this.position = animatedPosition;
    }

    public TurtlePosition getPreviousPosition() {
        return previousPosition;
    }

    public TurtlePosition getPosition() {
        return position;
    }
}
