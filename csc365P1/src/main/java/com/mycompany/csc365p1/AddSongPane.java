package com.mycompany.csc365p1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

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

        Button addSongButton = new Button("Add song");
        addSongButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Called");
                showAddSongPane();
            }
        });
        Button modifySongButton = new Button("Modify song");
        Button findSongButton = new Button("Find song");

        root.getChildren().addAll(addSongButton, modifySongButton, findSongButton);
    }

    void showAddSongPane() {
        root.getChildren().removeAll();

        AddSongPane addSongPane = new AddSongPane();
        root.getChildren().addAll(addSongPane.getRoot());
    }

    public VBox getRoot() {
        return root;
    }
}