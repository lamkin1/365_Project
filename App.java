package com.mycompany.csc365p1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    final int WIDTH = 1000;
    final int HEIGHT = 800;

    static DatabaseConnection dbConn;

    @Override
    public void start(Stage stage) {
        dbConn = new DatabaseConnection();
        SQLInitializer init = new SQLInitializer();
        CSVHelper csvh = new CSVHelper();

        //csvh.importSongs();
        //init.initialize();
        
        //test(dbConn);

        TitleScreen titleScreen = new TitleScreen(WIDTH, HEIGHT);

        Scene scene = new Scene(titleScreen.getRoot(), WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void test() {
        dbConn.insertArtist("Eminem"); //id 1
        dbConn.insertGenre("2000s"); //id 1
        dbConn.insertSong("Lose Yourself", 1, 265, 1); //id 1
        if (dbConn.selectArtistByName("Eminem").equals("Eminem")) System.out.println("Select artist by name is functional.");
        else System.out.println("ERROR: Select artist by name IS NOT functional!");
        if (dbConn.selectSongByTitle("Lose Yourself") == 1) System.out.println("Select song by title is functional.");
        else System.out.println("ERROR: Select song by title IS NOT functional!");
        if (dbConn.selectGenreByName("2000s").equals("2000s")) System.out.println("Select genre by name is functional.");
        else System.out.println("ERROR: Select genre by name IS NOT functional!");
    }
}
