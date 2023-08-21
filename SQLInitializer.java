/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csc365p1;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author seansponsler
 *         small class to initialize SQL tables on independent system
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
            // ugly recursion since we expect some SQLExceptions in this initialization
            e.printStackTrace();
            i++;
            if (i > 100) {
                // infinite catch
                System.err.println("Something went horribly wrong. i = " + i);
                return;
            }
            initialize();
        }
    }

    public void initializeTables() {
        // artist_name
        String createArtistsTable = "CREATE TABLE Artists ("
                + "id INT AUTO_INCREMENT,"
                + "artist_name VARCHAR(50) NOT NULL,"
                + "PRIMARY KEY (id)"
                + ")";
        tables.add(createArtistsTable);
        String createGenresTable = "CREATE TABLE Genres ("
                + "id INT AUTO_INCREMENT,"
                + "genre_name VARCHAR(50) NOT NULL,"
                + "PRIMARY KEY (id)"
                + ")";
        tables.add(createGenresTable);
        String createErasTable = "CREATE TABLE Genres ("
                + "id INT AUTO_INCREMENT,"
                + "era VARCHAR(8) NOT NULL,"
                + "PRIMARY KEY (id)"
                + ")";
        tables.add(createErasTable);
        String createAlbumsTable = "CREATE TABLE Albums ("
                + "id INT AUTO_INCREMENT,"
                + "album_name VARCHAR(50) NOT NULL,"
                + "artist_id INT NOT NULL,"
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY(artist_id) REFERENCES Artists(id)"
                + ")";
        tables.add(createAlbumsTable);
        // id, song_title, artist_id, album_id, duration(int), genre_id, url(varchar)
        String createSongsTable = "CREATE TABLE Songs ("
                + "id INT AUTO_INCREMENT,"
                + "song_title VARCHAR(50) NOT NULL,"
                + "artist_id INT NOT NULL,"
                + "album_id INT,"
                + "duration INT NOT NULL,"
                + "genre_id INT NOT NULL,"
                + "url VARCHAR(100),"
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY (artist_id) REFERENCES Artists(id),"
                + "FOREIGN KEY (album_id) REFERENCES Albums(id),"
                + "FOREIGN KEY (genre_id) REFERENCES Genres(id)"
                + ")";
        tables.add(createSongsTable);
        String createPlaylistsTable = "CREATE TABLE Playlists ("
                + "id INT AUTO_INCREMENT,"
                + "playlist_name VARCHAR(50) NOT NULL,"
                + "date_created DATE NOT NULL,"
                + "PRIMARY KEY (id)"
                + ")";
        tables.add(createPlaylistsTable);
        String createPlaylistSongsTable = "CREATE TABLE PlaylistSongs ("
                + "id INT AUTO_INCREMENT,"
                + "playlist_id INT NOT NULL,"
                + "song_id INT NOT NULL,"
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY (playlist_id) REFERENCES Playlists(id),"
                + "FOREIGN KEY(song_id) REFERENCES Songs(id)"
                + ")";
        tables.add(createPlaylistSongsTable);
    }
}
