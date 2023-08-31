package com.mycompany.csc365p1;

import com.mycompany.csc365p1.Panes.*;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;

/**
 *
 * @author danielching
 * class to represent the main title screen
 */
class TitleScreen {
    StackPane root;

    TitleScreen() {
        root = new StackPane();

        addChildren();
    }

    void addChildren() {
        root.getChildren().clear();

        TabPane tabPane = new TabPane();

        Tab homeTab = new Tab("Home");
        homeTab.setClosable(false);

        HomePane homePane = new HomePane(tabPane);
        homeTab.setContent(homePane.getRoot());

        Tab songsTab = new Tab("Songs");
        songsTab.setClosable(false);

        SongPane songPane = new SongPane();
        songsTab.setContent(songPane.getRoot());

        Tab playlistsTab = new Tab("Playlists");
        playlistsTab.setClosable(false);

        PlaylistPane playlistPane = new PlaylistPane();
        playlistsTab.setContent(playlistPane.getRoot());

        tabPane.getTabs().addAll(homeTab, songsTab, playlistsTab);

        root.getChildren().addAll(tabPane);
    }

    public StackPane getRoot() {
        return root;
    }
}