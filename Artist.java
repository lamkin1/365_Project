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
    private String artistName;
    
    public Artist(String artist) { 
        this.artistName = artist;
    }
    public String getArtistName() { return this.artistName; }
    public void setArtistName(String newArtist) { this.artistName = newArtist; }
}
