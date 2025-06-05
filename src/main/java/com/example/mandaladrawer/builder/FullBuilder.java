package com.example.mandaladrawer.builder;

import com.example.mandaladrawer.DrawingManager;
import com.example.mandaladrawer.EditorManager;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class FullBuilder implements Builder<Region> {

    private final DrawingManager drawingManager;
    private final EditorManager editorManager;

    public FullBuilder(DrawingManager drawingManager, EditorManager editorManager) {
        this.drawingManager = drawingManager;
        this.editorManager = editorManager;
    }

    @Override
    public Region build() {
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().add(new EditorBuilder(editorManager).build());
        splitPane.getItems().add(new DisplayBuilder(drawingManager).with(editorManager).build());
        return splitPane;
    }
}
