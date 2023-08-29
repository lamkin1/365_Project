package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import com.mycompany.csc365p1.Song;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AllGenresPane extends ChildPane {
    TableView table;
    AllGenresPane(GenrePane parent) {
        super(parent);
    }

    @Override
    void addChildren() {
        super.addChildren();

        ArrayList<String> genreNames = App.dbConn.getGenreNames();
        ComboBox<String> genreComboBox = new ComboBox<>(FXCollections.observableArrayList(genreNames));
        genreComboBox.setOnAction(actionEvent -> {
            if (this.table != null) {
                root.getChildren().remove(this.table);
            }
            createPlaylistTable(genreComboBox.getValue());
            root.getChildren().add(table);
        });

        root.getChildren().add(genreComboBox);
    }

    void createPlaylistTable(String genre) {
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

        ArrayList<Song> songs = App.dbConn.selectSongsByGenre(genre);
        table.setItems(FXCollections.observableArrayList(songs));
    }
}
