package com.mycompany.csc365p1;

import java.sql.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {
    final int WIDTH = 1000;
    final int HEIGHT = 800;
    @Override
    public void start(Stage stage) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        TitleScreen titleScreen = new TitleScreen(WIDTH, HEIGHT);

        Scene scene = new Scene(titleScreen.getRoot(), WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static void insertSong(String title, String date, int artist_id, int duration, int genre_id, String URL) {
        
    }
    public static void insertArtist(String name) {
        
    }
    public static void insertGenre(String genre) {
        
    }
    public static void insertAlbum(String name, int artist_id) {
        
    }
    public static void insertPlaylist(String playlist_name, String creation_date) {
        
    }
    public static void addSongToPlaylist(int song_id, int playlist_id) {
        
    }
}

class DatabaseConnection {
    Connection connection;

    DatabaseConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/dlching?user=dlching&password=028545432");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}

class TitleScreen {
    VBox root;
    final int width;
    final int height;

    TitleScreen(int width, int height) {
        this.width = width;
        this.height = height;

        root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(0.03125 * this.height);
        root.setFillWidth(true);

        addChildren();
    }

    void addChildren() {
        root.getChildren().removeAll();

        List<Button> buttons = Arrays.asList(
                new Button("Songs"),
                new Button("Artists"),
                new Button("Albums"),
                new Button("Genres"),
                new Button("Playlists")
        );

        for (Button button: buttons) {
            button.setMaxWidth(0.8 * this.width);
            button.setStyle(String.format("-fx-font-size: %d", Math.round(0.046875 * this.width)));
            button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
            root.getChildren().add(button);
        }
    }

    public VBox getRoot() {
        return root;
    }
}