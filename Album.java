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
    private String artist;
    private ArrayList<Song> tracklist;
    
    public Album(String album_name, String artist, Song song) {
        this.album_name = album_name;
        this.artist = artist;
        this.tracklist = new ArrayList();
        this.tracklist.add(song);
    }
    public Album(String album_name, String artist, ArrayList<Song> tracklist) {
        this.album_name = album_name;
        this.artist = artist;
        this.tracklist = tracklist;
    }
    public void setTracklist(ArrayList<Song> newTracklist) { this.tracklist = newTracklist; }
    public void setName(String newName) { this.album_name = newName; }
    public void setArtist(String newArtist) { this.artist = newArtist; }
    public String getAlbumName() { return this.album_name; }
    public String getArtist() { return this.artist; }
    public ArrayList<Song> getTracklist() { return this.tracklist; }
}
