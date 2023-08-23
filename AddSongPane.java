package com.mycompany.csc365p1;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class AddSongPane {
    VBox root;
    SongPane parent;

    AddSongPane(SongPane parent) {
        this.parent = parent;

        root = new VBox();
        root.setAlignment(Pos.CENTER);

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

        VBox submitVBox = new VBox();
        Button addButton = new Button("Add song");

        Label errorLabel = new Label();
        addButton.setOnAction(actionEvent -> {
            try {
                errorLabel.setText("");
                App.dbConn.insertSong(titleInput.getText(), artistInput.getText());
            }
            catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    errorLabel.setText("No such artist exists");
                } else {
                    errorLabel.setText("An error occured");
                }
            }
        });
        submitVBox.getChildren().addAll(addButton, errorLabel);

        root.getChildren().addAll(headerHBox, titleHBox, artistHBox, submitVBox);
    }

    public VBox getRoot() {
        return root;
    }
}
