package com.mycompany.csc365p1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var label = new Label("Hello, JavaFX!");
        var scene = new Scene(new StackPane(label), 640, 480);
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
