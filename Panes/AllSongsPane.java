package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import com.mycompany.csc365p1.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllSongsPane extends ChildPane {
    AllSongsPane(SongPane parent) {
        super(parent);
    }

    @Override
    void addChildren() {
        super.addChildren();

        Label statusLabel = new Label();

        TableView table = new TableView();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setStyle("-fx-selection-bar-non-focused: -fx-selection-bar;");

        ArrayList<Song> songs = new ArrayList<>();

        try {
            ResultSet rs = App.dbConn.selectAllSongs();
            ResultSetMetaData metaData = rs.getMetaData();

            for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
                String colName = metaData.getColumnName(i);
                TableColumn col = new TableColumn<>(colName);
                col.setCellValueFactory(new PropertyValueFactory<Song, String>(colName));
                table.getColumns().add(col);
            }

            while (rs.next()) {
                songs.add(new Song(
                        rs.getString("songTitle"),
                        rs.getString("artist"),
                        rs.getString("album"),
                        rs.getInt("duration"),
                        rs.getString("genre"),
                        rs.getString("era")
                ));
            }

            table.setItems(FXCollections.observableArrayList(songs));
        } catch (SQLException e) {
            System.out.println("Select all songs error");
        }

        Button findSongButton = new Button("Find song");

        ArrayList<String> playlistNames = App.dbConn.getPlaylistNames();
        ComboBox<String> playlistComboBox = new ComboBox<>(FXCollections.observableArrayList(playlistNames));
        playlistComboBox.setEditable(true);

        Button addSongToPlaylistButton = new Button("Add selected song(s) to playlist");
        addSongToPlaylistButton.setOnAction(actionEvent -> {
            ObservableList<Song> selectedSongs = table.getSelectionModel().getSelectedItems();
            String playlistName = playlistComboBox.getValue();

            for (Song song: selectedSongs) {
                try {
                    App.dbConn.addSongToPlaylist(song, playlistName);
                } catch (SQLException e) {
                    if (e.getErrorCode() == 1062) {
                    } else {
                        statusLabel.setStyle("-fx-text-fill: red;");
                        statusLabel.setText("An unknown error occured");
                        break;
                    }
                }
            }

            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Added songs to playlist " + playlistName);
        });

        InputLabel titleInputLabel = new InputLabel("Title");
        InputLabel artistInputLabel = new InputLabel("Artist");
        InputLabel albumInputLabel = new InputLabel("Album");
        InputLabel durationInputLabel = new InputLabel("Duration");
        InputLabel genreInputLabel = new InputLabel("Genre");
        InputLabel eraInputLabel = new InputLabel("Era");

        root.getChildren().addAll(
                table,
                addSongToPlaylistButton,
                playlistComboBox,
                titleInputLabel.root,
                artistInputLabel.root,
                albumInputLabel.root,
                durationInputLabel.root,
                genreInputLabel.root,
                eraInputLabel.root,
                findSongButton,
                statusLabel
        );

    }
}