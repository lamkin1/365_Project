package com.mycompany.csc365p1.Panes;

import javafx.scene.control.Button;

public class AlbumPane extends Pane {
    public AlbumPane() {
        super();
    }

    @Override
    public void addChildren() {
        super.addChildren();

        Button showAllAlbumsButton = new Button("Show all albums");
        showAllAlbumsButton.setOnAction(actionEvent -> showPane(new AllAlbumsPane(this).root));

        root.getChildren().add(showAllAlbumsButton);
    }
}
