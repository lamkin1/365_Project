package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import com.mycompany.csc365p1.Song;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class AddSongPane extends ChildPane {
    AddSongPane(Pane parent) {
        super(parent);
    }

    @Override
    void addChildren() {
        super.addChildren();

        VBox titleLabelVBox = new VBox();
        Label titleLabel = new Label("All fields are required");
        titleLabelVBox.getChildren().add(titleLabel);
        titleLabelVBox.setAlignment(Pos.CENTER);

        VBox inputLabelsVBox = new VBox();
        InputLabel titleInputLabel = new InputLabel("Title");
        InputLabel artistInputLabel = new InputLabel("Artist");
        InputLabel albumInputLabel = new InputLabel("Album");
        InputLabel durationInputLabel = new InputLabel("Duration");
        InputLabel genreInputLabel = new InputLabel("Genre");
        InputLabel eraInputLabel = new InputLabel("Era");

        inputLabelsVBox.getChildren().addAll(
                titleInputLabel.root,
                artistInputLabel.root,
                albumInputLabel.root,
                durationInputLabel.root,
                genreInputLabel.root,
                eraInputLabel.root
        );

        StatusLabel statusLabel = new StatusLabel();

        VBox addSongButtonVBox = new VBox();
        addSongButtonVBox.setAlignment(Pos.CENTER);
        Button addButton = new Button("Add song");
        addSongButtonVBox.getChildren().add(addButton);
        addButton.setOnAction(actionEvent -> {
            String title = titleInputLabel.getText();
            String artist = artistInputLabel.getText();
            String album = albumInputLabel.getText();
            String duration = durationInputLabel.getText();
            String genre = genreInputLabel.getText();
            String era = eraInputLabel.getText();

            if (title == null) {
                statusLabel.warn("Title required");
                return;
            }
            if (artist == null) {
                statusLabel.warn("Artist required");
                return;
            }
            if (album == null) {
                statusLabel.warn("Album required");
                return;
            }
            if (duration == null) {
                statusLabel.warn("Duration required");
                return;
            }
            if (genre == null) {
                statusLabel.warn("Genre required");
                return;
            }
            if (era == null) {
                statusLabel.warn("Era required");
                return;
            }

            try {
                App.dbConn.insertSong(new Song(title, artist, album, Integer.parseInt(duration), genre, era));
                statusLabel.confirm("Inserted song successfully");
            }
            catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    statusLabel.warn("Song already exists");
                } else if (e.getErrorCode() == 1452) {
                    statusLabel.warn("No such artist exists");
                } else {
                    statusLabel.warn("An unknown error occured");
                }
            }
            catch (NumberFormatException e) {
                statusLabel.warn("Duration must be a valid integer");
            }
        });

        root.getChildren().addAll(
                titleLabelVBox,
                inputLabelsVBox,
                addSongButtonVBox,
                statusLabel.getRoot()
        );
    }
}
