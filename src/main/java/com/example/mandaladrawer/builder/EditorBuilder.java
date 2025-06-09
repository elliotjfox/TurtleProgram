package com.example.mandaladrawer.builder;

import com.example.mandaladrawer.EditorManager;
import com.example.mandaladrawer.Program;
import com.example.mandaladrawer.Widgets;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        examples.getItems().add(Widgets.createMenuItem("Dashed Square", _ -> manager.loadExample(Program.DASHED_SQUARE_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Hexagon", _ -> manager.loadExample(Program.HEXAGON_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Flower", _ -> manager.loadExample(Program.FLOWER_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Spiral", _ -> manager.loadExample(Program.SPIRAL_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Polygon", _ -> manager.loadExample(Program.POLYGON_EXAMPLE)));
        examples.getItems().add(Widgets.createMenuItem("Concentric Polygons", _ -> manager.loadExample(Program.CONCENTRIC_POLYGONS)));
        examples.getItems().add(Widgets.createMenuItem("Grid", _ -> manager.loadExample(Program.GRID_EXAMPLE)));

        return examples;
    }

    private Menu createDebugMenu() {
        Menu debug = new Menu();
        debug.setText("Debug");

//        debug.getItems().add(Widgets.createMenuItem("Style", ))

        return debug;
    }

    private Node createTextEntry() {
        StyleClassedTextArea textArea = new StyleClassedTextArea();

        textArea.setWrapText(false);
        textArea.textProperty().addListener(((_, _, s) -> {
            manager.textProperty().setValue(s);
            manager.textChanged();
        }));

        manager.addOnLoad(event -> textArea.replaceText(event.getLoadedText()));

        final Pattern whiteSpace = Pattern.compile( "^\\s+" );
        final Pattern openBracket = Pattern.compile("[{]");

        textArea.addEventHandler(KeyEvent.KEY_PRESSED, KE -> {
            if (KE.getCode() == KeyCode.ENTER) {
                int caretPosition = textArea.getCaretPosition();
                int currentParagraph = textArea.getCurrentParagraph();
                Matcher m0 = whiteSpace.matcher(textArea.getParagraph(currentParagraph - 1).getSegments().getFirst());
                Matcher bracket = openBracket.matcher(textArea.getParagraph(currentParagraph - 1).getSegments().getFirst());

                // Indent to same level as previous
                if (m0.find()) {
                    Platform.runLater(() -> textArea.insertText(caretPosition, m0.group()));

                    if (bracket.find()) {
                        Platform.runLater(() -> {
                            textArea.insertText(caretPosition, m0.group() + "\t\n");
                            textArea.moveTo(caretPosition + 2);
                        });
                    }
                } else if (bracket.find()) {
                    // If there was an open bracket, move the other one further, and indent one extra
                    Platform.runLater(() -> {
                        textArea.insertText(caretPosition, "\t\n");
                        textArea.moveTo(caretPosition + 1);
                    });
                }
            } else if (KE.isShiftDown() && KE.getCode() == KeyCode.OPEN_BRACKET) {
                int caretPosition = textArea.getCaretPosition();

                Platform.runLater(() -> {
                    textArea.insertText(caretPosition + 1, "}");
                    textArea.moveTo(caretPosition + 1);
                });

            }
        });

        return textArea;
    }
}
