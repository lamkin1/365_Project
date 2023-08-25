package com.mycompany.csc365p1;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ArtistsPane {
    VBox root;

    ArtistsPane() {
        root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setFillWidth(true);

        addChildren();
    }

    public void addChildren() {
        root.getChildren().clear();

        Button findArtistButton = new Button("Find artist by name");

        root.getChildren().addAll(findArtistButton);
    }

    public VBox getRoot() {
        return root;
    }
}
