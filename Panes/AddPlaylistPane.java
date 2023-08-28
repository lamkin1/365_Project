package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class AddPlaylistPane extends ChildPane {
    AddPlaylistPane(PlaylistPane parent) {
        super(parent);
    }

    @Override
    void addChildren() {
        super.addChildren();

        Label statusLabel = new Label();

        InputLabel nameInputLabel = new InputLabel("Playlist name");
        Button createPlaylistButton = new Button("Create playlist");
        createPlaylistButton.setOnAction(actionEvent -> {
            try {
                statusLabel.setStyle("-fx-text-fill: green;");
                App.dbConn.insertPlaylist(nameInputLabel.getText());
                statusLabel.setText("Created playlist");
            }
            catch (SQLException e) {
                statusLabel.setStyle("-fx-text-fill: red;");
                if (e.getErrorCode() == 1062) {
                    statusLabel.setText("Playlist already exists");
                } else {
                    statusLabel.setText("An unknown error occured");
                }
            }
        });

        root.getChildren().addAll(nameInputLabel.root, createPlaylistButton, statusLabel);
    }
}
