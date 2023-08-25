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
    public String getArtist() { return this.artistName; }
    public void setArtist(String newArtist) { this.artistName = newArtist; }
}
