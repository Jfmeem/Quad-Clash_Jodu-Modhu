package com.example.quadclashjodumodhu.MyGame.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BotPlayer extends Player {
    private final Random random = new Random();

    public BotPlayer(String name) {
        super(name);
    }
    @Override
    public Card playCard() {
        // Smart logic: pass card of least common suit
        Map<Card.Suit, Integer> suitCount = new HashMap<>();
        for (Card c : hand) {
            suitCount.put(c.getSuit(), suitCount.getOrDefault(c.getSuit(), 0) + 1);
        }

        Card.Suit minSuit = hand.get(0).getSuit();
        for (Card.Suit suit : suitCount.keySet()) {
            if (suitCount.get(suit) < suitCount.get(minSuit)) {
                minSuit = suit;
            }
        }

        for (Card c : hand) {
            if (c.getSuit().equals(minSuit)) {
                hand.remove(c);
                return c;
            }
        }

        return hand.remove(0); // fallback
    }


    @Override
    public Card chooseCardToPlay() {
        return hand.isEmpty() ? null : hand.get(random.nextInt(hand.size()));
    }
}