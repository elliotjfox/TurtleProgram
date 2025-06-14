package com.example.mandaladrawer;

import com.example.mandaladrawer.event.BeginMoveAnimationEvent;
import com.example.mandaladrawer.event.EndAnimationEvent;
import com.example.mandaladrawer.event.MoveEvent;
import com.example.mandaladrawer.event.StartPathEvent;
import com.example.mandaladrawer.instruction.Instruction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.util.ArrayList;
import java.util.List;

public class DrawingManager {

    private final double height;
    private final double width;

    private boolean penDown;
    private TurtlePosition currentPosition;
    private TurtlePosition previousPosition;

    private Program currentProgram;
    private Path currentPath;

    private final List<EventHandler<ActionEvent>> onClear;
    private final List<EventHandler<MoveEvent>> onMove;
    private final List<EventHandler<BeginMoveAnimationEvent>> onBeginAnimation;
    private final List<EventHandler<EndAnimationEvent>> onEndAnimation;
    private final List<EventHandler<StartPathEvent>> onPathStart;

    private Animation currentAnimation;

    public DrawingManager(double height, double width) {
        this.height = height;
        this.width = width;

        onClear = new ArrayList<>();
        onMove = new ArrayList<>();
        onBeginAnimation = new ArrayList<>();
        onEndAnimation = new ArrayList<>();
        onPathStart = new ArrayList<>();

        penDown = true;
        currentPosition = new TurtlePosition(0, 0, 0, width, height);
    }

    public void addOnClearHandler(EventHandler<ActionEvent> handler) {
        onClear.add(handler);
    }

    public void addOnMoveHandle(EventHandler<MoveEvent> handler) {
        onMove.add(handler);
    }

    public void addOnBeginAnimation(EventHandler<BeginMoveAnimationEvent> handler) {
        onBeginAnimation.add(handler);
    }

    public void addOnEndAnimation(EventHandler<EndAnimationEvent> handler) {
        onEndAnimation.add(handler);
    }

    public void addPathStart(EventHandler<StartPathEvent> handler) {
        onPathStart.add(handler);
    }

    public void loadProgram(Program program) {
        currentProgram = program;
        currentPosition = new TurtlePosition(0, 0, 0, width, height);
        updatePosition();
        clear();
        currentPath = new Path(new MoveTo(currentPosition.getLayoutX(), currentPosition.getLayoutY()));

        StartPathEvent event = new StartPathEvent(currentPath);
        for (EventHandler<StartPathEvent> eventHandler : onPathStart) {
            eventHandler.handle(event);
        }

        System.out.println("Program loaded - " + program.getInstructionSize() + " instructions");
    }

    public void drawInstant() {
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

        if (isAnimationRunning()) {
            forceQuitAnimation();
        }

        Instruction currentInstruction = currentProgram.getNextInstruction();

        currentInstruction.execute(this, currentPath);
        updatePosition();
    }

    public void drawAnimated() {
        animateNextInstruction();
    }

    public void animateNextInstruction() {
        if (!currentProgram.hasInstructionsLeft()) {
            currentProgram = null;
            return;
        }

        if (isAnimationRunning()) {
            System.out.println("Timeline already running!");
            return;
        }

        Instruction currentInstruction = currentProgram.getNextInstruction();
        previousPosition = currentPosition;
        currentPosition = currentPosition.copy();

        Animation animation = currentInstruction.createAnimation(this, currentPath);

        if (animation == null) {
            currentInstruction.execute(this, currentPath);
            updatePosition();
            animateNextInstruction();
        } else {
            animation.onFinished(this, endAnimationEvent -> {
                for (EventHandler<EndAnimationEvent> handler : onEndAnimation) {
                    handler.handle(endAnimationEvent);
                }

                currentAnimation = null;

                if (!endAnimationEvent.isInterrupted()) {
                    animateNextInstruction();
                }
            });

            BeginMoveAnimationEvent event = new BeginMoveAnimationEvent(previousPosition, currentPosition, penDown);
            for (EventHandler<BeginMoveAnimationEvent> handler : onBeginAnimation) {
                handler.handle(event);
            }

            currentAnimation = animation;
            animation.play();
        }
    }

    private void forceQuitAnimation() {
        if (!isAnimationRunning()) {
            System.out.println("No animation to quit");
            return;
        }

        Animation animation = currentAnimation;
        currentAnimation = null;
        animation.finish(currentPosition);
    }

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

    public void face(double heading) {
        previousPosition = currentPosition;
        currentPosition = new TurtlePosition(currentPosition.getX(), currentPosition.getY(), heading, width, height);
    }

    public void raisePen() {
        penDown = false;
    }

    public void lowerPen() {
        penDown = true;
    }

    public void updatePosition() {
        MoveEvent event = new MoveEvent(currentPosition);
        for (EventHandler<MoveEvent> eventHandler : onMove) {
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

    public boolean isAnimationRunning() {
        return currentAnimation != null;
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
