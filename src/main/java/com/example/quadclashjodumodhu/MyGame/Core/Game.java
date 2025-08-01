package com.example.quadclashjodumodhu.MyGame.Core;
import com.example.quadclashjodumodhu.MyGame.Model.*;

import java.util.ArrayList;
import java.util.List;
public class Game {
    private final List<Player>players;
    private final Deck deck;
    private int currentPlayerIndex;
    public Game(){
        this.players=new ArrayList<>();
        this.deck=new Deck();
        initializeGame();
    }
    private void initializeGame(){
        players.add(new HumanPlayer("you"));
        players.add(new BotPlayer("Bot 1"));
        players.add(new BotPlayer("Bot 2"));
        players.add(new BotPlayer("Bot 3"));
        deck.shuffle();
        dealCards();
        currentPlayerIndex=0;
    }
    private void dealCards(){
        for (int i=0;i<4;i++){
            for(Player player:players){
                player.addCard(deck.drawCard());
            }
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void playTurn(Card card){
        Player currentPlayer=getCurrentPlayer();
        currentPlayer.playCard(card);
        if(checkWinCondition()){
            //handle win
            return;
        }
        nextPlayer();
    }
    private void nextPlayer(){
        currentPlayerIndex=(currentPlayerIndex+1)%players.size();
    }
    public boolean checkWinCondition(){
        return players.stream().anyMatch(Player::hasWinningHand);
    }
    public Player getWinner(){
        return players.stream()
                .filter(Player::hasWinningHand)
                .findFirst()
                .orElse(null);
    }
    public List<Player>getPlayers(){
        return new ArrayList<>(players);
    }
}
