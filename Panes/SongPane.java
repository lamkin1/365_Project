package com.mycompany.csc365p1.Panes;

import javafx.scene.control.Button;

public class SongPane extends Pane {
    public SongPane() {
        super();
    }

    @Override
    public void addChildren() {
        super.addChildren();

        Button addSongButton = new Button("Add song");
        addSongButton.setOnAction(actionEvent -> showPane(new AddSongPane(this).root));

        Button editSongButton = new Button("Edit song");
        editSongButton.setOnAction(actionEvent -> showPane(new FindSongsPane(this).root));

        Button showAllSongsButton = new Button("Show all songs");
        showAllSongsButton.setOnAction(actionEvent -> showPane(new AllSongsPane(this).root));

        root.getChildren().addAll(addSongButton, editSongButton, showAllSongsButton);
    }
}
