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

                values = Arrays.stream(values).map(value -> value.replaceAll(" ", "").toLowerCase()).toArray(String[]::new);

                songs.add(Arrays.asList(values));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
