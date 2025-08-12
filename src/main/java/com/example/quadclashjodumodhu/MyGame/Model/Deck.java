package com.example.quadclashjodumodhu.MyGame.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        initializeDeck();
        shuffle();
        printDeckInfo();
    }

    private void initializeDeck() {
        System.out.println("Creating deck with 4 suits...");

        for (Card.Suit suit : Card.Suit.values()) {
            // Add exactly 4 cards of each suit
            for (int i = 1; i <= 4; i++) {
                cards.add(new Card(suit));
            }
            System.out.println("Added 4 " + suit + " cards");
        }

        System.out.println("Deck created with " + cards.size() + " total cards");

        // Verify deck composition
        verifyDeckComposition();
    }

    private void verifyDeckComposition() {
        int[] suitCounts = new int[Card.Suit.values().length];

        for (Card card : cards) {
            suitCounts[card.getSuit().ordinal()]++;
        }

        System.out.println("Deck verification:");
        for (Card.Suit suit : Card.Suit.values()) {
            int count = suitCounts[suit.ordinal()];
            System.out.println("- " + suit + ": " + count + " cards " +
                    (count == 4 ? "✓" : "x"));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
        System.out.println("Deck shuffled randomly");
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            System.out.println("⚠ Deck is empty! No more cards to draw.");
            return null;
        }

        Card drawnCard = cards.remove(0);
        System.out.println("Drew: " + drawnCard.getSuit() + " (Remaining: " + cards.size() + ")");
        return drawnCard;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    private void printDeckInfo() {
        System.out.println("\n=== DECK INFO ===");
        System.out.println("Total cards: " + cards.size());
        System.out.println("Suits available: " + Card.Suit.values().length);
        System.out.println("Cards per suit: " + (cards.size() / Card.Suit.values().length));
        System.out.println("Ready for distribution to 4 players (4 cards each)");
    }

    public List<Card> getRemainingCards() {
        return new ArrayList<>(cards);
    }

    @Override
    public String toString() {
        return "Deck{" + "cards=" + cards.size() + "}";
    }
}