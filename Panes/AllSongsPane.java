package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import com.mycompany.csc365p1.Song;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllSongsPane extends ChildPane {
    ObservableList<Song> observableSongs;
    AllSongsPane(SongPane parent) {
        super(parent);
    }

    void populateTable(TableView table, StatusLabel statusLabel) {
        ArrayList<Song> songs = new ArrayList<>();

        table.getColumns().clear();
        table.getSelectionModel().clearSelection();

        try {
            ResultSet rs = App.dbConn.selectAllSongs();
            ResultSetMetaData metaData = rs.getMetaData();

            for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
                String attrName = metaData.getColumnName(i);
                TableColumn<Song, String> col = new TableColumn<>(Song.attributeTitles.get(attrName));
                col.setCellValueFactory(new PropertyValueFactory<>(attrName));

                col.setCellValueFactory(cellData -> {
                    Song song = cellData.getValue();
                    StringProperty cellValue = song.getPropertyByName(attrName);
                    return cellValue;
                });

                col.setOnEditCommit(event -> {
                    Song oldSong = event.getRowValue();
                    Song newSong = new Song(oldSong.getSongTitle(), oldSong.getArtist(), oldSong.getAlbum(), oldSong.getDuration(), oldSong.getGenre(), oldSong.getEra());

                    String newValue = event.getNewValue();
                    try {
                        newSong.setPropertyByName(attrName, newValue);
                        try {
                            App.dbConn.updateSong(oldSong, newSong);

                            oldSong.setSongTitle(newSong.getSongTitle());
                            oldSong.setArtist(newSong.getArtist());
                            oldSong.setAlbum(newSong.getAlbum());
                            oldSong.setDuration(newSong.getDuration());
                            oldSong.setGenre(newSong.getGenre());
                            oldSong.setEra(newSong.getEra());

                            statusLabel.confirm("Updated song");
                        } catch (SQLException e) {
                            e.printStackTrace();
                            statusLabel.warn("Error updating song");
                        }
                    } catch (NumberFormatException e) {
                        statusLabel.warn("Invalid duration");
                        populateTable(table, statusLabel);
                    }
                });

                col.setCellFactory(TextFieldTableCell.forTableColumn());

                table.getColumns().add(col);
            }

            TableColumn<Song, Button> deleteCol = new TableColumn<>();
            deleteCol.setCellFactory(new Callback<>() {
                @Override
                public TableCell<Song, Button> call(TableColumn<Song, Button> songStringTableColumn) {
                    return new TableCell<>() {
                        private final Button button = new Button("Delete");
                        {
                            button.setOnAction(event -> {
                                Song song = observableSongs.get(getIndex());
                                if (App.dbConn.deleteSong(song)) {
                                    observableSongs.remove(song);
                                    table.refresh();
                                    statusLabel.confirm("Deleted song!");
                                } else {
                                    statusLabel.warn("An error occured while deleting song");
                                }
                            });
                        }

                        @Override
                        protected void updateItem(Button item, boolean empty) {
                            super.updateItem(item, empty);
                            if (!empty) {
                                setGraphic(button);
                            } else {
                                setGraphic(null);
                            }
                        }
                    };
                }
            });

            table.getColumns().add(deleteCol);

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

            observableSongs = FXCollections.observableArrayList(songs);
            table.setItems(observableSongs);
            table.getSelectionModel().getSelectedIndices().addListener((ListChangeListener) observable -> {
                int numSelectedRows = table.getSelectionModel().getSelectedItems().size();
                if (numSelectedRows > 0) {
                    statusLabel.confirm(numSelectedRows + " selected song(s)");
                }
            });
        } catch (SQLException e) {
            System.out.println("Select all songs error");
        }
    }

    @Override
    void addChildren() {
        super.addChildren();

        StatusLabel statusLabel = new StatusLabel();
        statusLabel.label.setFont(new Font(14));

        TableView table = new TableView<>();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setEditable(true);
        table.setStyle("-fx-selection-bar-non-focused: -fx-selection-bar;");

        populateTable(table, statusLabel);

        VBox findInputsVBox = new VBox();
        findInputsVBox.setAlignment(Pos.CENTER);

        Label filterSongLabel = new Label("Filter song(s)");

        InputLabel titleInputLabel = new InputLabel("Title");
        InputLabel artistInputLabel = new InputLabel("Artist");
        InputLabel albumInputLabel = new InputLabel("Album");
        InputLabel durationInputLabel = new InputLabel("Duration");
        InputLabel genreInputLabel = new InputLabel("Genre");
        InputLabel eraInputLabel = new InputLabel("Era");

        findInputsVBox.getChildren().addAll(
                filterSongLabel,
                titleInputLabel.root,
                artistInputLabel.root,
                albumInputLabel.root,
                durationInputLabel.root,
                genreInputLabel.root,
                eraInputLabel.root
        );

        HBox findSongHBox = new HBox();
        findSongHBox.setSpacing(10);

        Button findSongButton = new Button("Find");
        findSongButton.setOnAction(actionEvent -> {

            String title = titleInputLabel.getText();
            String artist = artistInputLabel.getText();
            String album = albumInputLabel.getText();
            String duration = durationInputLabel.getText();
            String genre = genreInputLabel.getText();
            String era = eraInputLabel.getText();

            if (title == null && artist == null && album == null && duration == null && genre == null && era == null) {
                populateTable(table, statusLabel);
                return;
            }

            try {
                ArrayList<Song> foundSongs = App.dbConn.findSongs(title, artist, album, duration, genre, era);
                ArrayList<Song> observableSongsToRemove = new ArrayList<>();

                if (foundSongs.isEmpty()) {
                    statusLabel.warn("No songs found");
                    return;
                }

                populateTable(table, statusLabel);

                for (Song song: observableSongs) {
                    if (!foundSongs.contains(song)) {
                        observableSongsToRemove.add(song);
                    }
                }

                observableSongs.removeIf(observableSongsToRemove::contains);

                table.getSelectionModel().selectAll();
                table.refresh();

            } catch (SQLException e) {
                e.printStackTrace();
                statusLabel.warn("An unknown error occured");
            } catch (NumberFormatException e) {
                statusLabel.warn("Duration must be an integer");
            }
        });

        Button clearFiltersButton = new Button("Clear filters");
        clearFiltersButton.setOnAction(actionEvent -> {
            titleInputLabel.textField.setText("");
            artistInputLabel.textField.setText("");
            albumInputLabel.textField.setText("");
            durationInputLabel.textField.setText("");
            genreInputLabel.textField.setText("");
            eraInputLabel.textField.setText("");
            populateTable(table, statusLabel);
            statusLabel.confirm("");
        });

        findSongHBox.getChildren().addAll(findSongButton, clearFiltersButton);
        findSongHBox.setAlignment(Pos.CENTER);

        VBox playlistVBox = new VBox();

        ArrayList<String> playlistNames = App.dbConn.getPlaylistNames();
        ComboBox<String> playlistComboBox = new ComboBox<>(FXCollections.observableArrayList(playlistNames));
        playlistComboBox.setEditable(true);

        Button addSongToPlaylistButton = new Button("Add selected song(s) to playlist");
        addSongToPlaylistButton.setOnAction(actionEvent -> {
            ObservableList<Song> selectedSongs = table.getSelectionModel().getSelectedItems();
            if (selectedSongs.isEmpty()) {
                statusLabel.warn("No songs selected");
                return;
            }

            String playlistName = playlistComboBox.getValue();
            if (playlistName == null) {
                statusLabel.warn("No playlist selected");
                return;
            }

            ArrayList<Song> songsAlreadyInPlaylist = new ArrayList<>();

            for (Song song: selectedSongs) {
                try {
                    App.dbConn.addSongToPlaylist(song, playlistName);
                } catch (SQLException e) {
                    if (e.getErrorCode() == 1062) {
                        songsAlreadyInPlaylist.add(song);
                    } else {
                        statusLabel.warn("An unknown error occured");
                        break;
                    }
                }
            }

            int numSongsAdded = selectedSongs.size() - songsAlreadyInPlaylist.size();

            if (songsAlreadyInPlaylist.size() == selectedSongs.size()) {
                statusLabel.warn("Song(s) already in playlist");
            } else if (songsAlreadyInPlaylist.isEmpty()) {
                statusLabel.confirm("Added " + selectedSongs.size() + " songs(s) to playlist " + playlistName);
            } else {
                statusLabel.confirm("Added " + numSongsAdded + "/" + selectedSongs.size() + " new song(s) to playlist " + playlistName);
            }
        });

        playlistVBox.getChildren().addAll(addSongToPlaylistButton, playlistComboBox);
        playlistVBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
                table,
                playlistVBox,
                findInputsVBox,
                findSongHBox,
                statusLabel.getRoot()
        );
    }
}