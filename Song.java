/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csc365p1;

/**
 *
 * @author seansponsler
 */
public class Song {
    private String songTitle;
    private String artist;
    private String album;
    private int duration;
    private String genre;
    private String era;
    
    public Song(String songTitle, String artist, String album, int duration, String genre, String era) {
        this.songTitle = songTitle;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.genre = genre;
        this.era = era;
    }
    
    public String getSongTitle() { return songTitle; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public int getDuration() { return duration; }
    public String getGenre() { return genre; }
    public String getEra() { return era; }
    
    public void setSongTitle(String newTitle) { this.songTitle = newTitle; }
    public void setArtist(String newArtist) { this.artist = newArtist; }
    public void setAlbum(String newAlbum) { this.album = newAlbum; }
    public void setDuration(int newDuration) { this.duration = newDuration; }
    public void setGenre(String newGenre) { this.genre = newGenre; }
    public void setEra(String newEra) { this.era = newEra; }
}