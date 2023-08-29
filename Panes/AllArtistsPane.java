package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.App;
import com.mycompany.csc365p1.Artist;
import com.mycompany.csc365p1.Song;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AllArtistsPane extends ChildPane {
    TableView table;
    public AllArtistsPane(Pane parent) {
        super(parent);
    }

    void addChildren() {
        super.addChildren();

        ArrayList<String> artistNames = App.dbConn.getArtistNames();
        ComboBox<String> artistNamesComboBox = new ComboBox<>(FXCollections.observableArrayList(artistNames));

        artistNamesComboBox.setOnAction(actionEvent -> {
            if (this.table != null) {
                root.getChildren().remove(this.table);
            }
            createArtistsTable(artistNamesComboBox.getValue());
            root.getChildren().add(table);
        });

        root.getChildren().add(artistNamesComboBox);
    }

    void createArtistsTable(String artistName) {
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

        ArrayList<Song> artistSongs = App.dbConn.selectSongsByArtist(artistName);
        table.setItems(FXCollections.observableArrayList(artistSongs));
    }
}
