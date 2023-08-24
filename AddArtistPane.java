package com.mycompany.csc365p1;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class AddArtistPane {
    VBox root;
    ArtistsPane parent;

    AddArtistPane(ArtistsPane parent) {
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

        Label artistNameLabel = new Label("Artist name:");
        TextField artistNameInput = new TextField();

        Label statusLabel = new Label();

        Button addButton = new Button("Add artist");
        addButton.setOnAction(actionEvent -> {
            String artist = artistNameInput.getText();
            try {
                App.dbConn.insertArtist(artist);
                statusLabel.setText("Added artist " + artist);
            }
            catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    statusLabel.setText("Artist " + artist + " already exists");
                } else {
                    statusLabel.setText("An error occured");
                }
            }
        });

        root.getChildren().addAll(headerHBox, artistNameLabel, artistNameInput, addButton, statusLabel);
    }

    public VBox getRoot() {
        return root;
    }
}
