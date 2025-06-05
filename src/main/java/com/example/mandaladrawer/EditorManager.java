package com.example.mandaladrawer;

import com.example.mandaladrawer.parser.ProgramData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EditorManager {

    private String name;
    private boolean saved;

    private final StringProperty textProperty;

    private final List<EventHandler<TextLoadedEvent>> onLoad;

    private Stage stage;

    public EditorManager() {
        textProperty = new SimpleStringProperty();
        onLoad = new ArrayList<>();

        saved = true;
        textProperty.addListener(_ -> saved = false);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addOnLoad(EventHandler<TextLoadedEvent> handler) {
        onLoad.add(handler);
    }

    public void openFile() {
        if (stage == null) return;

        if (!saved) {
            Alert alert = Widgets.createUnsavedFileAlert();

            ButtonType save = new ButtonType("Save");
            ButtonType forceExit = new ButtonType("Exit without saving");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(save, forceExit, cancel);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isEmpty()) {
                return;
            }

            if (result.get() == save) {
                save();
            } else if (result.get() == forceExit) {

            } else {
                return;
            }
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Drawing Files", "*.drawing"));

        File file = fileChooser.showOpenDialog(stage);
        if (file == null) return;
        name = file.getName().split("[.]")[0];


        String newText = ProgramData.readFile(file);
        textProperty.set(newText);

        TextLoadedEvent event = new TextLoadedEvent(newText);
        for (EventHandler<TextLoadedEvent> eventHandler : onLoad) {
            eventHandler.handle(event);
        }
        saved = true;
    }

    public void loadExample(String code) {
        if (!saved) {
            Alert alert = Widgets.createUnsavedFileAlert();

            ButtonType save = new ButtonType("Save");
            ButtonType forceExit = new ButtonType("Exit without saving");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(save, forceExit, cancel);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isEmpty()) {
                return;
            }

            if (result.get() == save) {
                save();
            } else if (result.get() == forceExit) {

            } else {
                return;
            }
        }

        textProperty.set(code);

        TextLoadedEvent event = new TextLoadedEvent(code);
        for (EventHandler<TextLoadedEvent> eventHandler : onLoad) {
            eventHandler.handle(event);
        }
        saved = true;
    }

    public void save() {
        if (name == null) {
            saveAs();
        } else {
            String fileName = name + ".drawing";
            // TODO: Add check to see if file still exists?
            //  Currently just resurrects the file if it was deleted
            try {
                FileWriter writer = new FileWriter(fileName);
                writer.write(textProperty.get());
                writer.close();
                System.out.println("File saved");
                saved = true;
            } catch (IOException e) {
                System.out.println("Could not save");
            }
        }
    }

    public void saveAs() {
        Optional<String> result = Widgets.createTextInputDialog().showAndWait();
        result.ifPresent(s -> {
            name = s;
            saveFile(s);
        });
    }

    public void textChanged() {
        saved = false;
    }

    private void saveFile(String saveName) {
        String fileName = saveName + ".drawing";
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created");

                FileWriter writer = new FileWriter(fileName);
                writer.write(textProperty.get());
                writer.close();
                System.out.println("Wrote to file");

                saved = true;
            } else {
                Alert alert = Widgets.createSaveAlert(saveName);

                ButtonType overwrite = new ButtonType("Overwrite file");
                ButtonType rename = new ButtonType("Choose a different name");
                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(overwrite, rename, cancel);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isEmpty()) {
                    return;
                }

                if (result.get() == overwrite) {
                    FileWriter writer = new FileWriter(fileName);
                    writer.write(textProperty.get());
                    writer.close();
                    System.out.println("Wrote to file");

                    saved = true;
                } else if (result.get() == rename) {
                    saveAs();
                } else {
                    // Do nothing?
                }
            }
        } catch (IOException e) {
            System.out.println("Could not create file");
        }
    }

    public StringProperty textProperty() {
        return textProperty;
    }
}
