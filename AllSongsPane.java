package com.mycompany.csc365p1;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllSongsPane {
    VBox root;
    SongPane parent;

    AllSongsPane(SongPane parent) {
        this.parent = parent;

        root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: lightblue");

        addChildren();
    }

    void addChildren() {
        root.getChildren().clear();

        Button returnButton = new Button("Return");
        returnButton.setOnAction(actionEvent -> parent.addChildren());

        TableView table = new TableView();

        ArrayList<Song> songs = new ArrayList<>();

        try {
            ResultSet rs = App.dbConn.selectAllSongs();
            ResultSetMetaData metaData = rs.getMetaData();

            for (int i = 1; i < metaData.getColumnCount(); i++) {
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

        root.getChildren().addAll(returnButton, table);
    }

    public VBox getRoot() {
        return root;
    }
}
