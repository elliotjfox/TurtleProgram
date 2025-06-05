package com.example.mandaladrawer;

import javafx.event.ActionEvent;

public class TextLoadedEvent extends ActionEvent {

    private final String loadedText;

    public TextLoadedEvent(String loadedText) {
        this.loadedText = loadedText;
    }

    public String getLoadedText() {
        return loadedText;
    }
}
