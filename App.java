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
import java.util.Date;

/**
 * JavaFX App
 */
public class App extends Application {
    final int WIDTH = 1000;
    final int HEIGHT = 800;
    private static DatabaseConnection databaseConnection;
    @Override
    public void start(Stage stage) {
        databaseConnection = new DatabaseConnection();
        SQLInitializer init = new SQLInitializer();
        //init.initialize();
        
        /*-----------TESTS---------------*/
        insertArtist("Eminem"); //id 1
        insertGenre("2000s"); //id 1
        insertSong("Lose Yourself", 1, 265, 1); //id 1
        if (selectArtistByName("Eminem") == 1) System.out.println("Select artist by name is functional.");
        else System.out.println("ERROR: Select artist by name IS NOT functional!");
        if (selectSongByTitle("Lose Yourself") == 1) System.out.println("Select song by title is functional.");
        else System.out.println("ERROR: Select song by title IS NOT functional!");
        if (selectGenreByName("2000s") == 1) System.out.println("Select genre by name is functional.");
        else System.out.println("ERROR: Select genre by name IS NOT functional!");
        /*------------------------------*/
        
        
        TitleScreen titleScreen = new TitleScreen(WIDTH, HEIGHT);

        Scene scene = new Scene(titleScreen.getRoot(), WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
    //RETURNS ARTIST_ID
    public static int selectArtistByName(String name) {
        try {
            String selectString = "SELECT id FROM Artists WHERE artist_name = ?";
            PreparedStatement selectStmt = databaseConnection.getConnection().prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                int artist_id = rs.getInt("id");
                System.out.println("Found artist id from name: " + name + ": " + artist_id);
                return artist_id;
            } else {
                System.out.println("No artist id found for: " + name + "!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    //RETURNS ARTIST_ID
    public static int selectSongByTitle(String title) {
        try {
            String selectString = "SELECT id FROM Songs WHERE song_title = ?";
            PreparedStatement selectStmt = databaseConnection.getConnection().prepareStatement(selectString);
            selectStmt.setString(1, title);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                int song_id = rs.getInt("id");
                System.out.println("Found song id from title: " + title + ": " + song_id);
                return song_id;
            } else {
                System.out.println("No song id found for: " + title + "!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    //RETURNS ARTIST_ID
    public static int selectPlaylistByName(String name) {
        try {
            String selectString = "SELECT id FROM Playlists WHERE playlist_name = ?";
            PreparedStatement selectStmt = databaseConnection.getConnection().prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                int playlist_id = rs.getInt("id");
                System.out.println("Found playlist id from name: " + name + ": " + playlist_id);
                return playlist_id;
            } else {
                System.out.println("No playlist id found for: " + name + "!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    //RETURNS ARTIST_ID
    public static int selectGenreByName(String name) {
        try {
            String selectString = "SELECT id FROM Genres WHERE genre_name = ?";
            PreparedStatement selectStmt = databaseConnection.getConnection().prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                int genre_id = rs.getInt("id");
                System.out.println("Found genre id from name: " + name + ": " + genre_id);
                return genre_id;
            } else {
                System.out.println("No genre id found for: " + name + "!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    //RETURNS ARTIST_ID
    public static int selectAlbumByName(String name) {
        try {
            String selectString = "SELECT id FROM Albums WHERE album_name = ?";
            PreparedStatement selectStmt = databaseConnection.getConnection().prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                int album_id = rs.getInt("id");
                System.out.println("Found album id from name: " + name + ": " + album_id);
                return album_id;
            } else {
                System.out.println("No album id found for: " + name + "!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public static void insertSong(String title, int artist_id, int album_id, int duration, int genre_id, String URL) {
        try {
            //auto commit might just work instead of manually committing after each insert
            //databaseConnection.getConnection().setAutoCommit(false);
            
            //id, song_title, artist_id, album_id, duration(int), genre_id, url(varchar)
            String insertString = "INSERT INTO Songs (song_title, artist_id, album_id, duration, genre_id, url) VALUES (?,?,?,?,?,?)";
            PreparedStatement insertStmt = databaseConnection.getConnection().prepareStatement(insertString);
            insertStmt.setString(1, title);
            insertStmt.setInt(2, artist_id);
            insertStmt.setInt(3, album_id);
            insertStmt.setInt(4, duration);
            insertStmt.setInt(5, genre_id);
            insertStmt.setString(6, URL);
            insertStmt.executeUpdate();
            //databaseConnection.getConnection().commit();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //no URL constructor
    public static void insertSong(String title, int artist_id, int album_id, int duration, int genre_id) {
        try {
            //auto commit might just work instead of manually committing after each insert
            //databaseConnection.getConnection().setAutoCommit(false);
            
            //id, song_title, artist_id, album_id, duration(int), genre_id, url(varchar)
            String insertString = "INSERT INTO Songs (song_title, artist_id, album_id, duration, genre_id) VALUES (?,?,?,?,?)";
            PreparedStatement insertStmt = databaseConnection.getConnection().prepareStatement(insertString);
            insertStmt.setString(1, title);
            insertStmt.setInt(2, artist_id);
            insertStmt.setInt(3, album_id);
            insertStmt.setInt(4, duration);
            insertStmt.setInt(5, genre_id);
            insertStmt.executeUpdate();
            //databaseConnection.getConnection().commit();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //no URL + no album constructor
    public static void insertSong(String title, int artist_id, int duration, int genre_id) {
        try {
            //auto commit might just work instead of manually committing after each insert
            //databaseConnection.getConnection().setAutoCommit(false);
            
            //id, song_title, artist_id, album_id, duration(int), genre_id, url(varchar)
            String insertString = "INSERT INTO Songs (song_title, artist_id, duration, genre_id) VALUES (?,?,?,?)";
            PreparedStatement insertStmt = databaseConnection.getConnection().prepareStatement(insertString);
            insertStmt.setString(1, title);
            insertStmt.setInt(2, artist_id);
            insertStmt.setInt(3, duration);
            insertStmt.setInt(4, genre_id);
            insertStmt.executeUpdate();
            //databaseConnection.getConnection().commit();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertArtist(String name) {
        try {
            String insertString = "INSERT INTO Artists (artist_name) VALUES (?)";
            PreparedStatement insertStmt = databaseConnection.getConnection().prepareStatement(insertString);
            insertStmt.setString(1, name);
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertGenre(String genre) {
        try {
            String insertString = "INSERT INTO Genres (genre_name) VALUES (?)";
            PreparedStatement insertStmt = databaseConnection.getConnection().prepareStatement(insertString);
            insertStmt.setString(1, genre);
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertAlbum(String name, int artist_id) {
        try {
            String insertString = "INSERT INTO Albums (album_name, artist_id) VALUES (?, ?)";
            PreparedStatement insertStmt = databaseConnection.getConnection().prepareStatement(insertString);
            insertStmt.setString(1, name);
            insertStmt.setInt(2, artist_id);
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertPlaylist(String playlist_name) {
        try {
            //playlist_name, date_created
            String insertString = "INSERT INTO Playlists (playlist_name, date_created) VALUES (?, ?)";
            PreparedStatement insertStmt = databaseConnection.getConnection().prepareStatement(insertString);
            insertStmt.setString(1, playlist_name);
            Date currentDate = new Date();
            insertStmt.setDate(2, new java.sql.Date(currentDate.getTime()));
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addSongToPlaylist(int song_id, int playlist_id) {
        try {
            //playlist_id, song_id
            String insertString = "INSERT INTO PlaylistSongs (playlist_id, song_id) VALUES (?, ?)";
            PreparedStatement insertStmt = databaseConnection.getConnection().prepareStatement(insertString);
            insertStmt.setInt(1, playlist_id);
            insertStmt.setInt(2, song_id);
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class DatabaseConnection {
    Connection connection;

    DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/test", "root", "123");
            //             "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/ssponsle, "ssponsle", "028858030");
            System.out.println("Connected to DB");
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
