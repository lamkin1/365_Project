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

        Tab songsTab = new Tab("Songs");
        songsTab.setClosable(false);

        SongPane songPane = new SongPane();
        songsTab.setContent(songPane.getRoot());

        Tab artistsTab = new Tab("Artists");
        artistsTab.setClosable(false);

        Tab albumsTab = new Tab("Albums");
        albumsTab.setClosable(false);

        AlbumPane albumPane = new AlbumPane();
        albumsTab.setContent(albumPane.getRoot());

        ArtistsPane artistsPane = new ArtistsPane();
        artistsTab.setContent(artistsPane.getRoot());

        Tab genresTab = new Tab("Genres");
        genresTab.setClosable(false);

        GenrePane genrePane = new GenrePane();
        genresTab.setContent(genrePane.getRoot());

        Tab playlistsTab = new Tab("Playlists");
        playlistsTab.setClosable(false);

        tabPane.getTabs().addAll(songsTab, artistsTab, albumsTab, genresTab, playlistsTab);

        root.getChildren().addAll(tabPane);
    }

    public StackPane getRoot() {
        return root;
    }
}