package com.example.quadclashjodumodhu.MyGame.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Player {
    // Encapsulated private fields
    private final String name;
    private final List<Card> hand = new ArrayList<>();
    private int cardsPlayed = 0;

    public Player(String name) {
        this.name = name;
        System.out.println("Player created: " + name);
    }

    public void addCard(Card card) {
        if (card != null) {
            hand.add(card);
            System.out.println(" " + name + " received " + card.getSuit() +
                    " (Hand size: " + hand.size() + ")");
        }
    }

    public boolean removeCard(Card card) {
        boolean removed = hand.remove(card);
        if (removed) {
            cardsPlayed++;
            System.out.println("📤 " + name + " removed " + card.getSuit() +
                    " (Hand size: " + hand.size() + ")");
        }
        return removed;
    }

    public abstract Card playCard();

    public boolean hasWinningHand() {
        if (hand.size() != 4) {
            return false;
        }

        Map<Card.Suit, Integer> suitCounts = new HashMap<>();
        for (Card card : hand) {
            suitCounts.put(card.getSuit(), suitCounts.getOrDefault(card.getSuit(), 0) + 1);
        }

        for (Map.Entry<Card.Suit, Integer> entry : suitCounts.entrySet()) {
            if (entry.getValue() == 4) {
                System.out.println("🏆 " + name + " has winning hand: 4 " + entry.getKey() + " cards!");
                return true;
            }
        }

        return false;
    }

    public Card.Suit getWinningSuit() {
        Map<Card.Suit, Integer> suitCounts = new HashMap<>();
        for (Card card : hand) {
            suitCounts.put(card.getSuit(), suitCounts.getOrDefault(card.getSuit(), 0) + 1);
        }

        for (Map.Entry<Card.Suit, Integer> entry : suitCounts.entrySet()) {
            if (entry.getValue() == 4) {
                return entry.getKey();
            }
        }

        return null;
    }

    public boolean hasCard(Card card) {
        return hand.contains(card);
    }

    public Map<Card.Suit, Integer> getHandAnalysis() {
        Map<Card.Suit, Integer> analysis = new HashMap<>();
        for (Card card : hand) {
            analysis.put(card.getSuit(), analysis.getOrDefault(card.getSuit(), 0) + 1);
        }
        return analysis;
    }

    public void printHandStatus() {
        System.out.println("\n" + name + "'s hand (" + hand.size() + " cards):");
        Map<Card.Suit, Integer> suitCounts = getHandAnalysis();

        for (Card.Suit suit : Card.Suit.values()) {
            int count = suitCounts.getOrDefault(suit, 0);
            if (count > 0) {
                System.out.println("  " + suit + ": " + count + " card" + (count > 1 ? "s" : "") +
                        (count == 4 ? "  WINNING!" :
                                count == 3 ? " (close to win!)" : ""));
            }
        }
    }

    // Getter methods (Encapsulation)
    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand); // Return copy to protect encapsulation
    }

    public int getHandSize() {
        return hand.size();
    }

    public int getCardsPlayed() {
        return cardsPlayed;
    }

    // For debugging and display
    @Override
    public String toString() {
        return name + " [" + hand.size() + " cards, " + cardsPlayed + " played]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
