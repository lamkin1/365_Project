/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csc365p1;

/**
 *
 * @author seansponsler
 */
public class Genre {
    private String genre_name;
    public Genre(String genre) { this.genre_name = genre; }
    public String getGenreName() { return this.genre_name; }
    public void setGenreName(String newGenre) { this.genre_name = newGenre; }
}
