package com.mycompany.csc365p1;

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
                //values = Arrays.stream(values).map(value -> value.replaceAll(" ", "").toLowerCase()).toArray(String[]::new);

                songs.add(Arrays.asList(values));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void importSongs() {
        for (List<String> info: songs.subList(1, songs.size())) {
            String title = info.get(3);
            String artist = info.get(1);
            String album = info.get(6);
            String genre = info.get(5);
            String era = info.get(4);
            int duration = (int)Math.ceil(Double.parseDouble(info.get(2)));

            boolean existingArtist = App.dbConn.selectArtistByName(artist) != null;
            if (!existingArtist) {
                App.dbConn.insertArtist(artist);
            }

            boolean existingGenre = App.dbConn.selectGenreByName(genre) != null;
            if (!existingGenre) {
                App.dbConn.insertGenre(genre);
            }

            boolean existingEra = App.dbConn.selectEraByName(era) != null;
            if (!existingEra) {
                App.dbConn.insertEra(era);
            }

            boolean existingAlbum = App.dbConn.selectAlbum(album, artist) != null;
            if (!existingAlbum) {
                App.dbConn.insertAlbum(album, artist);
            }

            System.out.println("Title: " + title
                    + ", Arist: " + artist
                    + ", Album: " + album
                    + ", Genre: " + genre
                    + ", Era: " + era
                    + ", Duration: " + duration
            );
            App.dbConn.insertSong(title, artist, album, duration, genre, era);
        }
    }

}
