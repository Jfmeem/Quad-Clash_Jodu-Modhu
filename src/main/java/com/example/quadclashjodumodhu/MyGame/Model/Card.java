package com.example.quadclashjodumodhu.MyGame.Model;

public class Card {
    public enum Suit {
        DEVIL_QUEEN, EMBERLORE, VALORA, WHISPERS_OF_WINGS
    }

    private final Suit suit;

    public Card(Suit suit) {
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }
}