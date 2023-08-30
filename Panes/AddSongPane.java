package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import com.mycompany.csc365p1.Song;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class AddSongPane extends ChildPane {
    AddSongPane(Pane parent) {
        super(parent);

        VBox.setVgrow(root, Priority.ALWAYS);
    }

    @Override
    void addChildren() {
        super.addChildren();

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

        Label statusLabel = new Label();

        Button addButton = new Button("Add song");
        addButton.setAlignment(Pos.CENTER);
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
                statusLabel.setStyle("-fx-text-fill: red;");
                statusLabel.setText("Duration must be a valid integer");
            }
        });

        root.getChildren().addAll(
                inputLabelsVBox,
                addButton,
                statusLabel
        );
    }
}
