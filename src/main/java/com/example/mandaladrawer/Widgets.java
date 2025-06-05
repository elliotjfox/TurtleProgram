package com.example.mandaladrawer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;

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

    public static Node createButton(String text, EventHandler<ActionEvent> onAction) {
        Button button = new Button();

        button.setText(text);
        button.setOnAction(onAction);

        return button;
    }
}
