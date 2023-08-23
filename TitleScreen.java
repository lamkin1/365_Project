package com.mycompany.csc365p1;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;

/**
 *
 * @author danielching
 * class to represent the main title screen
 */
class TitleScreen {
    VBox root;
    final int width;
    final int height;

    TitleScreen(int width, int height) {
        this.width = width;
        this.height = height;

        root = new VBox();

        addChildren();
    }

    void addChildren() {
        root.getChildren().removeAll();

        TabPane tabPane = new TabPane();

        Tab songsTab = new Tab("Songs");
        songsTab.setClosable(false);

        SongPane songPane = new SongPane();
        songsTab.setContent(songPane.getRoot());

        Tab artistsTab = new Tab("Artists");
        artistsTab.setClosable(false);

        Tab albumsTab = new Tab("Albums");
        albumsTab.setClosable(false);

        Tab genresTab = new Tab("Genres");
        genresTab.setClosable(false);

        Tab playlistsTab = new Tab("Playlists");
        playlistsTab.setClosable(false);

        tabPane.getTabs().addAll(songsTab, artistsTab, albumsTab, genresTab, playlistsTab);

        root.getChildren().addAll(tabPane);
    }

    public VBox getRoot() {
        return root;
    }
}
