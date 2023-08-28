package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.Song;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class AllAlbumsPane extends ChildPane {
    AllAlbumsPane(AlbumPane parent) {
        super(parent);
    }

    @Override
    void addChildren() {
        super.addChildren();

        TableView table = new TableView();

        ArrayList<Song> albums = new ArrayList<>();

        root.getChildren().add(table);
    }
}
