package com.mycompany.csc365p1.Panes;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.geometry.Insets;

public class HomePane extends Pane {
    TabPane tabPane;
    public HomePane(TabPane tabPane) {
        super();
        this.tabPane = tabPane;
        root.getStyleClass().add("home-pane");
        root.setPadding(new Insets(0, 0, 250, 0));
    }

    @Override
    public void addChildren() {
        Label titleLabel = new Label("Music Library");
        titleLabel.getStyleClass().add("title-label");

        Button songsButton = new Button("Songs");
        songsButton.setOnAction(actionEvent -> {
            tabPane.getSelectionModel().select(1);
        });

        Button playlistsButton = new Button("Playlists");
        playlistsButton.setOnAction(actionEvent -> {
            tabPane.getSelectionModel().select(2);
        });

        root.getChildren().addAll(titleLabel, songsButton, playlistsButton);
    }
}
