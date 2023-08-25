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
    private String albumName;
    private Artist artist;
    private ArrayList<Song> tracklist;
    
    public Album(String albumName, Artist artist, Song song) {
        this.albumName = albumName;
        this.artist = artist;
        this.tracklist = new ArrayList();
        this.tracklist.add(song);
    }
    public String getAlbumName() { return this.albumName; }
    public Artist getArtist() { return this.artist; }
    public ArrayList<Song> getTracklist() { return this.tracklist; }
}
