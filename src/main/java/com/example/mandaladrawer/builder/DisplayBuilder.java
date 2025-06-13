package com.example.mandaladrawer.builder;

import com.example.mandaladrawer.DrawingManager;
import com.example.mandaladrawer.EditorManager;
import com.example.mandaladrawer.Program;
import com.example.mandaladrawer.Widgets;
import com.example.mandaladrawer.parser.ProgramData;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
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

        stackPane.setPadding(new Insets(5));

        stackPane.getChildren().add(createInkDisplay());
        stackPane.getChildren().add(createTurtleDisplay());

        return stackPane;
    }

    private Node createInkDisplay() {
        Pane inkDisplay = new Pane();
        inkDisplay.setMouseTransparent(true);

        manager.addOnLineAddedHandler(drawEvent -> inkDisplay.getChildren().add(drawEvent.getGraphic()));

        manager.addOnBeginAnimation(animationEvent -> {
            if (!animationEvent.isPenDown()) return;
            Line line = new Line();
            line.setStartX(animationEvent.getPreviousPosition().getLayoutX());
            line.setStartY(animationEvent.getPreviousPosition().getLayoutY());
            line.endXProperty().bind(animationEvent.getPosition().layoutXProperty());
            line.endYProperty().bind(animationEvent.getPosition().layoutYProperty());
            inkDisplay.getChildren().add(line);
        });

        manager.addOnClearHandler(_ -> inkDisplay.getChildren().clear());

        return inkDisplay;
    }

    private Node createTurtleDisplay() {
        Pane turtleDisplay = new Pane();
        turtleDisplay.setMouseTransparent(true);

        Node turtle = createTurtle();
        Rotate rotation = new Rotate();
        rotation.setPivotX(0);
        rotation.setPivotY(0);
        turtle.getTransforms().add(rotation);

        manager.addOnMoveHandle(moveEvent -> {
            turtle.setLayoutX(moveEvent.getPosition().getLayoutX());
            turtle.setLayoutY(moveEvent.getPosition().getLayoutY());
            rotation.setAngle(-moveEvent.getPosition().getHeading());
        });

        manager.addOnBeginAnimation(animationEvent -> {
            turtle.layoutXProperty().bind(animationEvent.getPosition().layoutXProperty());
            turtle.layoutYProperty().bind(animationEvent.getPosition().layoutYProperty());
            rotation.angleProperty().bind(animationEvent.getPosition().headingProperty().multiply(-1));
        });

        manager.addOnEndAnimation(moveEvent -> {
            turtle.layoutXProperty().unbind();
            turtle.layoutYProperty().unbind();
            rotation.angleProperty().unbind();

            turtle.setLayoutX(moveEvent.getPosition().getLayoutX());
            turtle.setLayoutY(moveEvent.getPosition().getLayoutY());
            rotation.setAngle(-moveEvent.getPosition().getHeading());
        });

        turtleDisplay.getChildren().add(turtle);
        return turtleDisplay;
    }

    private Node createTurtle() {
        Group turtle = new Group();

        Circle body = new Circle();
        body.setRadius(5);
        body.setFill(Color.LIGHTGREEN);

        double tmp = Math.sin(Math.toRadians(60));
        double length = 6.5;
        Polygon head = new Polygon(
                0, 0,
                length * tmp, length / 2,
                0, length
        );
        head.setFill(Color.LIGHTGREEN);
        head.setLayoutX(4.5);
        head.setLayoutY(-length / 2);

        turtle.getChildren().addAll(body, head);

        return turtle;
    }

    private Region createButtonBar() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);

        if (editorManager == null) {
            hBox.getChildren().add(Widgets.createButton("Finish Drawing", _ -> manager.drawInstant()));
            hBox.getChildren().add(Widgets.createButton("Animate Drawing", _ -> manager.drawAnimated()));
            hBox.getChildren().add(Widgets.createButton("Next Step", _ -> manager.executeNextInstruction()));
        } else {
            hBox.getChildren().add(Widgets.createButton(Widgets.createSkipIcon(10), event -> {
                if (manager.programmed()) {
                    manager.drawInstant();
                } else {
                    Program p = ProgramData.getStandardData().buildProgram(editorManager.textProperty().get());
                    if (p == null) {
                        System.out.println("Null program");
                    } else {
                        manager.loadProgram(p);
                        manager.drawInstant();
                    }
                }
            }));
            hBox.getChildren().add(Widgets.createButton(Widgets.createPlayIcon(10), event -> {
                if (!manager.programmed()) {
                    Program p = ProgramData.getStandardData().buildProgram(editorManager.textProperty().get());
                    manager.loadProgram(p);
                }
                manager.drawAnimated();
            }));
        }

        return hBox;
    }
}
