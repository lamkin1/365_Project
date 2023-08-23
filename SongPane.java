package com.mycompany.csc365p1;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class SongPane {
    VBox root;

    SongPane() {
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setFillWidth(true);

        addChildren();
    }

    public void addChildren() {
        root.getChildren().clear();

        Button addSongButton = new Button("Add song");
        addSongButton.setOnAction(actionEvent -> showAddSongPane());
        Button modifySongButton = new Button("Modify song");
        Button findSongButton = new Button("Find song");

        root.getChildren().addAll(addSongButton, modifySongButton, findSongButton);
    }

    void showAddSongPane() {
        root.getChildren().clear();
        AddSongPane addSongPane = new AddSongPane(this);
        root.getChildren().addAll(addSongPane.getRoot());
    }

    public VBox getRoot() {
        return root;
    }
}
