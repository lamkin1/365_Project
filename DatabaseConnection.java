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
            String selectString = "SELECT * FROM Artists WHERE artist_name = ?";
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
            String selectString = "SELECT id FROM Songs WHERE song_title = ?";
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

    //RETURNS ARTIST_ID
    public String selectPlaylistByName(String name) {
        try {
            String selectString = "SELECT id FROM Playlists WHERE playlist_name = ?";
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
            String selectString = "SELECT * FROM Genres WHERE genre_name = ?";
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
            String selectString = "SELECT * FROM Genres WHERE era = ?";
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

    public String selectAlbum(String album_name, String artist_name) {
        try {
            String selectString = "SELECT * FROM Albums WHERE album_name = ? AND artist = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, album_name);
            selectStmt.setString(2, artist_name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Found album: " + artist_name + ": " + album_name);
                return album_name + artist_name;
            } else {
                System.out.println("No album found for: " + artist_name + ": " + album_name + "!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertSong(String title, String artist, String album, int duration, String genre, String era) throws SQLException{
        try {
            String insertString = "INSERT INTO Songs (song_title, artist, album, duration, genre, era) VALUES (?,?,?,?,?,?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertString);
            insertStmt.setString(1, title);
            checkArtist(artist);
            insertStmt.setString(2, artist);
            checkAlbum(artist, album);
            insertStmt.setString(3, album);
            insertStmt.setInt(4, duration);
            checkGenre(genre);
            insertStmt.setString(5, genre);
            checkEra(era);
            insertStmt.setString(6, era);
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSong(String title, String artist, int duration, String genre_name) throws SQLException {
        try {
            String insertString = "INSERT INTO Songs (song_title, artist, duration, genre) VALUES (?,?,?,?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertString);
            insertStmt.setString(1, title);
            checkArtist(artist);
            insertStmt.setString(2, artist);
            insertStmt.setInt(3, duration);
            checkGenre(genre_name);
            insertStmt.setString(4, genre_name);
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //no album constructor
    public void insertSong(String title, String artist) throws SQLException {
        //auto commit might just work instead of manually committing after each insert
        //connection.setAutoCommit(false);

        //id, song_title, artist_id, album_id, duration(int), genre_id
        String insertString = "INSERT INTO Songs (song_title, artist) VALUES (?,?)";
        PreparedStatement insertStmt = connection.prepareStatement(insertString);
        insertStmt.setString(1, title);
        checkArtist(artist);
        insertStmt.setString(2, artist);
        insertStmt.executeUpdate();
        //connection.commit();
        insertStmt.close();
    }

    public void insertArtist(String name) throws SQLException {
        String insertString = "INSERT INTO Artists (artist_name) VALUES (?)";
        PreparedStatement insertStmt = connection.prepareStatement(insertString);
        insertStmt.setString(1, name);
        insertStmt.executeUpdate();
        insertStmt.close();
    }

    public void insertGenre(String genre) {
        try {
            String insertString = "INSERT INTO Genres (genre_name) VALUES (?)";
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

    public void insertAlbum(String name, String artist_name) {
        try {
            String insertString = "INSERT INTO Albums (album_name, artist) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertString);
            insertStmt.setString(1, name);
            insertStmt.setString(2, artist_name);
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertPlaylist(String playlist_name) {
        try {
            //playlist_name, date_created
            String insertString = "INSERT INTO Playlists (playlist_name, date_created) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertString);
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
    public void addSongToPlaylist(String title, String artist, String playlist_name) {
        try {
            //playlist_id, song_id
            String insertString = "INSERT INTO PlaylistSongs (playlist_id, song_id) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertString);
            checkPlaylist(playlist_name);
            insertStmt.setString(1, playlist_name);
            //insertStmt.setString(2, song_id); UPDATE w/ Song Class
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkArtist(String artist) throws SQLException {
        boolean existingArtist = App.dbConn.selectArtistByName(artist) != null;
        if(!existingArtist){
            App.dbConn.insertArtist(artist);
        }
    }

    public void checkGenre(String genre) throws SQLException {
        boolean existingGenre = App.dbConn.selectGenreByName(genre) != null;
        if (!existingGenre) {
            App.dbConn.insertGenre(genre);
        }
    }

    public void checkEra(String era) throws SQLException {
        boolean existingEra = App.dbConn.selectEraByName(era) != null;
        if (!existingEra) {
            App.dbConn.insertEra(era);
        }
    }

    public void checkAlbum(String album, String artist) throws SQLException {
        checkArtist(artist);
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