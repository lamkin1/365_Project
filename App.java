package com.mycompany.csc365p1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    final int WIDTH = 1200;
    final int HEIGHT = 800;

    public static DatabaseConnection dbConn;

    @Override
    public void start(Stage stage) {
        dbConn = new DatabaseConnection();
        CSVHelper csvh = new CSVHelper();

        //csvh.importSongs();

        TitleScreen titleScreen = new TitleScreen();

        Image icon = new Image(getClass().getResourceAsStream("assets/musicIcon.png"));

        stage.getIcons().add(icon);

        stage.setTitle("Music Library");

        Scene scene = new Scene(titleScreen.getRoot(), WIDTH, HEIGHT);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}