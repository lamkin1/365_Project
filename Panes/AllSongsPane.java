package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import com.mycompany.csc365p1.Song;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

        TableView table = new TableView();

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

        root.getChildren().add(table);
    }
}