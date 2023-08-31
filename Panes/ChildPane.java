package com.mycompany.csc365p1.Panes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ChildPane {
    VBox root;
    Pane parent;

    ChildPane(Pane parent) {
        this.parent = parent;

        root = new VBox();
        root.setAlignment(Pos.TOP_LEFT);
        root.setSpacing(10);
        VBox.setVgrow(root, Priority.ALWAYS);

        addChildren();
    }

    void addChildren() {
        root.getChildren().clear();

        Button returnButton = new Button("Return");
        returnButton.getStyleClass().add("return-button");
        returnButton.setOnAction(actionEvent -> parent.addChildren());
        root.getChildren().add(returnButton);
    }

    public VBox getRoot() {
        return root;
    }
}
