package com.mycompany.csc365p1;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseConnection {
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

    public ArrayList<Song> findSongs(String songTitle, String artist, String album, String duration, String genre, String era) throws SQLException {
        ArrayList<String> selectStringParts = new ArrayList<>();
        ArrayList<Object> params = new ArrayList<>();

        if (songTitle != null) {
            selectStringParts.add("songTitle = ?");
            params.add(songTitle);
        }
        if (artist != null) {
            selectStringParts.add("artist = ?");
            params.add(artist);
        }
        if (album != null) {
            selectStringParts.add("album = ?");
            params.add(album);
        }
        if (duration != null) {
            selectStringParts.add("duration = ?");
            params.add(Integer.parseInt(duration));
        }
        if (genre != null) {
            selectStringParts.add("genre = ?");
            params.add(genre);
        }
        if (era != null) {
            selectStringParts.add("era = ?");
            params.add(era);
        }

        String selectString = String.join(" AND ", selectStringParts);
        selectString += ";";
        selectString = "SELECT * FROM Songs WHERE " + selectString;

        ArrayList<Song> songs = new ArrayList<>();

        PreparedStatement selectStmt = connection.prepareStatement(selectString);
        for (int i = 0; i < params.size(); i++) {
            selectStmt.setObject(i + 1, params.get(i));
        }

        ResultSet rs = selectStmt.executeQuery();
        while (rs.next()) {
            String foundTitle = rs.getString("songTitle");
            String foundArtist = rs.getString("artist");
            String foundAlbum = rs.getString("album");
            int foundDuration = rs.getInt("duration");
            String foundGenre = rs.getString("genre");
            String foundEra = rs.getString("era");
            songs.add(new Song(foundTitle, foundArtist, foundAlbum, foundDuration, foundGenre, foundEra));
        }

        return songs;
    }

    public boolean deleteSong(Song song) {
        try {
            String deleteString = "DELETE FROM Songs WHERE songTitle = ? AND artist = ?";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteString);
            deleteStmt.setString(1, song.getSongTitle());
            deleteStmt.setString(2, song.getArtist());
            deleteStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error deleting song");
            e.printStackTrace();
        }

        return false;
    }

    public Song findSong(String songTitle, String artist) {
        try {
            String selectString = "SELECT * FROM Songs WHERE songTitle = ? AND artist = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, songTitle);
            selectStmt.setString(2, artist);

            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                String album = rs.getString("album");
                int duration = rs.getInt("duration");
                String genre = rs.getString("genre");
                String era = rs.getString("era");
                return new Song(songTitle, artist, album, duration, genre, era);
            } else {
                System.out.println("No song found for: " + songTitle +  "and " + artist + " !");
            }
        } catch (SQLException e) {
            System.out.println("Error selecting songs by title");
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Song> selectSongsByTitle(String title) {
        ArrayList<Song> songs = new ArrayList<>();

        try {
            String selectString = "SELECT * FROM Songs WHERE songTitle = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, title);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                String artist = rs.getString("artist");
                String album = rs.getString("album");
                int duration = rs.getInt("duration");
                String genre = rs.getString("genre");
                String era = rs.getString("era");
                songs.add(new Song(title, artist, album, duration, genre, era));
            } else {
                System.out.println("No song found for: " + title + "!");
            }
            selectStmt.close();
        } catch (SQLException e) {
            System.out.println("Error selecting songs by title");
            e.printStackTrace();
        }

        return songs;
    }

    public ArrayList<Song> selectSongsByArtistAndAlbum(String artist, String album) {
        ArrayList<Song> songs = new ArrayList<>();

        try {
            String selectString = "SELECT * FROM Songs WHERE artist = ? AND album = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, artist);
            selectStmt.setString(2, album);
            ResultSet rs = selectStmt.executeQuery();

            while (rs.next()) {
                String songTitle = rs.getString("songTitle");
                int duration = rs.getInt("duration");
                String genre = rs.getString("genre");
                String era = rs.getString("era");
                songs.add(new Song(songTitle, artist, album, duration, genre, era));
            }
            selectStmt.close();
        } catch (SQLException e) {
            System.out.println("Error selecting song by title");
            e.printStackTrace();
        }

        return songs;
    }

    public ArrayList<Song> selectSongsByArtist(String artist) {
        ArrayList<Song> songs = new ArrayList<>();

        try {
            String selectString = "SELECT * FROM Songs WHERE artist = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, artist);
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                String songTitle = rs.getString("songTitle");
                String album = rs.getString("album");
                int duration = rs.getInt("duration");
                String genre = rs.getString("genre");
                String era = rs.getString("era");
                songs.add(new Song(songTitle, artist, album, duration, genre, era));
            }
            selectStmt.close();
        } catch (SQLException e) {
            System.out.println("Error selecting song by title");
            e.printStackTrace();
        }

        return songs;
    }

    public ArrayList<Song> selectSongsByGenre(String genre) {
        ArrayList<Song> songs = new ArrayList<>();

        try {
            String selectString = "SELECT * FROM Songs WHERE genre = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, genre);
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                String songTitle = rs.getString("songTitle");
                String album = rs.getString("album");
                int duration = rs.getInt("duration");
                String artist = rs.getString("artist");
                String era = rs.getString("era");
                songs.add(new Song(songTitle, artist, album, duration, genre, era));
            }
            selectStmt.close();
        } catch (SQLException e) {
            System.out.println("Error selecting song by genre");
            e.printStackTrace();
        }

        return songs;
    }

    public ArrayList<String> selectAllAlbumsByArtist(String artist) {
        ArrayList<String> albums = new ArrayList<>();

        try {
            String selectString = "SELECT * FROM Albums WHERE artist = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, artist);
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                String albumName = rs.getString("albumName");
                albums.add(albumName);
            }
            selectStmt.close();
        } catch (SQLException e) {
            System.out.println("Error selecting album by artist " + artist);
            e.printStackTrace();
        }

        return albums;
    }

    public ArrayList<String> selectAllArtistsByAlbum(String albumName) {
        ArrayList<String> artists = new ArrayList<>();

        try {
            String selectString = "SELECT * FROM Albums WHERE albumName = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, albumName);
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                String artist = rs.getString("artist");
                artists.add(artist);
            }
            selectStmt.close();
        } catch (SQLException e) {
            System.out.println("Error selecting album by album " + albumName);
            e.printStackTrace();
        }

        return artists;
    }



    public ResultSet selectAllSongs() throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT * FROM Songs");
        return selectStmt.executeQuery();
    }

    public ResultSet selectAllPlaylistSongs() throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT * FROM PlaylistSongs ORDER BY playlist asc;");
        return selectStmt.executeQuery();
    }

    public ResultSet selectAllPlaylists() throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT * FROM Playlists;");
        return selectStmt.executeQuery();
    }
    
    public ResultSet selectAllAlbums() throws SQLException {
        PreparedStatement selectStmt = connection.prepareStatement("SELECT * FROM Albums;");
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

    //RETURNS ARTIST_ID
    public String selectPlaylistByName(String name) {
        try {
            String selectString = "SELECT * FROM Playlists WHERE playlistName = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                return name;
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
    public void updateSong(Song oldSong, Song newSong) throws SQLException {
        if (!ensureSongIntegrityConstraints(newSong.getArtist(), newSong.getAlbum(), newSong.getGenre(), newSong.getEra())) {
            System.out.println("Insert song integrity constraints not met when updating song");
            return;
        }

        PreparedStatement selectStmt = connection.prepareStatement("UPDATE Songs SET songTitle = ?, artist = ?, album = ?, duration = ?, genre = ?, era = ? WHERE songTitle = ? and artist = ?;");
        selectStmt.setString(1, newSong.getSongTitle());
        selectStmt.setString(2, newSong.getArtist());
        selectStmt.setString(3, newSong.getAlbum());
        selectStmt.setInt(4, newSong.getDuration());
        selectStmt.setString(5, newSong.getGenre());
        selectStmt.setString(6, newSong.getEra());
        selectStmt.setString(7, oldSong.getSongTitle());
        selectStmt.setString(8, oldSong.getArtist());
        selectStmt.executeUpdate();
        selectStmt.close();
    }
    public void updateArtist(Artist oldArtist, Artist newArtist) throws SQLException {
        Artist artist = selectArtistByName(oldArtist.getArtistName());
        ensureArtist(newArtist.getArtistName());
        PreparedStatement selectStmt = connection.prepareStatement("UPDATE Artists SET artist_name = ? WHERE artist_name = ?");
        selectStmt.setString(1, newArtist.getArtistName());
        selectStmt.setString(2, artist.getArtistName());
        selectStmt.executeQuery();
        selectStmt.close();
    }
    public void updateAlbum(Album oldAlbum, Album newAlbum) throws SQLException {
        Album album = selectAlbumByName(oldAlbum.getAlbumName());
        ensureArtist(newAlbum.getArtist());
        ensureAlbum(newAlbum.getAlbumName(), newAlbum.getArtist());
        PreparedStatement selectStmt = connection.prepareStatement("UPDATE Albums SET album_name = ?, artist = ? WHERE album_name = ? and artist = ?");
        selectStmt.setString(1, newAlbum.getAlbumName());
        selectStmt.setString(2, newAlbum.getArtist());
        selectStmt.setString(3, album.getAlbumName());
        selectStmt.setString(4, album.getArtist());
        selectStmt.executeQuery();
        selectStmt.close();
    }

    public ArrayList<String> getPlaylistNames() {
        ArrayList<String> playlistNames = new ArrayList<>();
        try {
            ResultSet rs = this.selectAllPlaylists();
            while (rs.next()) {
                playlistNames.add(rs.getString("playlistName"));
            }
        } catch (SQLException e) {
            System.out.println("Select all playlists error");
        }
        return playlistNames;
    }

    public ArrayList<String> getGenreNames() {
        ArrayList<String> genreNames = new ArrayList<>();
        try {
            ResultSet rs = this.selectAllGenres();

            while (rs.next()) {
                genreNames.add(rs.getString("genreName"));
            }
        } catch (SQLException e) {
            System.out.println("Select genre names error");
        }
        return genreNames;
    }

    public ArrayList<String> getArtistNames() {
        ArrayList<String> artistNames = new ArrayList<>();
        try {
            ResultSet rs = this.selectAllArtists();

            while (rs.next()) {
                artistNames.add(rs.getString("artistName"));
            }
        } catch (SQLException e) {
            System.out.println("Select all artist names error");
        }
        return artistNames;
    }

    public ArrayList<String> getAlbumNames() {
        ArrayList<String> albumNames = new ArrayList<>();
        try {
            ResultSet rs = this.selectAllAlbums();

            while (rs.next()) {
                albumNames.add(rs.getString("albumName"));
            }
        } catch (SQLException e) {
            System.out.println("Select all album names error");
        }
        return albumNames;
    }

    public ArrayList<Song> getTracklistFromPlaylist(String playlist) {
        try {
            ArrayList<Song> tracklist = new ArrayList();
            String selectString = "SELECT * FROM PlaylistSongs WHERE playlist = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, playlist);
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                Song songToAdd = findSong(rs.getString("song"), rs.getString("artist"));
                tracklist.add(songToAdd);
            }
            selectStmt.close();
            return tracklist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public ArrayList<Song> getTracklistFromAlbum(String name) {
        try {
            ArrayList<Song> tracklist = new ArrayList();
            String selectString = "SELECT * FROM Songs WHERE album = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectString);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No playlist found for: " + name + "!");
            }
            while (rs.next()) {
                Song songToAdd = findSong(rs.getString("song"), rs.getString("artist"));
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
    public void insertPlaylist(String playlistName) throws SQLException {
        String insertString = "INSERT INTO Playlists (playlistName, dateCreated) VALUES (?, ?)";
        PreparedStatement insertStmt = connection.prepareStatement(insertString);
        insertStmt.setString(1, playlistName);
        Date currentDate = new Date();
        insertStmt.setDate(2, new java.sql.Date(currentDate.getTime()));
        insertStmt.executeUpdate();
        insertStmt.close();
    }
    
    public void addSongToPlaylist(Song song, String playlistName) throws SQLException {
        String insertString = "INSERT INTO PlaylistSongs (playlist, song, artist) VALUES (?, ?, ?)";
        PreparedStatement insertStmt = connection.prepareStatement(insertString);
        checkPlaylist(playlistName);
        insertStmt.setString(1, playlistName);
        insertStmt.setString(2, song.getSongTitle());
        insertStmt.setString(3, song.getArtist());
        insertStmt.executeUpdate();
        insertStmt.close();
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

    public void checkPlaylist(String playlist) throws SQLException {
        boolean existingPlaylist = App.dbConn.selectPlaylistByName(playlist) != null;
        if (!existingPlaylist){
            App.dbConn.insertPlaylist(playlist);
        }
    }
}