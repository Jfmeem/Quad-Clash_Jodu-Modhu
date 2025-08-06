package com.example.quadclashjodumodhu.MyGame.Core;

import com.example.quadclashjodumodhu.MyGame.Model.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> players = new ArrayList<>();
    private final Deck deck = new Deck();
    private int currentPlayerIndex = 0;
    private Player winner;

    public Game() {
        initializePlayers();
        dealCards();
    }

    private void initializePlayers() {
        players.add(new HumanPlayer());
        players.add(new BotPlayer("Bot 1"));
        players.add(new BotPlayer("Bot 2"));
        players.add(new BotPlayer("Bot 3"));
    }

    private void dealCards() {
        for (int i = 0; i < 4; i++) {
            for (Player player : players) {
                player.addCard(deck.drawCard());
            }
        }
    }

    public boolean playTurn(Card card) {
        Player current = getCurrentPlayer();
        current.playCard(card);

        if (!deck.isEmpty()) {
            current.addCard(deck.drawCard());
        }

        if (current.hasWinningHand()) {
            winner = current;
        }

        nextPlayer();
        return false;
    }

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public boolean isGameOver() {
        return winner != null;
    }

    public Player getWinner() {
        return winner;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }
}