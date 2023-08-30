/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csc365p1;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

    public StringProperty getPropertyByName(String name) {
        if (name.equals("songTitle")) return new SimpleStringProperty(getSongTitle());
        if (name.equals("artist")) return new SimpleStringProperty(getArtist());
        if (name.equals("album")) return new SimpleStringProperty(getAlbum());
        if (name.equals("duration")) return new SimpleStringProperty(Integer.toString(getDuration()));
        if (name.equals("genre")) return new SimpleStringProperty(getGenre());
        if (name.equals("era")) return new SimpleStringProperty(getEra());
        return null;
    }

    public void setPropertyByName(String name, String newValue) throws NumberFormatException {
        if (name.equals("songTitle")) {
            setSongTitle(newValue);
            return;
        }
        if (name.equals("artist")) {
            setArtist(newValue);
            return;
        }
        if (name.equals("album")) {
            setAlbum(newValue);
            return;
        }
        if (name.equals("duration")) {
            setDuration(Integer.parseInt(newValue));
            return;
        }
        if (name.equals("genre")) {
            setGenre(newValue);
            return;
        }
        if (name.equals("era")) {
            setEra(newValue);
        }
    }

    public void setSongTitle(String newTitle) { this.songTitle = newTitle; }
    public void setArtist(String newArtist) { this.artist = newArtist; }
    public void setAlbum(String newAlbum) { this.album = newAlbum; }
    public void setDuration(int newDuration) { this.duration = newDuration; }
    public void setGenre(String newGenre) { this.genre = newGenre; }
    public void setEra(String newEra) { this.era = newEra; }

    public boolean equals(Song song) {
        return this.songTitle.equals(song.getSongTitle()) &&
                this.artist.equals(song.getArtist()) &&
                this.album.equals(song.getAlbum()) &&
                this.duration == song.getDuration() &&
                this.genre.equals(song.getGenre()) &&
                this.era.equals(song.getEra());
    }
}