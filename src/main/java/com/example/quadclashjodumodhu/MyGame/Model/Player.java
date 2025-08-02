package com.example.quadclashjodumodhu.MyGame.Model;
import java.util.ArrayList;
import java.util.List;
public abstract class Player {
    private final String name;
    private final List<Card> hand;
    public Player(String name){
        this.name=name;
        this.hand=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }
    public void addCard(Card card){
        hand.add(card);
    }
    public void playCard(Card card){
        hand.remove(card);
    }
    public void PlayCard(Card card){
        hand.remove(card);
    }
    public boolean hasWinningHand(){
        if(hand.size()!=4)return false;
        Card.Suits firstSuit=hand.get(0).getSuit();
        return hand.stream().allMatch(card -> card.getSuit()==firstSuit);

    }

    public abstract Card chooseCardToPlay();

   // public abstract void playCard(Card card);
}
