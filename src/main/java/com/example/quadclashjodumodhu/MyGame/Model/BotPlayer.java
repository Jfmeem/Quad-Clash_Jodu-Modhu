package com.example.quadclashjodumodhu.MyGame.Model;
import java.util.Random;
public class BotPlayer extends Player {
    private final Random random=new Random();
    public BotPlayer(String name){
        super(name);
    }
    @Override
    public Card chooseCardToPlay(){
        if(getHand().isEmpty())return null;
        return getHand().get(random.nextInt(getHand().size()));
    }
}
