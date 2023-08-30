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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllSongsPane extends ChildPane {
    ObservableList<Song> observableSongs;
    AllSongsPane(SongPane parent) {
        super(parent);
        VBox.setVgrow(root, Priority.ALWAYS);
    }

    void populateTable(TableView table, Label statusLabel) {
        ArrayList<Song> songs = new ArrayList<>();

        table.getColumns().clear();

        try {
            ResultSet rs = App.dbConn.selectAllSongs();
            ResultSetMetaData metaData = rs.getMetaData();

            for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
                String colName = metaData.getColumnName(i);
                TableColumn<Song, String> col = new TableColumn<>(colName);
                col.setCellValueFactory(new PropertyValueFactory<>(colName));

                col.setCellValueFactory(cellData -> {
                    Song song = cellData.getValue();
                    StringProperty cellValue = song.getPropertyByName(colName);
                    return cellValue;
                });

                col.setOnEditCommit(event -> {
                    Song oldSong = event.getRowValue();
                    Song newSong = new Song(oldSong.getSongTitle(), oldSong.getArtist(), oldSong.getAlbum(), oldSong.getDuration(), oldSong.getGenre(), oldSong.getEra());

                    String newValue = event.getNewValue();
                    try {
                        newSong.setPropertyByName(colName, newValue);
                        try {
                            App.dbConn.updateSong(oldSong, newSong);

                            oldSong.setSongTitle(newSong.getSongTitle());
                            oldSong.setArtist(newSong.getArtist());
                            oldSong.setAlbum(newSong.getAlbum());
                            oldSong.setDuration(newSong.getDuration());
                            oldSong.setGenre(newSong.getGenre());
                            oldSong.setEra(newSong.getEra());

                            statusLabel.setStyle("-fx-text-fill: green;");
                            statusLabel.setText("Updated song");
                        } catch (SQLException e) {
                            e.printStackTrace();
                            statusLabel.setStyle("-fx-text-fill: red;");
                            statusLabel.setText("Error updating song");
                        }
                    } catch (NumberFormatException e) {
                        statusLabel.setStyle("-fx-text-fill: red;");
                        statusLabel.setText("Invalid duration");
                    }
                });

                col.setCellFactory(TextFieldTableCell.forTableColumn());

                table.getColumns().add(col);
            }

            TableColumn<Song, Button> deleteCol = new TableColumn<>("Delete");
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
                                    statusLabel.setStyle("-fx-text-fill: green;");
                                    statusLabel.setText("Deleted song!");
                                } else {
                                    statusLabel.setText("An error occured while deleting song");
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
                    statusLabel.setStyle("-fx-text-fill: green;");
                    statusLabel.setText(numSelectedRows + " selected row(s)");
                }
            });
        } catch (SQLException e) {
            System.out.println("Select all songs error");
        }
    }

    @Override
    void addChildren() {
        super.addChildren();

        Label statusLabel = new Label();

        TableView table = new TableView();
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setEditable(true);
        table.setStyle("-fx-selection-bar-non-focused: -fx-selection-bar;");

        populateTable(table, statusLabel);

        InputLabel titleInputLabel = new InputLabel("Title");
        InputLabel artistInputLabel = new InputLabel("Artist");
        InputLabel albumInputLabel = new InputLabel("Album");
        InputLabel durationInputLabel = new InputLabel("Duration");
        InputLabel genreInputLabel = new InputLabel("Genre");
        InputLabel eraInputLabel = new InputLabel("Era");

        VBox findSongVBox = new VBox();

        Button findSongButton = new Button("Find song(s)");
        findSongButton.setOnAction(actionEvent -> {
            String title = titleInputLabel.getText();
            String artist = artistInputLabel.getText();
            String album = albumInputLabel.getText();
            String duration = durationInputLabel.getText();
            String genre = genreInputLabel.getText();
            String era = eraInputLabel.getText();

            try {
                ArrayList<Integer> tableIndices = new ArrayList<>();
                ArrayList<Song> foundSongs = App.dbConn.findSongs(title, artist, album, duration, genre, era);
                for (Song song: foundSongs) {
                    for (int i = 0; i < observableSongs.size(); i++) {
                        if (observableSongs.get(i).equals(song)) {
                            tableIndices.add(i);
                            break;
                        }
                    }
                }

                table.getSelectionModel().clearSelection();

                if (tableIndices.isEmpty()) {
                    statusLabel.setStyle("-fx-text-fill: red;");
                    statusLabel.setText("No songs found");
                }

                for (Integer tableIndex: tableIndices) {
                    table.getSelectionModel().selectIndices(tableIndex);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                statusLabel.setStyle("-fx-text-fill: red;");
                statusLabel.setText("An unknown error occured");
            }
        });

        findSongVBox.getChildren().add(findSongButton);
        findSongVBox.setAlignment(Pos.CENTER);

        VBox playlistVBox = new VBox();

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

        playlistVBox.getChildren().addAll(addSongToPlaylistButton, playlistComboBox);
        playlistVBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
                table,
                playlistVBox,
                titleInputLabel.root,
                artistInputLabel.root,
                albumInputLabel.root,
                durationInputLabel.root,
                genreInputLabel.root,
                eraInputLabel.root,
                findSongVBox,
                statusLabel
        );
    }
}