package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import com.mycompany.csc365p1.Song;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.sql.SQLException;

public class AddSongPane extends ChildPane {
    AddSongPane(Pane parent) {
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

        Button addButton = new Button("Add song");
        addButton.setOnAction(actionEvent -> {
            try {
                statusLabel.setStyle("-fx-text-fill: green;");
                App.dbConn.insertSong(new Song(
                        titleInputLabel.getText(),
                        artistInputLabel.getText(),
                        albumInputLabel.getText(),
                        Integer.parseInt(durationInputLabel.getText()),
                        genreInputLabel.getText(),
                        eraInputLabel.getText()
                ));
                statusLabel.setText("Inserted song successfully");
            }
            catch (SQLException e) {
                statusLabel.setStyle("-fx-text-fill: red;");
                if (e.getErrorCode() == 1062) {
                    statusLabel.setText("Song already exists");
                } else if (e.getErrorCode() == 1452) {
                    statusLabel.setText("No such artist exists");
                } else {
                    statusLabel.setText("An unknown error occured");
                }
            }
            catch (NumberFormatException e) {
                statusLabel.setText("Duration must be a valid integer");
            }
        });

        root.getChildren().addAll(
                titleInputLabel.getRoot(),
                artistInputLabel.getRoot(),
                albumInputLabel.getRoot(),
                durationInputLabel.getRoot(),
                genreInputLabel.getRoot(),
                eraInputLabel.getRoot(),
                addButton,
                statusLabel
        );
    }
}
