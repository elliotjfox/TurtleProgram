package com.example.mandaladrawer.event;

import com.example.mandaladrawer.TurtlePosition;
import javafx.event.ActionEvent;

public class BeginMoveAnimationEvent extends ActionEvent {

    private final TurtlePosition previousPosition;
    private final TurtlePosition position;
    private final boolean penDown;

    public BeginMoveAnimationEvent(TurtlePosition previousPosition, TurtlePosition animatedPosition, boolean penDown) {
        this.previousPosition = previousPosition;
        this.position = animatedPosition;
        this.penDown = penDown;
    }

    public TurtlePosition getPreviousPosition() {
        return previousPosition;
    }

    public TurtlePosition getPosition() {
        return position;
    }

    public boolean isPenDown() {
        return penDown;
    }
}
