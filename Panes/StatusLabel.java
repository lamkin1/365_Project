package com.mycompany.csc365p1.Panes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatusLabel {
    VBox root;
    Label label;

    StatusLabel() {
        label = new Label();

        root = new VBox();
        root.getChildren().add(label);
        root.setAlignment(Pos.CENTER);
    }

    private void setText(String text) {
        label.setText(text);
    }
    public void warn(String msg) {
        label.setStyle("-fx-text-fill: red");
        setText(msg);
    }

    public void confirm(String msg) {
        label.setStyle("-fx-text-fill: green");
        setText(msg);
    }

    public VBox getRoot() {
        return root;
    }
}
