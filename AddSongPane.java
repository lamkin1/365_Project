package com.mycompany.csc365p1;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.sql.SQLException;

public class AddSongPane {
    VBox root;
    SongPane parent;

    AddSongPane(SongPane parent) {
        this.parent = parent;

        root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: lightblue");

        addChildren();
    }

    void addChildren() {
        root.getChildren().clear();

        Button returnButton = new Button("Return");
        returnButton.setOnAction(actionEvent -> parent.addChildren());

        HBox headerHBox = new HBox();
        headerHBox.getChildren().add(returnButton);

        Label titleLabel = new Label("Title:");
        TextField titleInput = new TextField();
        HBox titleHBox = new HBox();
        titleHBox.getChildren().addAll(titleLabel, titleInput);
        titleHBox.setAlignment(Pos.CENTER);

        Label artistLabel = new Label("Artist:");
        TextField artistInput = new TextField();
        HBox artistHBox = new HBox();
        artistHBox.getChildren().addAll(artistLabel, artistInput);
        artistHBox.setAlignment(Pos.CENTER);

        Label statusLabel = new Label();

        Button addButton = new Button("Add song");
        addButton.setOnAction(actionEvent -> {
            try {
                statusLabel.setStyle("-fx-text-fill: green;");
                App.dbConn.insertSong(titleInput.getText(), artistInput.getText());
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
        });

        root.getChildren().addAll(headerHBox, titleHBox, artistHBox, addButton, statusLabel);
    }

    public VBox getRoot() {
        return root;
    }
}
