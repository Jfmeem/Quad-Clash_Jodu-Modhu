package com.example.quadclashjodumodhu.MyGame.Model;

import java.util.*;

public class BotPlayer extends Player {
    private final Random random = new Random();
    private final String strategy;

    // AI Strategy types (Defensive বাদ, Smart দুইবার)
    private static final String[] STRATEGIES = {
            "SMART", "SMART", "AGGRESSIVE"
    };

    public BotPlayer(String name) {
        super(name);
        this.strategy = STRATEGIES[random.nextInt(STRATEGIES.length)];
        System.out.println(" Bot player initialized: " + name + " [Strategy: " + strategy + "]");
    }

    @Override
    public Card playCard() {
        if (getHandSize() == 0) {
            System.err.println(" * " + getName() + ": No cards to play!");
            return null;
        }

        Card chosenCard = null;

        switch (strategy) {
            case "SMART" -> chosenCard = chooseSmartCard();
            case "AGGRESSIVE" -> chosenCard = chooseAggressiveCard();
            default -> chosenCard = chooseRandomCard();
        }

        if (chosenCard != null) {
            removeCard(chosenCard);
            System.out.println(" " + getName() + " (" + strategy + ") played: " +
                    chosenCard.getSuit() + " [Remaining: " + getHandSize() + "]");

            // Show AI reasoning
            explainChoice(chosenCard);
        }

        return chosenCard;
    }

    private Card chooseSmartCard() {
        Map<Card.Suit, Integer> analysis = getHandAnalysis();
        List<Card> hand = getHand();

        // Never give away if we have 4 of same suit (winning hand)
        for (Card.Suit suit : Card.Suit.values()) {
            if (analysis.getOrDefault(suit, 0) == 4) {
                // Find different suit to give away
                for (Card card : hand) {
                    if (!card.getSuit().equals(suit)) {
                        return card;
                    }
                }
            }
        }

        // Avoid suits with 3 or more cards
        Set<Card.Suit> avoidSuits = new HashSet<>();
        for (Map.Entry<Card.Suit, Integer> entry : analysis.entrySet()) {
            if (entry.getValue() >= 3) {
                avoidSuits.add(entry.getKey());
            }
        }

        // Find least valuable card to give away
        Card.Suit targetSuit = null;
        int minCount = 5;

        for (Map.Entry<Card.Suit, Integer> entry : analysis.entrySet()) {
            if (!avoidSuits.contains(entry.getKey()) && entry.getValue() < minCount) {
                minCount = entry.getValue();
                targetSuit = entry.getKey();
            }
        }

        if (targetSuit != null) {
            for (Card card : hand) {
                if (card.getSuit().equals(targetSuit)) {
                    return card;
                }
            }
        }

        return hand.get(0);
    }

    private Card chooseAggressiveCard() {
        Map<Card.Suit, Integer> analysis = getHandAnalysis();
        List<Card> hand = getHand();

        // Give away singletons first
        for (Card card : hand) {
            if (analysis.getOrDefault(card.getSuit(), 0) == 1) {
                return card;
            }
        }

        // Fall back to smart choice
        return chooseSmartCard();
    }

    private Card chooseRandomCard() {
        List<Card> hand = getHand();
        return hand.get(random.nextInt(hand.size()));
    }

    private void explainChoice(Card chosenCard) {
        Map<Card.Suit, Integer> analysis = getHandAnalysis();
        int suitCount = analysis.getOrDefault(chosenCard.getSuit(), 0);

        String reasoning = switch (strategy) {
            case "SMART" -> "Chose " + chosenCard.getSuit() + " (had " + suitCount +
                    ") to minimize suit diversity";
            case "AGGRESSIVE" -> "Aggressively giving away " + chosenCard.getSuit() +
                    " singleton to focus collection";
            default -> "Random choice: " + chosenCard.getSuit();
        };

        System.out.println("  * " + getName() + ": " + reasoning);
    }

    public void analyzeGameState() {
        Map<Card.Suit, Integer> analysis = getHandAnalysis();

        System.out.println("\n " + getName() + " analysis:");
        for (Map.Entry<Card.Suit, Integer> entry : analysis.entrySet()) {
            String status = switch (entry.getValue()) {
                case 4 -> " WINNING!";
                case 3 -> " Close to win";
                case 2 -> " Building";
                case 1 -> " Singleton";
                default -> "";
            };

            if (entry.getValue() > 0) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " " + status);
            }
        }
    }

    public String getStrategy() {
        return strategy;
    }

    public void simulateThinking() {
        try {
            Thread.sleep(500 + random.nextInt(1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " [Strategy: " + strategy + "]";
    }
}
