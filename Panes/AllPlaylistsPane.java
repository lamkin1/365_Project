package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import com.mycompany.csc365p1.Song;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllPlaylistsPane extends ChildPane {
    TableView table;
    AllPlaylistsPane(PlaylistPane parent) {
        super(parent);
    }

    @Override
    void addChildren() {
        super.addChildren();

        ArrayList<String> playlistNames = App.dbConn.getPlaylistNames();
        ComboBox<String> playlistComboBox = new ComboBox<>(FXCollections.observableArrayList(playlistNames));
        playlistComboBox.setOnAction(actionEvent -> {
            if (this.table != null) {
                root.getChildren().remove(this.table);
            }
            createPlaylistTable(playlistComboBox.getValue());
            root.getChildren().add(table);
        });

        root.getChildren().add(playlistComboBox);
    }

    void createPlaylistTable(String playlistName) {
        this.table = new TableView();

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

        ArrayList<Song> playlistSongs = App.dbConn.getTracklistFromPlaylist(playlistName);
        table.setItems(FXCollections.observableArrayList(playlistSongs));
    }
}
