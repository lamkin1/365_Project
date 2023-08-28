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
    String playlistName;
    String date_created;
    ArrayList<Song> tracklist;
    public Playlist(String name, ArrayList<Song> tracklist) { 
        this.playlistName = name;
        this.tracklist = tracklist;
    }
    public String getPlaylistName() { return this.playlistName; }
    public String getDateCreated() { return this.date_created; }
    public ArrayList<Song> getTracklist() { return this.tracklist; }
    
    public void setPlaylistName(String newName) { this.playlistName = newName; }
    public void setDateCreated(String newDate) { this.date_created = newDate; }
    public void setTracklist(ArrayList<Song> newTracklist) { this.tracklist = newTracklist; }
    public void addSong(Song songToAdd) { this.tracklist.add(songToAdd); }
    public void removeSong(Song songToRemove) {
        if (this.tracklist.contains(songToRemove)) {
            this.tracklist.remove(songToRemove);
        }
        else {
            System.err.println("Tracklist does not contain song titled: " + songToRemove.getSongTitle());
        }
    }
    
}