/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csc365p1;

import java.util.ArrayList;

/**
 *
 * @author seansponsler
 */
public class Album {
    private String album_name;
    private Artist artist;
    private ArrayList<Song> tracklist;
    
    public Album(String album_name, Artist artist, Song song) {
        this.album_name = album_name;
        this.artist = artist;
        this.tracklist = new ArrayList();
        this.tracklist.add(song);
    }
    public String getAlbumName() { return this.album_name; }
    public Artist getArtist() { return this.artist; }
    public ArrayList<Song> getTracklist() { return this.tracklist; }
}
