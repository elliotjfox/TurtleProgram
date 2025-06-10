package com.example.mandaladrawer.builder;

import com.example.mandaladrawer.*;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
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
        BorderPane borderPane = new BorderPane();

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().add(new EditorBuilder(editorManager).build());
        splitPane.getItems().add(new DisplayBuilder(drawingManager).with(editorManager).build());
        borderPane.setCenter(splitPane);

        borderPane.setTop(createMenu());

        return borderPane;
    }

    private Node createMenu() {
        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().add(createFileMenu());
        menuBar.getMenus().add(createSettingsMenu());
        menuBar.getMenus().add(createDebugMenu());

        return menuBar;
    }

    private Menu createFileMenu() {
        Menu fileMenu = new Menu();
        fileMenu.setText("File");

        fileMenu.getItems().add(Widgets.createMenuItem("Save", _ -> editorManager.save()));
        fileMenu.getItems().add(Widgets.createMenuItem("Save As", _ -> editorManager.saveAs()));
        fileMenu.getItems().add(Widgets.createMenuItem("Open File", _ -> editorManager.openFile()));

        fileMenu.getItems().add(new SeparatorMenuItem());

        fileMenu.getItems().add(createExamplesMenu());

        return fileMenu;
    }

    private Menu createExamplesMenu() {
        Menu examples = new Menu();
        examples.setText("Load Example");

        examples.getItems().add(Widgets.createMenuItem("Square", _ -> editorManager.loadExample(Program.SQUARE_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Dashed Square", _ -> editorManager.loadExample(Program.DASHED_SQUARE_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Hexagon", _ -> editorManager.loadExample(Program.HEXAGON_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Flower", _ -> editorManager.loadExample(Program.FLOWER_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Spiral", _ -> editorManager.loadExample(Program.SPIRAL_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Polygon", _ -> editorManager.loadExample(Program.POLYGON_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Concentric Polygons", _ -> editorManager.loadExample(Program.CONCENTRIC_POLYGONS)));
        examples.getItems().add(Widgets.createMenuItem("Grid", _ -> editorManager.loadExample(Program.GRID_EXAMPLE)));

        return examples;
    }

    private Menu createSettingsMenu() {
        Menu settings = new Menu();
        settings.setText("Settings");

        settings.getItems().add(createThemeMenu());
        settings.getItems().add(createSpeedMenu());

        return settings;
    }

    private Menu createThemeMenu() {
        Menu theme = new Menu();
        theme.setText("Set Theme");

        return theme;
    }

    private Menu createSpeedMenu() {
        Menu speed = new Menu();
        speed.setText("Animation Speed");

        speed.getItems().add(Widgets.createSettingSlider("Global Animation Speed", SettingsManager.animationSpeed, -1, 1));
        speed.getItems().add(new SeparatorMenuItem());
        speed.getItems().add(Widgets.createSettingSlider("Movement Animation Speed", SettingsManager.movementAnimationSpeed, -1, 1));
        speed.getItems().add(Widgets.createSettingSlider("Rotation Animation Speed", SettingsManager.rotationAnimationSpeed, -1, 1));

        return speed;
    }

    private Menu createDebugMenu() {
        Menu debug = new Menu();
        debug.setText("Debug");

//        debug.getItems().add(Widgets.createMenuItem("Reload css", _ -> Main.reloadCss()));

        return debug;
    }
}
