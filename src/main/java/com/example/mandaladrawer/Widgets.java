package com.example.mandaladrawer;

import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Widgets {

    public static TextInputDialog createTextInputDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save As");
        dialog.setHeaderText("What do you want to save the file as?");
        dialog.setContentText("File name:");
        return dialog;
    }

    public static Alert createSaveAlert(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Filename conflict");
        alert.setHeaderText("A file exists with the same name (\"" + name + "\")");
        alert.setContentText("Do you want to overwrite that file?");

        return alert;
    }

    public static Alert createUnsavedFileAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Unsaved File");
        alert.setHeaderText("The current file is unsaved.");
        alert.setContentText("Save changes before opening new file?");

        return alert;
    }

    public static MenuItem createMenuItem(String text, EventHandler<ActionEvent> onAction) {
        MenuItem menuItem = new MenuItem();

        menuItem.setText(text);
        menuItem.setOnAction(onAction);

        return menuItem;
    }

    public static Button createButton(String text, EventHandler<ActionEvent> onAction) {
        Button button = new Button();

        button.setText(text);
        button.setOnAction(onAction);

        return button;
    }

    public static Button createButton(Node icon, EventHandler<ActionEvent> onAction) {
        Button button = new Button();

        button.setGraphic(icon);
        button.setOnAction(onAction);

        return button;
    }

    public static Node createPlayIcon(double length) {
        double sine = Math.sin(Math.toRadians(60));

        return new Polygon(
                0, 0,
                length * sine, length / 2,
                0, length
        );
    }

    public static Node createPauseIcon(double length) {
        Group group = new Group();
        double third = length / 3;

        Rectangle rectangle1 = new Rectangle(third, length);
        Rectangle rectangle2 = new Rectangle(third, length);
        rectangle2.setX(length - third);

        group.getChildren().addAll(rectangle1, rectangle2);

        return group;
    }

    public static Node createSkipIcon(double length) {
        Group group = new Group();

        Node play = createPlayIcon(length);
        Rectangle rectangle = new Rectangle(length / 3, length);
        rectangle.setX(length);

        group.getChildren().addAll(play, rectangle);

        return group;
    }

    public static CustomMenuItem createSettingSlider(String title, DoubleProperty settingProperty, double min, double max) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        Label label = new Label(title);
        label.setTextFill(Color.BLACK);
        vBox.getChildren().add(label);

        Slider slider = new Slider();
        slider.valueProperty().bindBidirectional(settingProperty);
        slider.setMin(min);
        slider.setMax(max);
        slider.setShowTickLabels(true);

        vBox.getChildren().add(slider);

        CustomMenuItem menuItem = new CustomMenuItem();
        menuItem.setContent(vBox);
        menuItem.setHideOnClick(false);

        return menuItem;
    }
}
