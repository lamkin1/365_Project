package com.mycompany.csc365p1.Panes;

import javafx.scene.control.Button;

public class PlaylistPane extends Pane {
    public PlaylistPane() {
        super();
    }

    @Override
    public void addChildren() {
        super.addChildren();

        Button createPlaylistButton = new Button("Create playlist");
        createPlaylistButton.setOnAction(actionEvent -> showPane(new AddPlaylistPane(this).root));

        Button showAllPlaylistsButton = new Button("Show all playlists");
        showAllPlaylistsButton.setOnAction(actionEvent -> showPane(new AllPlaylistsPane(this).root));

        root.getChildren().addAll(createPlaylistButton, showAllPlaylistsButton);
    }
}
