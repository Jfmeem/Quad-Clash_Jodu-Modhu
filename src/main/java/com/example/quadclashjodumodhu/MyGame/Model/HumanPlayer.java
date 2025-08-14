package com.example.quadclashjodumodhu.MyGame.Model;

import java.util.Map;

public class HumanPlayer extends Player {
    private Card selectedCard; // Card selected from UI
    private boolean isWaitingForSelection = false;

    public HumanPlayer(String name) {
        super(name);
        System.out.println("Human player initialized: " + name);
    }

    @Override
    public Card playCard() {
        if (selectedCard == null) {
            System.err.println(" * " + getName() + ": No card selected!");
            isWaitingForSelection = true;
            return null;
        }

        if (!hasCard(selectedCard)) {
            System.err.println(" * " + getName() + ": Selected card not in hand!");
            selectedCard = null;
            return null;
        }

        // Remove card from hand and return it
        Card cardToPlay = selectedCard;
        removeCard(cardToPlay);
        selectedCard = null; // Reset selection
        isWaitingForSelection = false;

        System.out.println(" * " + getName() + " played: " + cardToPlay.getSuit());
        return cardToPlay;
    }

    public void setSelectedCard(Card card) {
        if (card == null) {
            System.out.println("⚠* " + getName() + ": Card selection cleared");
            selectedCard = null;
            return;
        }

        if (!hasCard(card)) {
            System.err.println(" * " + getName() + ": Cannot select card not in hand!");
            return;
        }

        selectedCard = card;
        isWaitingForSelection = false;
        System.out.println(" * " + getName() + " selected: " + card.getSuit());

        // Show strategy hint
        showSelectionStrategy(card);
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public boolean isWaitingForSelection() {
        return isWaitingForSelection;
    }

    public void clearSelection() {
        selectedCard = null;
        isWaitingForSelection = false;
        System.out.println(" * " + getName() + ": Selection cleared");
    }

    public String getPlayingAdvice() {
        Map<Card.Suit, Integer> analysis = getHandAnalysis();
        StringBuilder advice = new StringBuilder();

        advice.append(" Playing advice for ").append(getName()).append(":\n");

        // Find best and worst suits
        Card.Suit bestSuit = null;
        Card.Suit worstSuit = null;
        int maxCount = 0;
        int minCount = 5;

        for (Map.Entry<Card.Suit, Integer> entry : analysis.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                bestSuit = entry.getKey();
            }
            if (entry.getValue() < minCount && entry.getValue() > 0) {
                minCount = entry.getValue();
                worstSuit = entry.getKey();
            }
        }

        if (maxCount >= 3) {
            advice.append(" Keep ").append(bestSuit).append(" cards (you have ").append(maxCount).append(")!\n");
        }

        if (worstSuit != null && minCount == 1) {
            advice.append("🗑 Consider giving away ").append(worstSuit).append(" (you only have 1)\n");
        }

        return advice.toString();
    }

    private void showSelectionStrategy(Card card) {
        Map<Card.Suit, Integer> analysis = getHandAnalysis();
        int suitCount = analysis.getOrDefault(card.getSuit(), 0);

        if (suitCount == 4) {
            System.out.println("Warning: You're giving away a winning suit!");
        } else if (suitCount == 3) {
            System.out.println("Careful: You have 3 " + card.getSuit() + " cards!");
        } else if (suitCount == 1) {
            System.out.println(" Good choice: Getting rid of singleton " + card.getSuit());
        } else {
            System.out.println(" You have " + suitCount + " " + card.getSuit() + " cards");
        }
    }

    public void displayHandForUI() {
        System.out.println("\n" + getName() + "'s turn:");
        printHandStatus();
        System.out.println(getPlayingAdvice());
    }

    public boolean canMakeMove() {
        return getHandSize() > 0 && !isWaitingForSelection;
    }

    public Card chooseCardToPlay() {
        return null;
    }

    @Override
    public String toString() {
        String status = super.toString();
        if (selectedCard != null) {
            status += " [Selected: " + selectedCard.getSuit() + "]";
        }
        if (isWaitingForSelection) {
            status += " [Waiting for selection]";
        }
        return status;
    }
}
