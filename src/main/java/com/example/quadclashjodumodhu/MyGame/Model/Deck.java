package com.example.quadclashjodumodhu.MyGame.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (int i = 0; i < 4; i++) {
                cards.add(new Card(suit));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.isEmpty() ? null : cards.remove(0);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}