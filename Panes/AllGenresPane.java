package com.mycompany.csc365p1.Panes;

import com.mycompany.csc365p1.Genre;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class AllGenresPane extends ChildPane {
    AllGenresPane(GenrePane parent) {
        super(parent);
    }

    @Override
    void addChildren() {
        super.addChildren();

        TableView table = new TableView();

        ArrayList<Genre> genres = new ArrayList<>();

        root.getChildren().add(table);
    }
}
