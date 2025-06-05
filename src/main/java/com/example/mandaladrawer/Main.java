package com.example.mandaladrawer;

import com.example.mandaladrawer.builder.FullBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DrawingManager drawingManager = new DrawingManager(400, 400);
        EditorManager editorManager = new EditorManager();
        editorManager.setStage(stage);
        Region display = new FullBuilder(drawingManager, editorManager).build();

        Scene scene = new Scene(display);
        URL cssSheet = getClass().getResource("editor.css");
        if (cssSheet != null) {
            scene.getStylesheets().add(cssSheet.toExternalForm());
        }
        stage.setTitle("Full");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
