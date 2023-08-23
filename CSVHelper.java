package com.mycompany.csc365p1;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVHelper {

    List<List<String>> songs = new ArrayList<>();
    DatabaseConnection dbconn;

    public CSVHelper(DatabaseConnection dbconn) {
        this.dbconn = dbconn;
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
        for(List<String> info: songs.subList(1, songs.size())){
            System.out.println("Song: " + info.get(3)
                    + ", Arist: " + info.get(1)
                    + ", Album: " + info.get(6)
                    + ", Genre: " + info.get(5)
                    + ", Era: " + info.get(4)
                    + ", Duration: " + (int)Math.ceil(Double.parseDouble(info.get(2)))
            );
            //dbconn.insertSong(info.get(3),info.get(1), info.get(2), (int)Math.ceil(Double.parseDouble(info.get(2))), info.get(5), info.get(4));
        }
    }

}
