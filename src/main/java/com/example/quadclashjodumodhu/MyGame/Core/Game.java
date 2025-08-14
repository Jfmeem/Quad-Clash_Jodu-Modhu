package com.example.quadclashjodumodhu.MyGame.Core;

import com.example.quadclashjodumodhu.MyGame.Model.*;

import java.util.*;

public class Game {
    private final List<Player> players = new ArrayList<>();
    private final Deck deck = new Deck();
    private int currentPlayerIndex = 0;
    private Player winner;
    private int turnCount = 0;
    private boolean allCardsRevealed = false;

    public Game() {
        initializePlayers();
        dealInitialCards();
        printGameStart();
    }

    private void initializePlayers() {
        players.add(new HumanPlayer("You"));
        players.add(new BotPlayer("Bot 1"));
        players.add(new BotPlayer("Bot 2"));
        players.add(new BotPlayer("Bot 3"));

        System.out.println("=== QUAD CLASH: JODU-MODHU ===");
        System.out.println("Players initialized: " + players.size());
        for (Player player : players) {
            System.out.println("- " + player.getName());
        }
    }

    private void dealInitialCards() {
        System.out.println("\n--- Dealing Cards ---");
        for (int cardNumber = 1; cardNumber <= 4; cardNumber++) {
            for (Player player : players) {
                Card dealtCard = deck.drawCard();
                if (dealtCard != null) {
                    player.addCard(dealtCard);
                }
            }
        }

        System.out.println("Card distribution complete:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getHandSize() + " cards");
        }
        System.out.println("Remaining deck size: " + deck.size());
    }

    public boolean playTurn(Card selectedCard) {
        turnCount++;
        Player currentPlayer = getCurrentPlayer();
        Player nextPlayer = getNextPlayer();

        System.out.println("\n=== TURN " + turnCount + " ===");
        System.out.println("Current Player: " + currentPlayer.getName() + " (" + currentPlayer.getHandSize() + " cards)");
        System.out.println("Next Player: " + nextPlayer.getName() + " (" + nextPlayer.getHandSize() + " cards)");

        Card cardToPass = null;

        if (currentPlayer instanceof HumanPlayer) {
            if (selectedCard != null && currentPlayer.hasCard(selectedCard)) {
                cardToPass = selectedCard;
                currentPlayer.removeCard(cardToPass);
                System.out.println("✓ " + currentPlayer.getName() + " selected: " + cardToPass.getSuit());
            } else {
                System.err.println(" Invalid card selection!");
                return false;
            }
        } else {
            cardToPass = currentPlayer.playCard();
            System.out.println("* " + currentPlayer.getName() + " played: " +
                    (cardToPass != null ? cardToPass.getSuit() : "null"));
        }

        if (cardToPass != null) {
            nextPlayer.addCard(cardToPass);
            System.out.println("➡ " + currentPlayer.getName() + " passed " +
                    cardToPass.getSuit() + " to " + nextPlayer.getName());

            System.out.println("After turn: " + currentPlayer.getName() + "(" +
                    currentPlayer.getHandSize() + ") → " +
                    nextPlayer.getName() + "(" + nextPlayer.getHandSize() + ")");
        }

        checkWinConditions();

        if (winner == null) {
            nextPlayer();
            return false;
        } else {
            revealAllCards();
            System.out.println("\n GAME OVER! Winner: " + winner.getName() +
                    " with suit: " + getWinnerSuit());
            printFinalStatus();
            return true;
        }
    }

    private void checkWinConditions() {
        for (Player player : players) {
            if (player.hasWinningHand()) {
                winner = player;
                System.out.println("* " + player.getName() + " has winning hand!");
                System.out.print("Winning cards: ");
                for (Card card : player.getHand()) {
                    System.out.print(card.getSuit() + " ");
                }
                System.out.println();
                break;
            }
        }
    }

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        System.out.println("⏭ Next turn: " + getCurrentPlayer().getName());
    }

    private Player getNextPlayer() {
        int nextIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(nextIndex);
    }

    private void printGameStart() {
        System.out.println("\n GAME STARTED! ");
        System.out.println("Goal: Collect 4 cards of the same suit");
        System.out.println("Turn Order: " + players.get(0).getName() + " → " +
                players.get(1).getName() + " → " +
                players.get(2).getName() + " → " +
                players.get(3).getName());
        System.out.println("First turn: " + getCurrentPlayer().getName());
    }

    private void printFinalStatus() {
        System.out.println("\n=== FINAL STATUS ===");
        for (Player player : players) {
            System.out.print(player.getName() + " (" + player.getHandSize() + "): ");
            for (Card card : player.getHand()) {
                System.out.print(card.getSuit() + " ");
            }
            if (player.hasWinningHand()) {
                System.out.print(" WINNER!");
            }
            System.out.println();
        }
        System.out.println("Total turns played: " + turnCount);
    }

    public void revealAllCards() {
        allCardsRevealed = true;
    }

    public boolean isAllCardsRevealed() {
        return allCardsRevealed;
    }

    public String getWinnerSuit() {
        if (winner != null && winner.getHand().size() > 0) {
            return winner.getHand().get(0).getSuit().toString();
        }
        return "Unknown";
    }

    public Map<String, List<Card>> getAllFinalCards() {
        Map<String, List<Card>> finalCards = new LinkedHashMap<>();
        for (Player player : players) {
            finalCards.put(player.getName(), new ArrayList<>(player.getHand()));
        }
        return finalCards;
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

    public int getTurnCount() {
        return turnCount;
    }
}
