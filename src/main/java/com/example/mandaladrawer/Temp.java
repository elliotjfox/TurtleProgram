package com.example.mandaladrawer;

import com.example.mandaladrawer.builder.TestBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Temp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Region display = new TestBuilder().build();

        Scene scene = new Scene(display);
        stage.setTitle("Full");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String... args) {
        launch();
    }
}
