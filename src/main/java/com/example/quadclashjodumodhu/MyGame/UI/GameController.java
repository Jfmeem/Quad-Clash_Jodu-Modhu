package com.example.quadclashjodumodhu.MyGame.UI;

import com.example.quadclashjodumodhu.MyGame.Core.Game;
import com.example.quadclashjodumodhu.MyGame.Model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.Optional;

/**
 * Main Game Controller for Quad Clash: Jodu-Modhu
 * Handles JavaFX integration as specified in project proposal
 * Manages game lifecycle and user interface coordination
 */
public class GameController {

    @FXML
    private StackPane gameContainer;

    private Game game;
    private GameBoard gameBoard;
    private boolean gameInitialized = false;

    /**
     * Initialize the game controller and setup the game
     * Called automatically by JavaFX after FXML loading
     */
    @FXML
    public void initialize() {
        System.out.println("🎮 Initializing Quad Clash: Jodu-Modhu Game Controller...");

        try {
            // Validate JavaFX setup
            validateJavaFXSetup();

            // Create new game instance
            createNewGame();

            // Setup UI
            setupGameUI();

            // Show welcome message
            showWelcomeDialog();

            gameInitialized = true;
            System.out.println("✅ Game Controller initialized successfully!");

        } catch (Exception e) {
            System.err.println("❌ Failed to initialize game: " + e.getMessage());
            e.printStackTrace();
            showErrorDialog("Initialization Error",
                    "Failed to initialize the game: " + e.getMessage());
        }
    }

    /**
     * Validate JavaFX environment
     */
    private void validateJavaFXSetup() {
        if (gameContainer == null) {
            throw new RuntimeException("Game container not properly initialized by FXML");
        }

        System.out.println("✓ JavaFX environment validated");
    }

    /**
     * Create a new game instance
     */
    private void createNewGame() {
        System.out.println("🎲 Creating new game instance...");

        // Validate card setup as per proposal
        if (!Card.isValidGameSetup()) {
            throw new RuntimeException("Invalid card setup: Expected exactly 4 suits, found " +
                    Card.getSuitCount());
        }

        game = new Game();
        System.out.println("✓ Game instance created with " + game.getPlayers().size() + " players");

        // Log player setup
        for (Player player : game.getPlayers()) {
            System.out.println("  - " + player.toString());
        }
    }

    /**
     * Setup the game UI
     */
    private void setupGameUI() {
        System.out.println("🎨 Setting up game UI...");

        gameBoard = new GameBoard(game);
        gameContainer.getChildren().clear();
        gameContainer.getChildren().add(gameBoard);

        // Start the game
        gameBoard.startGame();

        System.out.println("✓ Game UI setup complete");
    }

    /**
     * Show welcome dialog with game rules
     */
    private void showWelcomeDialog() {
        Alert welcomeAlert = new Alert(Alert.AlertType.INFORMATION);
        welcomeAlert.setTitle("Welcome to Quad Clash: Jodu-Modhu!");
        welcomeAlert.setHeaderText("🎴 Turn-Based Card Game 🎴");

        String rules = buildGameRulesText();
        welcomeAlert.setContentText(rules);

        // Custom styling
        welcomeAlert.getDialogPane().setStyle(
                "-fx-font-family: Arial; " +
                        "-fx-font-size: 12px;"
        );

        welcomeAlert.showAndWait();
    }

    /**
     * Build game rules text for welcome dialog
     */
    private String buildGameRulesText() {
        return """
               🎯 OBJECTIVE:
               Collect 4 cards of the same type to win!
               
               👥 PLAYERS:
               • You (Human Player)
               • 3 AI Opponents with different strategies
               
               🃏 CARDS:
               • 16 total cards (4 suits × 4 cards each)
               • Devil Queen 👑 • Ember Lore 🔥
               • Valora ⚔️ • Whispers of Wings 🕊️
               
               🎮 HOW TO PLAY:
               1. Each player starts with 4 cards
               2. Take turns passing 1 card to the next player
               3. First to collect 4 same-suit cards wins!
               4. Turn order: You → Bot 1 → Bot 2 → Bot 3 → repeat
               
               💡 STRATEGY TIPS:
               • Keep cards of suits you have multiple of
               • Give away singleton cards
               • Watch for AI strategies: Smart, Aggressive, Defensive
               
               Good luck! Click OK to start playing.
               """;
    }

    /**
     * Reset and start a new game
     */
    @FXML
    public void startNewGame() {
        if (gameInitialized) {
            // Confirm new game
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("New Game");
            confirmAlert.setHeaderText("Start New Game?");
            confirmAlert.setContentText("Are you sure you want to start a new game? Current progress will be lost.");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Restart game
                initialize();
            }
        }
    }

    /**
     * Show game statistics
     */
    @FXML
    public void showGameStats() {
        if (game == null) return;

        Alert statsAlert = new Alert(Alert.AlertType.INFORMATION);
        statsAlert.setTitle("Game Statistics");
        statsAlert.setHeaderText("📊 Current Game Status");

        StringBuilder stats = new StringBuilder();
        stats.append("🎯 Turn: ").append(game.getTurnCount()).append("\n");
        stats.append("👤 Current Player: ").append(game.getCurrentPlayer().getName()).append("\n\n");

        stats.append("📋 PLAYER STATUS:\n");
        for (Player player : game.getPlayers()) {
            stats.append("• ").append(player.getName()).append(": ")
                    .append(player.getHandSize()).append(" cards");

            if (player instanceof BotPlayer bot) {
                stats.append(" [").append(bot.getStrategy()).append("]");
            }

            if (player.hasWinningHand()) {
                stats.append(" 🏆 WINNER!");
            }

            stats.append("\n");

            // Show hand analysis
            var analysis = player.getHandAnalysis();
            for (var entry : analysis.entrySet()) {
                if (entry.getValue() > 0) {
                    stats.append("    ").append(entry.getKey().getSymbol())
                            .append(" ").append(entry.getKey().getDisplayName())
                            .append(": ").append(entry.getValue()).append("\n");
                }
            }
        }

        if (game.isGameOver()) {
            stats.append("\n🎉 GAME COMPLETED! 🎉\n");
            stats.append("Winner: ").append(game.getWinner().getName());
        }

        statsAlert.setContentText(stats.toString());
        statsAlert.showAndWait();
    }

    /**
     * Show game help
     */
    @FXML
    public void showHelp() {
        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("Help - Quad Clash: Jodu-Modhu");
        helpAlert.setHeaderText("❓ How to Play");

        String helpText = """
                         🎮 GAME CONTROLS:
                         • Click on a card in your hand to select it
                         • Click "Play Selected Card" to pass it to next player
                         • Selected cards have a golden border
                         
                         📋 GAME FLOW:
                         1. Your turn: Select and play a card
                         2. AI players automatically play their turns
                         3. Cards are passed clockwise around the table
                         4. Game continues until someone wins
                         
                         🏆 WINNING:
                         • First player to collect 4 cards of same suit wins
                         • Suits: Devil Queen, Ember Lore, Valora, Whispers of Wings
                         
                         🤖 AI STRATEGIES:
                         • SMART: Calculated moves, avoids giving away good cards
                         • AGGRESSIVE: Takes risks to collect cards quickly  
                         • DEFENSIVE: Tries to prevent others from winning
                         
                         💡 TIPS:
                         • Hover over cards for detailed information
                         • Keep track of what suits you have most of
                         • Try to give away cards you only have 1 of
                         
                         🎯 WINNING STRATEGY:
                         Focus on one or two suits and try to collect 4 of the same type!
                         """;

        helpAlert.setContentText(helpText);
        helpAlert.showAndWait();
    }

    /**
     * Show about dialog
     */
    @FXML
    public void showAbout() {
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About Quad Clash: Jodu-Modhu");
        aboutAlert.setHeaderText("🎴 Quad Clash: Jodu-Modhu 🎴");

        String aboutText = """
                          📝 PROJECT INFORMATION:
                          A Simple Turn-Based Card Game in Java
                          
                          👨‍💻 DEVELOPERS:
                          • Nabila Siddiqua (BSSE-1644)
                          • Jannatul Meem (BSSE-1646)
                          
                          📚 COURSE: SE 1206
                          🏫 Object-Oriented Programming Project
                          
                          🛠️ TECHNOLOGIES USED:
                          • Java Programming Language
                          • JavaFX for GUI Framework  
                          • IntelliJ IDEA as IDE
                          
                          🎯 OOP PRINCIPLES IMPLEMENTED:
                          • Encapsulation: Private fields, public methods
                          • Inheritance: Player → HumanPlayer, BotPlayer
                          • Abstraction: Abstract Player class
                          • Polymorphism: Different playCard() implementations
                          
                          🎮 GAME FEATURES:
                          • 1 Human Player vs 3 AI Opponents
                          • Turn-based gameplay with card passing
                          • Smart AI with multiple strategies
                          • Interactive JavaFX interface
                          • Win condition detection
                          
                          Thank you for playing!
                          """;

        aboutAlert.setContentText(aboutText);
        aboutAlert.showAndWait();
    }

    /**
     * Exit the application
     */
    @FXML
    public void exitApplication() {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit Game");
        exitAlert.setHeaderText("Exit Quad Clash?");
        exitAlert.setContentText("Are you sure you want to exit the game?");

        Optional<ButtonType> result = exitAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("👋 Thank you for playing Quad Clash: Jodu-Modhu!");
            Platform.exit();
        }
    }

    /**
     * Show error dialog
     * @param title Error title
     * @param message Error message
     */
    private void showErrorDialog(String title, String message) {
        Platform.runLater(() -> {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(title);
            errorAlert.setContentText(message);
            errorAlert.showAndWait();
        });
    }

    /**
     * Get cards for compatibility with existing GameBoard
     * @return List of cards in human player's hand
     */
    public List<Card> getCards() {
        if (game != null && !game.getPlayers().isEmpty()) {
            return game.getPlayers().get(0).getHand();
        }
        return List.of();
    }

    // Getters for testing and debugging
    public Game getGame() {
        return game;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public boolean isGameInitialized() {
        return gameInitialized;
    }
}