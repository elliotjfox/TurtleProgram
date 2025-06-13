package com.example.mandaladrawer.event;

import javafx.event.ActionEvent;
import javafx.scene.shape.Path;

public class StartPathEvent extends ActionEvent {

    private final Path path;

    public StartPathEvent(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
