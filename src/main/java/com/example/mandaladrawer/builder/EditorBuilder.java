package com.example.mandaladrawer.builder;

import com.example.mandaladrawer.EditorManager;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
        StackPane stackPane = new StackPane();

        Node textEntry = createTextEntry();
        stackPane.setPadding(new Insets(4, 2, 2, 4));
        stackPane.getChildren().add(textEntry);

        return stackPane;
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
                // If '{' was pressed, add a '}' to the other side
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
