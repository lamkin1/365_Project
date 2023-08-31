package com.mycompany.csc365p1.Panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SongPane extends Pane {
    public SongPane() {
        super();
    }

    @Override
    public void addChildren() {
        super.addChildren();

        VBox containerVBox = new VBox();
        containerVBox.setPadding(new Insets(0, 0, 250, 0));
        containerVBox.setAlignment(Pos.CENTER);
        containerVBox.setSpacing(10);
        VBox.setVgrow(containerVBox, Priority.ALWAYS);

        Button addSongButton = new Button("Add song");
        addSongButton.setOnAction(actionEvent -> showPane(new AddSongPane(this).root));

        Button showAllSongsButton = new Button("Show all songs");
        showAllSongsButton.setOnAction(actionEvent -> showPane(new AllSongsPane(this).root));

        containerVBox.getChildren().addAll(addSongButton, showAllSongsButton);

        root.getChildren().addAll(containerVBox);
    }
}
