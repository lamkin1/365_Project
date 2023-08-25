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
public class Playlist {
    String playlist_name;
    String date_created;
    ArrayList<Song> tracklist;
    public Playlist(String name, String date) { 
        this.playlist_name = name;
        this.date_created = date;
        tracklist = new ArrayList();
    }
    public String getPlaylistName() { return this.playlist_name; }
    public String getDateCreated() { return this.date_created; }
    public ArrayList<Song> getTracklist() { return this.tracklist; }
    
    public void setPlaylistName(String newName) { this.playlist_name = newName; }
    public void setDateCreated(String newDate) { this.date_created = newDate; }
    
    public void addSong(Song songToAdd) { this.tracklist.add(songToAdd); }
    public void removeSong(Song songToRemove) {
        if (this.tracklist.contains(songToRemove)) {
            this.tracklist.remove(songToRemove);
        }
        else {
            System.err.println("Tracklist does not contain song titled: " + songToRemove.getTitle());
        }
    }
    
}
