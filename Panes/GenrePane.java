package com.mycompany.csc365p1.Panes;

import javafx.scene.control.Button;

public class GenrePane extends Pane {
    public GenrePane() {
        super();
    }

    @Override
    public void addChildren() {
        super.addChildren();

        Button showAllGenresButton = new Button("Show all genres");
        showAllGenresButton.setOnAction(actionEvent -> showPane(new AllGenresPane(this).root));

        root.getChildren().add(showAllGenresButton);
    }
}
