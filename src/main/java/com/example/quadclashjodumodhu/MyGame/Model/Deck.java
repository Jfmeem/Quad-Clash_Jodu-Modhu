package com.example.quadclashjodumodhu.MyGame.Model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Deck {
    private final List<Card>cards;
    public Deck(){
        this.cards=new ArrayList<>();
        initializeDeck();
    }
    private void initializeDeck(){
        for (Card.Suits suit : Card.Suits.values()){
            for (int i=0;i<4;i++){
                String imagePath="/images/cards"+suit.name().toLowerCase() +"_"+(i+1) +" .jpg";
                cards.add(new Card(suit,imagePath));
            }
        }
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
    public Card drawCard(){
        if(cards.isEmpty())return null;
        return cards.remove(0);
    }
    public boolean isEmpty(){
        return cards.isEmpty();
    }

}
