package com.mycompany.csc365p1.Panes;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class Pane {
    VBox root;

    protected Pane() {
        root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setFillWidth(true);

        addChildren();
    }

    public void addChildren() {
        root.getChildren().clear();
    }

    public void showPane(VBox childRoot) {
        root.getChildren().clear();
        root.getChildren().add(childRoot);
    }

    public VBox getRoot() {
        return root;
    }
}