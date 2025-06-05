package com.example.mandaladrawer.builder;

import com.example.mandaladrawer.EditorManager;
import com.example.mandaladrawer.Program;
import com.example.mandaladrawer.Widgets;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.fxmisc.richtext.StyleClassedTextArea;

public class EditorBuilder implements Builder<Region> {

    private final EditorManager manager;

    public EditorBuilder(EditorManager manager) {
        this.manager = manager;
    }

    @Override
    public Region build() {
        BorderPane borderPane = new BorderPane();

        borderPane.setTop(createMenu());

        Node textEntry = createTextEntry();
        BorderPane.setMargin(textEntry, new Insets(4, 2, 2, 4));
        borderPane.setCenter(textEntry);

        return borderPane;
    }

    private Node createMenu() {
        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().add(createFileMenu());
        menuBar.getMenus().add(createDebugMenu());

        return menuBar;
    }

    private Menu createFileMenu() {
        Menu fileMenu = new Menu();
        fileMenu.setText("File");

        fileMenu.getItems().add(Widgets.createMenuItem("Save", _ -> manager.save()));
        fileMenu.getItems().add(Widgets.createMenuItem("Save As", _ -> manager.saveAs()));
        fileMenu.getItems().add(Widgets.createMenuItem("Open File", _ -> manager.openFile()));

        fileMenu.getItems().add(new SeparatorMenuItem());

        fileMenu.getItems().add(createExamplesMenu());

        return fileMenu;
    }

    private Menu createExamplesMenu() {
        Menu examples = new Menu();
        examples.setText("Load Example");

        examples.getItems().add(Widgets.createMenuItem("Square", _ -> manager.loadExample(Program.SQUARE_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Hexagon", _ -> manager.loadExample(Program.HEXAGON_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Flower", _ -> manager.loadExample(Program.FLOWER_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Spiral", _ -> manager.loadExample(Program.SPIRAL_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Polygon", _ -> manager.loadExample(Program.POLYGON_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Dashed Square", _ -> manager.loadExample(Program.DASHED_SQUARE_EXAMPLE)));

        return examples;
    }

    private Menu createDebugMenu() {
        Menu debug = new Menu();
        debug.setText("Debug");

//        debug.getItems().add(Widgets.createMenuItem("Style", ))

        return debug;
    }

    private Node createTextEntry() {
//        TextArea textArea = new TextArea();
//
//        textArea.setWrapText(false);
//        textArea.textProperty().bindBidirectional(manager.textProperty());
//
//        return textArea;

        StyleClassedTextArea textArea = new StyleClassedTextArea();

        textArea.setWrapText(false);
        textArea.textProperty().addListener(((_, _, s) -> {
            manager.textProperty().setValue(s);
            manager.textChanged();
        }));

        manager.addOnLoad(event -> textArea.replaceText(event.getLoadedText()));

        return textArea;

//        CodeArea codeArea = new CodeArea("test");
//
//        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
//        codeArea.setWrapText(true);
//        codeArea.textProperty().addListener((_, s, _) -> manager.textProperty().setValue(s));
//        manager.addOnLoad(event -> codeArea.replaceText(event.getLoadedText()));
//
//        return codeArea;
    }
}
