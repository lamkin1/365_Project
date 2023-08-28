package com.mycompany.csc365p1.Panes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FindSongsPane extends ChildPane {
    FindSongsPane(SongPane parent) {
        super(parent);
    }

    void addChildren() {
        super.addChildren();

        InputLabel titleInputLabel = new InputLabel("Title");
        InputLabel artistInputLabel = new InputLabel("Artist");
        InputLabel albumInputLabel = new InputLabel("Album");
        InputLabel durationInputLabel = new InputLabel("Duration");
        InputLabel genreInputLabel = new InputLabel("Genre");
        InputLabel eraInputLabel = new InputLabel("Era");

        Label statusLabel = new Label();

        Button updateSongButton = new Button("Update song");
        updateSongButton.setOnAction(actionEvent -> {
            // TODO update the song
        });

        Button deleteSongButton = new Button("Delete song");
        deleteSongButton.setOnAction(actionEvent -> {
            // TODO delete the song
        });

        root.getChildren().addAll(
                titleInputLabel.getRoot(),
                artistInputLabel.getRoot(),
                albumInputLabel.getRoot(),
                durationInputLabel.getRoot(),
                genreInputLabel.getRoot(),
                eraInputLabel.getRoot(),
                findSongButton,
                updateSongButton,
                deleteSongButton,
                statusLabel
        );
    }
}
