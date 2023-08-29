package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import com.mycompany.csc365p1.Song;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AllAlbumsPane extends ChildPane {
    TableView table;
    ComboBox<String> albumNamesComboBox;
    ComboBox<String> artistNamesComboBox;

    AllAlbumsPane(AlbumPane parent) {
        super(parent);
    }

    void updateAlbumNamesItems() {
        String artistName = artistNamesComboBox.getValue();
        ArrayList<String> items;
        if (artistName != null) {
            items = App.dbConn.selectAllAlbumsByArtist(artistName);
        }
        else {
            items = App.dbConn.getAlbumNames();
        }
        albumNamesComboBox.setItems(FXCollections.observableArrayList(items));
    }

    void updateArtistNamesItems() {
        String albumName = albumNamesComboBox.getValue();
        ArrayList<String> items;
        if (albumName != null) {
            items = App.dbConn.selectAllArtistsByAlbum(albumName);
        }
        else {
            items = App.dbConn.getArtistNames();
        }
        artistNamesComboBox.setItems(FXCollections.observableArrayList(items));
    }

    @Override
    void addChildren() {
        super.addChildren();

        Label albumNameLabel = new Label("Album name");
        Label artistNameLabel = new Label("Artist name");

        albumNamesComboBox = new ComboBox<>();
        artistNamesComboBox = new ComboBox<>();

        updateAlbumNamesItems();
        updateArtistNamesItems();

        albumNamesComboBox.setOnAction(actionEvent -> {
            if (albumNamesComboBox.getValue() == null) {
                root.getChildren().remove(this.table);
            } else if (artistNamesComboBox.getValue() != null) {
                if (this.table != null) {
                    root.getChildren().remove(this.table);
                }

                createTable();
                root.getChildren().add(table);
            }
            updateArtistNamesItems();
        });

        artistNamesComboBox.setOnAction(actionEvent -> {
            if (artistNamesComboBox.getValue() == null) {
                root.getChildren().remove(this.table);
            } else if (albumNamesComboBox.getValue() != null) {
                if (this.table != null) {
                    root.getChildren().remove(this.table);
                }

                createTable();
                root.getChildren().add(table);
            }
            updateAlbumNamesItems();
        });

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(actionEvent -> {
            albumNamesComboBox.valueProperty().set(null);
            artistNamesComboBox.valueProperty().set(null);
        });

        root.getChildren().addAll(albumNameLabel, albumNamesComboBox, artistNameLabel, artistNamesComboBox, clearButton);
    }

    void createTable() {
        String artist = artistNamesComboBox.getValue();
        String album = albumNamesComboBox.getValue();

        this.table = new TableView<>();

        List<String> colNames = new ArrayList<>();
        Field[] fields = Song.class.getDeclaredFields();
        for (Field field: fields) {
            colNames.add(field.getName());
        }

        colNames.forEach(colName -> {
            TableColumn col = new TableColumn<>(colName);
            col.setCellValueFactory(new PropertyValueFactory<Song, String>(colName));
            table.getColumns().add(col);
        });

        ArrayList<Song> songs = App.dbConn.selectSongsByArtistAndAlbum(artist, album);
        table.setItems(FXCollections.observableArrayList(songs));
    }
}
