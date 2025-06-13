package com.example.mandaladrawer;

import javafx.event.ActionEvent;
import javafx.scene.Node;

public class DrawEvent extends ActionEvent {
    private final TurtlePosition pos1;
    private final Node graphic;

    public DrawEvent(TurtlePosition pos1, Node graphic) {
        this.pos1 = pos1;
        this.graphic = graphic;
    }

    public TurtlePosition getPos1() {
        return pos1;
    }

    public Node getGraphic() {
        return graphic;
    }
}
