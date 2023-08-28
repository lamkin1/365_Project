package com.mycompany.csc365p1;

import java.sql.*;
import java.util.ArrayList;
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

    public Artist selectArtistByName(String name) {
        try {
            String selectString = "SELECT * FROM Artists WHERE artistName = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                return new Artist(name);
            } else {
                System.out.println("No artist with name" + name + "found");
            }
            selectStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //song_title, artist, album, duration, genre, era
    public Song selectSongByTitle(String title) {
        try {
            String selectString = "SELECT id FROM Songs WHERE songTitle = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, title);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                String artist = rs.getString("artist");
                String album = rs.getString("album");
                int duration = rs.getInt("duration");
                String genre = rs.getString("genre");
                String era = rs.getString("era");
                return new Song(title, artist, album, duration, genre, era);
            } else {
                System.out.println("No song found for: " + title + "!");
            }
            selectStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet selectAllSongs() throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT * FROM Songs");
        return selectStmt.executeQuery();
    }
    
    public ResultSet selectAllPlaylists() throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT * FROM PlaylistSongs ORDER BY playlist asc;");
        return selectStmt.executeQuery();
    }
    
    public ResultSet selectAllAlbums() throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT album, artist, songTitle FROM Songs ORDER BY album asc;");
        return selectStmt.executeQuery();
    }
    
    public ResultSet selectAllArtists() throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT * FROM Artists");
        return selectStmt.executeQuery();
    }
    
    public ResultSet selectAllGenres() throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT * FROM Genres");
        return selectStmt.executeQuery();
    }
    
    public ResultSet selectAllEras() throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT * FROM Era");
        return selectStmt.executeQuery();
    }
    
    public ResultSet selectAllFromGenre(String genreName) throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT songTitle FROM Songs WHERE genre = ?");
        selectStmt.setString(1, genreName);
        return selectStmt.executeQuery();
    }
    
    public ResultSet selectAllFromAlbum(String albumName) throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT songTitle FROM Songs WHERE album = ?");
        selectStmt.setString(1, albumName);
        return selectStmt.executeQuery();
    }
    
    //i hate this one
    public ResultSet selectAllByDuration(int duration) throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT songTitle FROM Songs WHERE duration = ?");
        selectStmt.setInt(1, duration);
        return selectStmt.executeQuery();
    }
    
    public ResultSet selectAllByArtist(String name) throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT songTitle FROM Songs WHERE artist = ?");
        selectStmt.setString(1, name);
        return selectStmt.executeQuery();
    }
    
    public ResultSet selectAllFromPlaylist(String playlistName) throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT song FROM PlaylistSongs WHERE playlist = ?");
        selectStmt.setString(1, playlistName);
        return selectStmt.executeQuery();
    }

    public Playlist selectPlaylistByName(String name) {
        try {
            String selectString = "SELECT * FROM Playlists WHERE playlistName = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                //CURDATE()
                ArrayList<Song> tracklist = getTracklistFromPlaylist(name);
                return new Playlist(name, tracklist);
            } else {
                System.out.println("No playlist id found for: " + name + "!");
            }
            selectStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Album selectAlbumByName(String name) {
        try {
            String selectString = "SELECT artist FROM Albums WHERE albumName = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                String artist = rs.getString("artist");
                ArrayList<Song> tracklist = getTracklistFromPlaylist(name);
                return new Album(name, artist, tracklist);
            } else {
                System.out.println("No playlist id found for: " + name + "!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //JAMES
    public void updateSong(Song oldSong, Song newSong) {
        //find old song by selectSongByTitle(oldSong.getTitle());
        //write update SQL statement to replace all fields with new song fields
    }
    public void updateArtist(Artist oldArtist, Artist newArtist) {
        
    }
    public void updateAlbum(Album oldAlbum, Album newAlbum) {
        //contains tracklist arraylist, 
    }
    
    
    public ArrayList<Song> getTracklistFromPlaylist(String name) {
        try {
            ArrayList<Song> tracklist = new ArrayList();
            String selectString = "SELECT * FROM PlaylistSongs WHERE playlist = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next() == false) {
                System.out.println("No playlist found for: " + name + "!");
            }
            while (rs.next()) {
                //rs.getString("song");
                Song songToAdd = selectSongByTitle(rs.getString("song"));
                tracklist.add(songToAdd);
            }
            return tracklist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<Song> getTracklistFromAlbum(String name) {
        try {
            ArrayList<Song> tracklist = new ArrayList();
            String selectString = "SELECT * FROM Songs WHERE album = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next() == false) {
                System.out.println("No playlist found for: " + name + "!");
            }
            while (rs.next()) {
                //rs.getString("song");
                Song songToAdd = selectSongByTitle(rs.getString("song"));
                tracklist.add(songToAdd);
            }
            return tracklist;
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

    public void insertSong(Song song) throws SQLException {
        if (!ensureSongIntegrityConstraints(song.getArtist(), song.getAlbum(), song.getGenre(), song.getEra())) {
            System.out.println("Insert song integrity constraints not met");
            return;
        }

        String insertString = "INSERT INTO Songs (songTitle, artist, album, duration, genre, era) VALUES (?,?,?,?,?,?)";
        PreparedStatement insertStmt = connection.prepareStatement(insertString);

        insertStmt.setString(1, song.getSongTitle());
        insertStmt.setString(2, song.getArtist());
        insertStmt.setString(3, song.getAlbum());
        insertStmt.setInt(4, song.getDuration());
        insertStmt.setString(5, song.getGenre());
        insertStmt.setString(6, song.getEra());

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
    
    public void addSongToPlaylist(Song song, String playlistName) {
        try {
            //playlist_id, song_id
            String insertString = "INSERT INTO PlaylistSongs (playlist_id, song) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertString);
            checkPlaylist(playlistName);
            insertStmt.setString(1, playlistName);
            insertStmt.setString(2, song.getSongTitle());
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
        boolean existingAlbum = App.dbConn.selectAlbumByName(album) != null;
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