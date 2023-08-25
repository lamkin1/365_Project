package com.mycompany.csc365p1;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class InputLabel {
    Label label;
    TextField textField;
    HBox root;

    InputLabel(String strLabel) {
        this(strLabel, false);
    }

    InputLabel(String strLabel, boolean optional) {
        Label label = new Label(strLabel + (optional ? " (optional)" : "") + ":");
        this.label = label;

        TextField textField = new TextField();
        this.textField = textField;

        HBox hbox = new HBox();
        hbox.getChildren().addAll(label, textField);
        hbox.setAlignment(Pos.CENTER);
        root = hbox;
    }

    public String getText() {
        return this.textField.getText();
    }
    public HBox getRoot() {
        return root;
    }
}
