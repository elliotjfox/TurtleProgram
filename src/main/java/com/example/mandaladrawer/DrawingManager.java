package com.example.mandaladrawer;

import com.example.mandaladrawer.instruction.Instruction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class DrawingManager {

    private final double height;
    private final double width;

    private boolean penDown;
    private TurtlePosition lastPosition;
    private TurtlePosition currentPosition;

    private Program currentProgram;

    private final List<EventHandler<DrawEvent>> onDraw;
    private final List<EventHandler<ActionEvent>> onClear;
    private final List<EventHandler<MoveEvent>> onMove;

    public DrawingManager(double height, double width) {
        this.height = height;
        this.width = width;

        this.onDraw = new ArrayList<>();
        this.onClear = new ArrayList<>();
        this.onMove = new ArrayList<>();

        penDown = true;
        lastPosition = new TurtlePosition(200, 200, 0);
        currentPosition = new TurtlePosition(200, 200, 0);
    }

    public void addOnDrawHandler(EventHandler<DrawEvent> handler) {
        onDraw.add(handler);
    }

    public void addOnClearHandler(EventHandler<ActionEvent> handler) {
        onClear.add(handler);
    }

    public void addOnMoveHandle(EventHandler<MoveEvent> handler) {
        onMove.add(handler);
    }

    public void loadProgram(Program program) {
        currentProgram = program;
        currentPosition = new TurtlePosition(width / 2, height / 2, 0);
        moved();
        clear();
    }

    public void drawInstant() {
        System.out.println("Drawing " + currentProgram.getInstructionSize() + " instructions");
        while (currentProgram.hasInstructionsLeft()) {
            executeNextInstruction();
        }
        currentProgram = null;
    }

    public void drawSlow() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), actionEvent -> executeNextInstruction())
        );
        timeline.setCycleCount(currentProgram.getInstructionSize());
        timeline.play();
    }

    public void executeNextInstruction() {
        if (!currentProgram.hasInstructionsLeft()) {
            currentProgram = null;
            return;
        }

        Instruction currentInstruction = currentProgram.getNextInstruction();
        currentInstruction.execute(this);
        if (currentInstruction.movesTurtle()) {
            moved();
        }
    }

    public void moveForward(double distance) {
        currentPosition = currentPosition.moveForward(distance);
    }

    public void rotate(double angle) {
        currentPosition = currentPosition.rotate(angle);
    }

    public void raisePen() {
        penDown = false;
    }

    public void lowerPen() {
        penDown = true;
    }

    private void moved() {
        if (penDown) {
            placeLine(lastPosition, currentPosition);
        }

        lastPosition = currentPosition;

        MoveEvent event = new MoveEvent(currentPosition);
        for (EventHandler<MoveEvent> eventHandler : onMove) {
            eventHandler.handle(event);
        }
    }

    private void placeLine(TurtlePosition pos1, TurtlePosition pos2) {
        DrawEvent event = new DrawEvent(pos1, pos2);
        for (EventHandler<DrawEvent> eventHandler : onDraw) {
            eventHandler.handle(event);
        }
    }

    public void clear() {
        ActionEvent event = new ActionEvent();
        clear(event);
    }

    public void clear(ActionEvent event) {
        for (EventHandler<ActionEvent> eventHandler : onClear) {
            eventHandler.handle(event);
        }
    }

    public boolean programmed() {
        return currentProgram != null;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
