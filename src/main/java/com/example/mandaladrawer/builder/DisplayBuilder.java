package com.example.mandaladrawer.builder;

import com.example.mandaladrawer.DrawingManager;
import com.example.mandaladrawer.EditorManager;
import com.example.mandaladrawer.Program;
import com.example.mandaladrawer.Widgets;
import com.example.mandaladrawer.parser.ProgramData;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Builder;

public class DisplayBuilder implements Builder<Region> {

    private final DrawingManager manager;
    private EditorManager editorManager;

    public DisplayBuilder(DrawingManager manager) {
        this.manager = manager;
    }

    public DisplayBuilder with(EditorManager editorManager) {
        this.editorManager = editorManager;
        return this;
    }

    @Override
    public Region build() {
        VBox vBox = new VBox();

        HBox tmp = new HBox();
        tmp.setMouseTransparent(true);
        tmp.getChildren().add(createDisplay());

        vBox.getChildren().add(createButtonBar());
        vBox.getChildren().add(tmp);
        return vBox;
    }

    private Region createDisplay() {
        StackPane stackPane = new StackPane();

        stackPane.setMouseTransparent(true);

        stackPane.setPrefHeight(manager.getHeight());
        stackPane.setPrefWidth(manager.getWidth());
        stackPane.setAlignment(Pos.CENTER);

        stackPane.getChildren().add(createInkDisplay());
        stackPane.getChildren().add(createTurtle());

        return stackPane;
    }

    private Node createInkDisplay() {
        Pane inkDisplay = new Pane();
        inkDisplay.setMouseTransparent(true);
        manager.addOnDrawHandler(drawEvent -> inkDisplay.getChildren().add(
                new Line(
                        drawEvent.getPos1().getX(),
                        drawEvent.getPos1().getY(),
                        drawEvent.getPos2().getX(),
                        drawEvent.getPos2().getY()
                )
        ));
        manager.addOnClearHandler(_ -> inkDisplay.getChildren().clear());
        return inkDisplay;
    }

    private Node createTurtle() {
        Pane turtleDisplay = new Pane();
        turtleDisplay.setMouseTransparent(true);

        Circle turtle = new Circle();
        turtle.setRadius(10);
        turtle.setFill(Color.WHITE);
        turtle.setCenterX(manager.getWidth() / 2);
        turtle.setCenterY(manager.getHeight() / 2);
        manager.addOnMoveHandle(moveEvent -> {
            turtle.setCenterX(moveEvent.getPosition().getX());
            turtle.setCenterY(moveEvent.getPosition().getY());
        });

        turtleDisplay.getChildren().add(turtle);
        return turtleDisplay;
    }

    private Region createButtonBar() {
        HBox hBox = new HBox();

        if (editorManager == null) {
            hBox.getChildren().add(Widgets.createButton("Finish Drawing", _ -> manager.drawInstant()));
            hBox.getChildren().add(Widgets.createButton("Animate Drawing", _ -> manager.drawSlow()));
            hBox.getChildren().add(Widgets.createButton("Next Step", _ -> manager.executeNextInstruction()));
        } else {
            hBox.getChildren().add(Widgets.createButton("Draw", event -> {
                Program p = ProgramData.getStandardData().buildProgram(editorManager.textProperty().get());
                if (p == null) {
                    System.out.println("Null program");
                } else {
                    manager.loadProgram(p);
                    manager.drawInstant();
                }
            }));
            hBox.getChildren().add(Widgets.createButton("Step", event -> {
                if (!manager.programmed()) {
                    Program p = ProgramData.getStandardData().buildProgram(editorManager.textProperty().get());
                    manager.loadProgram(p);
                }
                manager.executeNextInstruction();
            }));
        }

        return hBox;
    }
}
