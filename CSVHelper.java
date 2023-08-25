package com.mycompany.csc365p1;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVHelper {
    List<List<String>> songs = new ArrayList<>();

    public CSVHelper() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Songs Dataset.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                values = Arrays.stream(values).map(value -> value.replaceAll(",", "")).toArray(String[]::new);

                songs.add(Arrays.asList(values));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void importSongs() {
        try {
            for (List<String> info: songs.subList(1, songs.size())) {
                String title = info.get(3);
                String artist = info.get(1);
                String album = info.get(6);
                String genre = info.get(5);
                String era = info.get(4);
                int duration = (int)Math.ceil(Double.parseDouble(info.get(2)));

                App.dbConn.checkArtist(artist);
                App.dbConn.checkGenre(genre);
                App.dbConn.checkEra(era);
                App.dbConn.checkAlbum(album, artist);

                System.out.println("Title: " + title
                        + ", Arist: " + artist
                        + ", Album: " + album
                        + ", Genre: " + genre
                        + ", Era: " + era
                        + ", Duration: " + duration
                );
                App.dbConn.insertSong(title, artist, album, duration, genre, era);
            }
        } catch (SQLException e) {
        }
    }

}
