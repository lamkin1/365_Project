package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class AddPlaylistPane extends ChildPane {
    AddPlaylistPane(PlaylistPane parent) {
        super(parent);
    }

    @Override
    void addChildren() {
        super.addChildren();

        StatusLabel statusLabel = new StatusLabel();

        InputLabel nameInputLabel = new InputLabel("Playlist name");
        nameInputLabel.root.setSpacing(10);

        VBox createPlaylistVBox = new VBox();
        createPlaylistVBox.setAlignment(Pos.CENTER);
        Button createPlaylistButton = new Button("Create playlist");
        createPlaylistButton.setOnAction(actionEvent -> {
            String playlistName = nameInputLabel.getText();
            if (playlistName == null) {
                statusLabel.warn("Playlist must have a name");
                return;
            }

            try {
                App.dbConn.insertPlaylist(nameInputLabel.getText());
                statusLabel.confirm("Created playlist");
            }
            catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    statusLabel.warn("Playlist already exists");
                } else {
                    statusLabel.warn("An unknown error occured");
                }
            }
        });
        createPlaylistVBox.getChildren().add(createPlaylistButton);

        root.getChildren().addAll(nameInputLabel.root, createPlaylistVBox, statusLabel.getRoot());
    }
}
