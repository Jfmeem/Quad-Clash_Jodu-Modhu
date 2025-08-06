package com.example.quadclashjodumodhu.MyGame.Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected final String name;
    protected final List<Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void playCard(Card card) {
        hand.remove(card);
    }

    public boolean hasWinningHand() {
        if (hand.size() != 4) return false;
        Card.Suit firstSuit = hand.get(0).getSuit();
        return hand.stream().allMatch(c -> c.getSuit() == firstSuit);
    }

    public abstract Card chooseCardToPlay();

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }
}