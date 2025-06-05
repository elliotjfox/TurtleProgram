package com.example.mandaladrawer;

import com.example.mandaladrawer.builder.DisplayBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class MainDrawing extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DrawingManager manager = new DrawingManager(400, 400);
        Region display = new DisplayBuilder(manager).build();
        Scene scene = new Scene(display);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}