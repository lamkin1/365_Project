package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import com.mycompany.csc365p1.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllPlaylistsPane extends ChildPane {
    AllPlaylistsPane(PlaylistPane parent) {
        super(parent);
    }

    @Override
    void addChildren() {
        super.addChildren();

        TableView table = new TableView<>();

        StatusLabel statusLabel = new StatusLabel();

        ArrayList<String> playlistNames = App.dbConn.getPlaylistNames();

        HBox inputsHBox = new HBox();
        inputsHBox.setAlignment(Pos.CENTER);
        inputsHBox.setSpacing(10);

        ObservableList<String> observablePlaylistNames = FXCollections.observableArrayList(playlistNames);
        ComboBox<String> playlistComboBox = new ComboBox<>(observablePlaylistNames);
        playlistComboBox.setOnAction(actionEvent -> populateTable(table, playlistComboBox.getValue()));

        Button deletePlaylistButton = new Button("Delete playlist");
        deletePlaylistButton.setOnAction(actionEvent -> {
            try {
                String playlistName = playlistComboBox.getValue();
                if (playlistName == null) {
                    statusLabel.warn("No playlist selected");
                    return;
                }

                App.dbConn.deletePlaylist(playlistName);
                observablePlaylistNames.remove(playlistName);
                statusLabel.confirm("Deleted playlist " + playlistName);
                populateTable(table, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        inputsHBox.getChildren().addAll(playlistComboBox, deletePlaylistButton);

        root.getChildren().addAll(inputsHBox, table, statusLabel.getRoot());
    }

    void populateTable(TableView table, String playlistName) {
        table.getColumns().clear();

        Field[] fields = Song.class.getDeclaredFields();
        ArrayList<Field> nonStaticFields = new ArrayList<>();

        for (Field field: fields) {
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                nonStaticFields.add(field);
            }
        }

        for (Field field: nonStaticFields) {
            String colName = field.getName();
            String attrName = Song.attributeTitles.get(colName);
            TableColumn col = new TableColumn<>(attrName);
            col.setCellValueFactory(new PropertyValueFactory<Song, String>(colName));
            table.getColumns().add(col);
        }

        ArrayList<Song> playlistSongs = App.dbConn.getTracklistFromPlaylist(playlistName);
        table.setItems(FXCollections.observableArrayList(playlistSongs));
    }
}
