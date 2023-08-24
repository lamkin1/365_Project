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

        Button addSongButton = new Button("Add artist");
        addSongButton.setOnAction(actionEvent -> showAddArtistPane());

        Button modifySongButton = new Button("Modify artist");
        Button findSongButton = new Button("Find artist");

        root.getChildren().addAll(addSongButton, modifySongButton, findSongButton);
    }

    void showAddArtistPane() {
        root.getChildren().clear();
        AddArtistPane addArtistPane = new AddArtistPane(this);
        root.getChildren().add(addArtistPane.getRoot());
    }

    public VBox getRoot() {
        return root;
    }
}
