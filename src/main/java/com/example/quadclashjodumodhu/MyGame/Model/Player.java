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

//    //public void playCard(Card card) {
//        hand.remove(card);
//    }
    public abstract Card playCard();

    public boolean hasWinningHand() {
        if (hand.size() != 4) return false;
       // Card.Suit firstSuit = hand.get(0).getSuit();
        //return hand.stream().allMatch(c -> c.getSuit() == firstSuit);
        Card.Suit suit=hand.get(0).getSuit();
        for(Card c:hand){
            if(!c.getSuit().equals(suit))return false;
        }
        return true;
    }


    public void passCardTo(Player nextPlayer) {

        Card cardToPass = playCard(); // implement your logic here

        nextPlayer.addCard(cardToPass);
    }


    public abstract Card chooseCardToPlay();

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }
}