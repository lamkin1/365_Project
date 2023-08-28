package com.mycompany.csc365p1.Panes;

import javafx.scene.control.Button;

public class ArtistsPane extends Pane {
    public ArtistsPane() {
        super();
    }

    public void addChildren() {
        super.addChildren();

        Button findArtistButton = new Button("Find artist by name");
        Button showAllArtistsButton = new Button("Show all artists");
        showAllArtistsButton.setOnAction(actionEvent -> showPane(new AllArtistsPane(this).root));

        root.getChildren().addAll(findArtistButton, showAllArtistsButton);
    }
}
