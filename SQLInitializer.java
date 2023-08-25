/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csc365p1;
import java.sql.*;
import java.util.ArrayList;
import com.mycompany.csc365p1.DatabaseConnection;


/**
 *
 * @author seansponsler
 * small class to initialize SQL tables on independent system
 */
public class SQLInitializer {
    private DatabaseConnection databaseConnection;
    private Connection connection;
    private int i;
    private static ArrayList<String> tables;
    
    public SQLInitializer() {
        i = 0;
        tables = new ArrayList();
        initializeTables();
        this.databaseConnection = new DatabaseConnection();
        this.connection = this.databaseConnection.getConnection();
    }
    
    public void initialize() {
        try {
            System.out.println("TABLES SIZE: " + tables.size());
            while (i < tables.size()) {
                PreparedStatement createStmt = connection.prepareStatement(tables.get(i));
                createStmt.executeUpdate();
                createStmt.close();
                i++;
            }
        } catch (SQLException e) {
            //ugly recursion since we expect some SQLExceptions in this initialization
            e.printStackTrace();
            i++;
            if (i > 100) {
                //infinite catch
                System.err.println("Something went horribly wrong. i = " + i);
                return;
            }
            initialize();
        }
    }
    
    public void initializeTables() {
        //artist_name
        String createArtistsTable = "CREATE TABLE Artists ("
                + "artist_name VARCHAR(50) NOT NULL,"
                + "PRIMARY KEY (artist_name)"
                + ")";
        tables.add(createArtistsTable);
        
        String createGenresTable = "CREATE TABLE Genres ("
                + "genre_name VARCHAR(50) NOT NULL,"
                + "PRIMARY KEY (genre_name)"
                + ")";
        tables.add(createGenresTable);
        
        String createAlbumsTable = "CREATE TABLE Albums ("
                + "album_name VARCHAR(50) NOT NULL,"
                + "artist VARCHAR(50) NOT NULL,"
                + "PRIMARY KEY (album_name, artist),"
                + "FOREIGN KEY(artist) REFERENCES Artists(artist_name)"
                + ")";
        tables.add(createAlbumsTable);
        
        //id, song_title, artist_id, album_id, duration(int), genre_id, url(varchar)
        String createSongsTable = "CREATE TABLE Songs ("
                + "song_title VARCHAR(50) NOT NULL,"
                + "artist VARCHAR(50) NOT NULL,"
                + "album VARCHAR(50),"
                + "duration INT,"
                + "genre VARCHAR(50),"
                + "era VARCHAR(8),"
                + "PRIMARY KEY (song_title, artist),"
                + "FOREIGN KEY (artist) REFERENCES Artists(artist_name),"
                + "FOREIGN KEY (album) REFERENCES Albums(album_name),"
                + "FOREIGN KEY (genre) REFERENCES Genres(genre_name),"
                + "FOREIGN KEY (era) REFERENCES Eras(era)"
                + ")";
        tables.add(createSongsTable);
        
        String createPlaylistsTable = "CREATE TABLE Playlists ("
                + "playlist_name VARCHAR(50) NOT NULL,"
                + "date_created DATE NOT NULL,"
                + "PRIMARY KEY (playlist_name)"
                + ")";
        tables.add(createPlaylistsTable);
        
        String createPlaylistSongsTable = "CREATE TABLE PlaylistSongs ("
                + "playlist VARCHAR(50) NOT NULL,"
                + "song VARCHAR(50) NOT NULL,"
                + "PRIMARY KEY (playlist, song),"
                + "FOREIGN KEY (playlist) REFERENCES Playlists(playlist_name),"
                + "FOREIGN KEY(song) REFERENCES Songs(song_title)"
                + ")";
        tables.add(createPlaylistSongsTable);   
    }
}
