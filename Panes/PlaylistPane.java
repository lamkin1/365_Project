package com.mycompany.csc365p1.Panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PlaylistPane extends Pane {
    public PlaylistPane() {
        super();
    }

    @Override
    public void addChildren() {
        super.addChildren();

        VBox containerVBox = new VBox();
        containerVBox.setPadding(new Insets(0, 0, 250, 0));
        containerVBox.setAlignment(Pos.CENTER);
        containerVBox.setSpacing(10);
        VBox.setVgrow(containerVBox, Priority.ALWAYS);

        Button createPlaylistButton = new Button("Create playlist");
        createPlaylistButton.setOnAction(actionEvent -> showPane(new AddPlaylistPane(this).root));

        Button showAllPlaylistsButton = new Button("Show all playlists");
        showAllPlaylistsButton.setOnAction(actionEvent -> showPane(new AllPlaylistsPane(this).root));

        containerVBox.getChildren().addAll(createPlaylistButton, showAllPlaylistsButton);

        root.getChildren().addAll(containerVBox);
    }
}
