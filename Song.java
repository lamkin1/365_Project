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
    private String song_title;
    private Artist artist;
    private Album album;
    private int duration;
    private Genre genre;
    private Era era;
    
    public Song(String song_title, Artist artist, Album album, int duration, Genre genre, Era era) {
        this.song_title = song_title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.genre = genre;
        this.era = era;
    }
    //NO ALBUM
    public Song(String song_title, Artist artist, int duration, Genre genre, Era era) {
        this.song_title = song_title;
        this.artist = artist;
        this.duration = duration;
        this.genre = genre;
        this.era = era;
    }
    //NO ALBUM, GENRE
    public Song(String song_title, Artist artist, int duration, Era era) {
        this.song_title = song_title;
        this.artist = artist;
        this.duration = duration;
        this.era = era;
    }
    //NO ALBUM, GENRE, ERA
    public Song(String song_title, Artist artist, int duration) {
        this.song_title = song_title;
        this.artist = artist;
        this.duration = duration;
    }
    //BASE CONSTRUCTOR
    public Song(String song_title, Artist artist) {
        this.song_title = song_title;
        this.artist = artist;
    }
    
    public String getTitle() { return song_title; }
    public Artist getArtist() { return artist; }
    public Album getAlbum() { return album; }
    public int getDuration() { return duration; }
    public Genre getGenre() { return genre; }
    public Era getEra() { return era; }
    
    public void setTitle(String newTitle) { this.song_title = newTitle; }
    public void setArtist(Artist newArtist) { this.artist = newArtist; }
    public void setAlbum(Album newAlbum) { this.album = newAlbum; }
    public void setDuration(int newDuration) { this.duration = newDuration; }
    public void setGenre(Genre newGenre) { this.genre = newGenre; }
    public void setEra(Era newEra) { this.era = newEra; }

}
