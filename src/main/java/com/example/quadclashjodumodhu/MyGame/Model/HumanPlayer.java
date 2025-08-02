package com.example.quadclashjodumodhu.MyGame.Model;

public class HumanPlayer extends Player {
    public HumanPlayer(String name){
        super(name);
    }
    @Override
    public Card chooseCardToPlay(){
        //return null;
        throw new UnsupportedOperationException("Human player cards are selected via UI");
    }
}
