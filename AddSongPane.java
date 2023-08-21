package com.mycompany.csc365p1;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AddSongPane {
    VBox root;

    AddSongPane() {
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setFillWidth(true);

        addChildren();
    }

    void addChildren() {
        root.getChildren().removeAll();

        Label titleLabel = new Label("Title:");
        TextField titleInput = new TextField();

        root.getChildren().addAll(titleLabel, titleInput);
    }

    public VBox getRoot() {
        return root;
    }
}
