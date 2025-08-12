package com.example.quadclashjodumodhu.MyGame.Model;

/**
 * Card class for Quad Clash: Jodu-Modhu
 * As per project proposal: 4 types of cards, 4 identical cards per type
 * Implements proper equals/hashCode for game logic
 */
public class Card {

    /**
     * Four card suits as specified in project proposal
     * Each suit represents a different type of card
     */
    public enum Suit {
        DEVILQUEEN("Devil Queen", "", "A powerful royal card"),
        EMBERLORE("Ember Lore", "", "Cards of ancient fire magic"),
        VALORA("Valora", "⚔", "Warrior cards of strength"),
        WHISPERSOFWINGS("Whispers of Wings", "", "Mystical wind spirit cards");

        private final String displayName;
        private final String symbol;
        private final String description;

        Suit(String displayName, String symbol, String description) {
            this.displayName = displayName;
            this.symbol = symbol;
            this.description = description;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getDescription() {
            return description;
        }
    }


    public enum AssetType {
        CARD_BACK("Back of card design"),
        BACKGROUND("Game background elements");

        private final String description;

        AssetType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    // Encapsulated card data
    private final Suit suit;
    private final int cardId; // Unique identifier for each card
    private static int nextId = 1;


    public Card(Suit suit) {
        if (suit == null) {
            throw new IllegalArgumentException("Card suit cannot be null");
        }
        this.suit = suit;
        this.cardId = nextId++;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getCardId() {
        return cardId;
    }

    public String getDisplayName() {
        return suit.getDisplayName();
    }

    public String getSymbol() {
        return suit.getSymbol();
    }

    public String getDescription() {
        return suit.getDescription();
    }

    public boolean canCombineWith(Card other) {
        return other != null && this.suit == other.suit;
    }

    public int getRarityLevel() {
        return 1; // All cards are common rarity in this game
    }

    public String getImageFileName() {
        return suit.name().toLowerCase() + ".jpg";
    }

    public boolean isValidForGame() {
        return suit != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Card card = (Card) obj;
        return cardId == card.cardId && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return 31 * suit.hashCode() + cardId;
    }

    @Override
    public String toString() {
        return suit.getDisplayName() + " #" + cardId + " " + suit.getSymbol();
    }

    public String toShortString() {
        return suit.getSymbol() + suit.name().substring(0, 3);
    }

    public String toDetailedString() {
        return "Card{id=" + cardId + ", suit=" + suit + ", display='" +
                suit.getDisplayName() + "'}";
    }

    public int compareTo(Card other) {
        if (other == null) return 1;

        // First sort by suit
        int suitComparison = this.suit.compareTo(other.suit);
        if (suitComparison != 0) {
            return suitComparison;
        }

        // Then by card ID for consistent ordering
        return Integer.compare(this.cardId, other.cardId);
    }

    public static Suit[] getAllSuits() {
        return Suit.values();
    }

    public static int getSuitCount() {
        return Suit.values().length;
    }

    public static boolean isValidGameSetup() {
        return getSuitCount() == 4;
    }
}