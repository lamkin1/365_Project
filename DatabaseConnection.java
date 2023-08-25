package com.mycompany.csc365p1;

import java.sql.*;
import java.util.Date;

class DatabaseConnection {
    Connection connection;

    DatabaseConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/gr0up0", "gr0up0", "test123");

            System.out.println("Connected to DB");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public String selectArtistByName(String name) {
        try {
            String selectString = "SELECT * FROM Artists WHERE artistName = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Found artist with name: " + name);
                return name;
            } else {
                System.out.println("No artist with name" + name + "found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //RETURNS ARTIST_ID
    public int selectSongByTitle(String title) {
        try {
            String selectString = "SELECT id FROM Songs WHERE songTitle = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
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

    public ResultSet selectAllSongs() throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT * FROM Songs");
        return selectStmt.executeQuery();
    }

    //RETURNS ARTIST_ID
    public String selectPlaylistByName(String name) {
        try {
            String selectString = "SELECT id FROM Playlists WHERE playlistName = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Found playlist id from name: " + name);
                return name;
            } else {
                System.out.println("No playlist id found for: " + name + "!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String selectGenreByName(String name) {
        try {
            String selectString = "SELECT * FROM Genres WHERE genreName = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Found genre with name: " + name);
                return name;
            } else {
                System.out.println("No genre with name " + name + " found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String selectEraByName(String era) {
        try {
            String selectString = "SELECT * FROM Eras WHERE era = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, era);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Found era: " + era);
                return era;
            } else {
                System.out.println("No era called " + era + " found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String selectAlbum(String albumName, String artistName) {
        try {
            String selectString = "SELECT * FROM Albums WHERE albumName = ? AND artist = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, albumName);
            selectStmt.setString(2, artistName);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Found album: " + artistName + ": " + albumName);
                return albumName + artistName;
            } else {
                System.out.println("No album found for: " + artistName + ": " + albumName + "!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertSong(String title, String artist, String album, int duration, String genre, String era) throws SQLException {
        if (!ensureSongIntegrityConstraints(artist, album, genre, era)) {
            System.out.println("Insert song integrity constraints not met");
            return;
        }

        String insertString = "INSERT INTO Songs (songTitle, artist, album, duration, genre, era) VALUES (?,?,?,?,?,?)";
        PreparedStatement insertStmt = connection.prepareStatement(insertString);

        insertStmt.setString(1, title);
        insertStmt.setString(2, artist);
        insertStmt.setString(3, album);
        insertStmt.setInt(4, duration);
        insertStmt.setString(5, genre);
        insertStmt.setString(6, era);

        insertStmt.executeUpdate();
        insertStmt.close();
    }

    public boolean ensureSongIntegrityConstraints(String artist, String album, String genre, String era) {
        try {
            ensureArtist(artist);
            ensureAlbum(album, artist);
            ensureGenre(genre);
            ensureEra(era);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void insertArtist(String name) throws SQLException {
        String insertString = "INSERT INTO Artists (artistName) VALUES (?)";
        PreparedStatement insertStmt = connection.prepareStatement(insertString);
        insertStmt.setString(1, name);
        insertStmt.executeUpdate();
        insertStmt.close();
    }

    public void insertGenre(String genre) {
        try {
            String insertString = "INSERT INTO Genres (genreName) VALUES (?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertString);
            insertStmt.setString(1, genre);
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertEra(String era) {
        try {
            String insertString = "INSERT INTO Eras (era) VALUES (?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertString);
            insertStmt.setString(1, era);
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAlbum(String name, String artistName) {
        try {
            String insertString = "INSERT INTO Albums (albumName, artist) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertString);
            insertStmt.setString(1, name);
            insertStmt.setString(2, artistName);
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertPlaylist(String playlistName) {
        try {
            //playlistName, date_created
            String insertString = "INSERT INTO Playlists (playlistName, date_created) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertString);
            insertStmt.setString(1, playlistName);
            Date currentDate = new Date();
            insertStmt.setDate(2, new java.sql.Date(currentDate.getTime()));
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addSongToPlaylist(String title, String artist, String playlistName) {
        try {
            //playlist_id, song_id
            String insertString = "INSERT INTO PlaylistSongs (playlist_id, song_id) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertString);
            checkPlaylist(playlistName);
            insertStmt.setString(1, playlistName);
            //insertStmt.setString(2, song_id); UPDATE w/ Song Class
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ensureArtist(String artist) throws SQLException {
        boolean existingArtist = App.dbConn.selectArtistByName(artist) != null;
        if (!existingArtist){
            App.dbConn.insertArtist(artist);
        }
    }

    public void ensureGenre(String genre) throws SQLException {
        boolean existingGenre = App.dbConn.selectGenreByName(genre) != null;
        if (!existingGenre) {
            App.dbConn.insertGenre(genre);
        }
    }

    public void ensureEra(String era) throws SQLException {
        boolean existingEra = App.dbConn.selectEraByName(era) != null;
        if (!existingEra) {
            App.dbConn.insertEra(era);
        }
    }

    public void ensureAlbum(String album, String artist) throws SQLException {
        boolean existingAlbum = App.dbConn.selectAlbum(album, artist) != null;
        if (!existingAlbum) {
            App.dbConn.insertAlbum(album, artist);
        }
    }

    public void checkPlaylist(String playlist){
        boolean existingPlaylist = App.dbConn.selectPlaylistByName(playlist) != null;
        if(!existingPlaylist){
            App.dbConn.insertPlaylist(playlist);
        }
    }
}