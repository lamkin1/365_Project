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
        //artistName
        String createArtistsTable = "CREATE TABLE Artists ("
                + "artistName VARCHAR(50) NOT NULL,"
                + "PRIMARY KEY (artistName)"
                + ")";
        tables.add(createArtistsTable);
        
        String createGenresTable = "CREATE TABLE Genres ("
                + "genreName VARCHAR(50) NOT NULL,"
                + "PRIMARY KEY (genreName)"
                + ")";
        tables.add(createGenresTable);
        
        String createAlbumsTable = "CREATE TABLE Albums ("
                + "albumName VARCHAR(50) NOT NULL,"
                + "artist VARCHAR(50) NOT NULL,"
                + "PRIMARY KEY (albumName, artist),"
                + "FOREIGN KEY(artist) REFERENCES Artists(artistName)"
                + ")";
        tables.add(createAlbumsTable);
        
        //id, songTitle, artist_id, album_id, duration(int), genre_id, url(varchar)
        String createSongsTable = "CREATE TABLE Songs ("
                + "songTitle VARCHAR(50) NOT NULL,"
                + "artist VARCHAR(50) NOT NULL,"
                + "album VARCHAR(50),"
                + "duration INT,"
                + "genre VARCHAR(50),"
                + "era VARCHAR(8),"
                + "PRIMARY KEY (songTitle, artist),"
                + "FOREIGN KEY (artist) REFERENCES Artists(artistName),"
                + "FOREIGN KEY (album) REFERENCES Albums(albumName),"
                + "FOREIGN KEY (genre) REFERENCES Genres(genreName),"
                + "FOREIGN KEY (era) REFERENCES Eras(era)"
                + ")";
        tables.add(createSongsTable);
        
        String createPlaylistsTable = "CREATE TABLE Playlists ("
                + "playlistName VARCHAR(50) NOT NULL,"
                + "date_created DATE NOT NULL,"
                + "PRIMARY KEY (playlistName)"
                + ")";
        tables.add(createPlaylistsTable);
        
        String createPlaylistSongsTable = "CREATE TABLE PlaylistSongs ("
                + "playlist VARCHAR(50) NOT NULL,"
                + "song VARCHAR(50) NOT NULL,"
                + "PRIMARY KEY (playlist, song),"
                + "FOREIGN KEY (playlist) REFERENCES Playlists(playlistName),"
                + "FOREIGN KEY(song) REFERENCES Songs(songTitle)"
                + ")";
        tables.add(createPlaylistSongsTable);   
    }
}
