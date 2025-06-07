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
    private TurtlePosition currentPosition;
    private TurtlePosition previousPosition;

    private Program currentProgram;

    private final List<EventHandler<DrawEvent>> onLineAdded;
    private final List<EventHandler<ActionEvent>> onClear;
    private final List<EventHandler<MoveEvent>> onMove;

    public DrawingManager(double height, double width) {
        this.height = height;
        this.width = width;

        onLineAdded = new ArrayList<>();
        onClear = new ArrayList<>();
        onMove = new ArrayList<>();

        penDown = true;
        currentPosition = new TurtlePosition(0, 0, 0, width, height);
    }

    public void addOnLineAddedHandler(EventHandler<DrawEvent> handler) {
        onLineAdded.add(handler);
    }

    public void addOnClearHandler(EventHandler<ActionEvent> handler) {
        onClear.add(handler);
    }

    public void addOnMoveHandle(EventHandler<MoveEvent> handler) {
        onMove.add(handler);
    }

    public void loadProgram(Program program) {
        currentProgram = program;
        currentPosition = new TurtlePosition(0, 0, 0, width, height);
        updatePosition();
        clear();
    }

    public void drawInstant() {
        System.out.println("Starting program, " + currentProgram.getInstructionSize() + " instructions");
        while (currentProgram.hasInstructionsLeft()) {
            executeNextInstruction();
        }
        currentProgram = null;
    }

    public void executeNextInstruction() {
        if (!currentProgram.hasInstructionsLeft()) {
            currentProgram = null;
            return;
        }

        Instruction currentInstruction = currentProgram.getNextInstruction();

        currentInstruction.execute(this);
    }

    public void drawAnimated() {

    }

//    public void animateNextInstruction() {
//        if (!currentProgram.hasInstructionsLeft()) {
//            currentProgram = null;
//            return;
//        }
//
//        Instruction currentInstruction = currentProgram.getNextInstruction();
//
//        Timeline instructionTimeline = currentInstruction.createTimeline(this);
//
//
//        instructionTimeline.setOnFinished(_ -> animateNextInstruction());
//    }

    public void moveForward(double distance) {
        previousPosition = currentPosition;
        currentPosition = currentPosition.moveForward(distance);
    }

    public void rotate(double angle) {
        previousPosition = currentPosition;
        currentPosition = currentPosition.rotate(angle);
    }

    public void goTo(double x, double y) {
        previousPosition = currentPosition;
        currentPosition = new TurtlePosition(x, y, currentPosition.getHeading(), width, height);
    }

    public void raisePen() {
        penDown = false;
    }

    public void lowerPen() {
        penDown = true;
    }

    public void moved() {
        updatePosition();

        if (penDown) {
            placeLine(previousPosition, currentPosition);
        }
    }

    public void updatePosition() {
        MoveEvent event = new MoveEvent(currentPosition);
        for (EventHandler<MoveEvent> eventHandler : onMove) {
            eventHandler.handle(event);
        }
    }

    public void placeLine(TurtlePosition pos1, TurtlePosition pos2) {
        DrawEvent event = new DrawEvent(pos1, pos2);
        for (EventHandler<DrawEvent> eventHandler : onLineAdded) {
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

    public boolean isPenDown() {
        return penDown;
    }

    public TurtlePosition getPosition() {
        return currentPosition;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
