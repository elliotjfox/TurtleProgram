package com.example.mandaladrawer;

import com.example.mandaladrawer.builder.EditorBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class MainEditor extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        EditorManager manager = new EditorManager();
        manager.setStage(stage);
        Region display = new EditorBuilder(manager).build();

        Scene scene = new Scene(display);
        stage.setTitle("Editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String... args) {
        launch(args);
    }
}
