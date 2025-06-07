package com.example.mandaladrawer.builder;

import com.example.mandaladrawer.instruction.RotateInstruction;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Builder;

import java.util.concurrent.Callable;

public class TestBuilder implements Builder<Region> {

    @Override
    public Region build() {
        VBox vBox = new VBox(5);

        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(360);

        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(10));

        Node turtle = createTurtle();
        Rotate rotate = new Rotate();
        rotate.pivotXProperty().set(0);
        rotate.pivotYProperty().set(0);
        turtle.getTransforms().add(rotate);
        rotate.angleProperty().bind(slider.valueProperty());

        stackPane.getChildren().add(turtle);

        vBox.getChildren().addAll(slider, stackPane);

        return vBox;
    }

    private Node createTurtle() {
        Group group = new Group();

        Circle circle = new Circle();
        circle.setRadius(15);
        group.getChildren().add(circle);

        double length = 10;
        double tmp = Math.sin(Math.toRadians(60));
        Polygon head = new Polygon(
                0, 0,
                length * tmp, length / 2,
                0, length
        );
        head.setLayoutX(15);
        head.setLayoutY(-length / 2);
        group.getChildren().add(head);

        return group;
    }
}
