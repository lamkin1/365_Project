package com.mycompany.csc365p1;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author danielching
 * class to represent the main title screen
 */
class TitleScreen {
    VBox root;
    final int width;
    final int height;

    TitleScreen(int width, int height) {
        this.width = width;
        this.height = height;

        root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(0.03125 * this.height);
        root.setFillWidth(true);

        addChildren();
    }

    void addChildren() {
        root.getChildren().removeAll();

        List<Button> buttons = Arrays.asList(
                new Button("Songs"),
                new Button("Artists"),
                new Button("Albums"),
                new Button("Genres"),
                new Button("Playlists")
        );

        for (Button button: buttons) {
            button.setMaxWidth(0.8 * this.width);
            button.setStyle(String.format("-fx-font-size: %d", Math.round(0.046875 * this.width)));
            button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
            root.getChildren().add(button);
        }
    }

    public VBox getRoot() {
        return root;
    }
}
