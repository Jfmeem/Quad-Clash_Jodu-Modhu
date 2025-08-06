package com.example.quadclashjodumodhu.MyGame.Model;

public class Card {
    public enum Suit {
        DEVILQUEEN, EMBERLORE, VALORA, WHISPERSOFWINGS
    }

    private final Suit suit;

    public Card(Suit suit) {
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }
}