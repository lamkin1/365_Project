/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csc365p1;

/**
 *
 * @author seansponsler
 */
public class Artist {
    private String artist_name;
    
    public Artist(String artist) { 
        this.artist_name = artist;
    }
    public String getArtist() { return this.artist_name; }
    public void setArtist(String newArtist) { this.artist_name = newArtist; }
}
