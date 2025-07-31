package com.example.quadclashjodumodhu.MyGame.Model;

public class Card {
    public enum Suits{
        DevilQueen, Emberlore, Valora, WhispersOfWings
    }
    private final Suits suit;
    private final String imagePath;

    public Card(Suits suit, String imagePath){
        this.suit = suit;
        this.imagePath = imagePath;

    }
    
    public Suits getSuit(){
        return suit;
    }
    public String getImagePath(){
        return imagePath;
    }

    public String toString(){
        return "Card {suit = " + suit + ", imagePath = ' " + imagePath  + "}";
    }
}
