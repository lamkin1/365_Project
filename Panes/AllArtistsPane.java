package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import com.mycompany.csc365p1.Artist;
import com.mycompany.csc365p1.Song;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllArtistsPane extends ChildPane {
    public AllArtistsPane(Pane parent) {
        super(parent);
    }

    void addChildren() {
        super.addChildren();

        TableView table = new TableView();

        ArrayList<Artist> artists = new ArrayList<>();

        try {
            ResultSet rs = App.dbConn.selectAllArtists();
            ResultSetMetaData metaData = rs.getMetaData();

            for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
                String colName = metaData.getColumnName(i);
                TableColumn col = new TableColumn<>(colName);
                col.setCellValueFactory(new PropertyValueFactory<Song, String>(colName));
                table.getColumns().add(col);
            }

            while (rs.next()) {
                artists.add(new Artist(
                        rs.getString("artistName")
                ));
            }

            table.setItems(FXCollections.observableArrayList(artists));
        } catch (SQLException e) {
            System.out.println("Select all songs error");
        }

        root.getChildren().add(table);
    }
}
