package com.example.quadclashjodumodhu.MyGame.UI;

import com.example.quadclashjodumodhu.MyGame.Model.BotPlayer;
import com.example.quadclashjodumodhu.MyGame.Model.Card;
import com.example.quadclashjodumodhu.MyGame.Model.Player;
import com.example.quadclashjodumodhu.MyGame.Model.HumanPlayer;
import com.example.quadclashjodumodhu.MyGame.Core.Game;
import com.example.quadclashjodumodhu.MyGame.Util.CardLoader;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.List;

public class GameBoard extends BorderPane {
    private final Game game;
    private final CardLoader cardLoader;
    private final HBox humanHand;
    private final VBox[] aiHands;
    private final Button playButton;
    private final Label statusLabel;
    private final Label turnInfoLabel;
    private final Label gameInfoLabel;

    // UI State
    private boolean gameInProgress = true;
    private boolean waitingForHumanInput = false;

    public GameBoard(Game game) {
        this.game = game;
        this.cardLoader = new CardLoader();

        // Initialize UI components
        this.humanHand = new HBox(10);
        this.aiHands = new VBox[3];
        this.playButton = new Button("Play Selected Card");
        this.statusLabel = new Label();
        this.turnInfoLabel = new Label();
        this.gameInfoLabel = new Label();

        setupUI();
        updateGameView();

        System.out.println(" GameBoard initialized for Quad Clash: Jodu-Modhu");
    }


    private void setupUI() {
        setPadding(new Insets(20));
        setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #3498db); " +
                "-fx-font-family: 'Arial';");


        setupHeader();


        setupGameArea();


        setupStatusArea();


        updateTurnInfo();
    }


    private void setupHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);

        // Main title
        Label title = new Label(" QUAD CLASH: JODU-MODHU ");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; " +
                "-fx-text-fill: white; -fx-effect: dropshadow(gaussian, black, 2, 0, 1, 1);");

        // Game description
        Label description = new Label("Turn-Based Card Game • Goal: Collect 4 Cards of Same Type");
        description.setStyle("-fx-font-size: 14px; -fx-text-fill: #ecf0f1;");

        // Game info
        gameInfoLabel.setText("Players: 1 Human + 3 AI • Total Cards: 16 (4 suits × 4 cards)");
        gameInfoLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #bdc3c7;");

        header.getChildren().addAll(title, description, gameInfoLabel);
        setTop(header);
    }

    private void setupGameArea() {
        GridPane gameArea = new GridPane();
        gameArea.setAlignment(Pos.CENTER);
        gameArea.setHgap(30);
        gameArea.setVgap(30);

        // Human player area (bottom)
        setupHumanPlayerArea(gameArea);

        // AI players areas
        setupAIPlayersAreas(gameArea);

        setCenter(gameArea);
    }

    private void setupHumanPlayerArea(GridPane gameArea) {
        VBox humanArea = new VBox(15);
        humanArea.setAlignment(Pos.CENTER);
        humanArea.setStyle("-fx-background-color: rgba(52, 152, 219, 0.2); " +
                "-fx-border-color: #3498db; -fx-border-width: 2; " +
                "-fx-border-radius: 10; -fx-padding: 20;");

        // Human player label
        Label humanLabel = new Label(" YOU (Human Player)");
        humanLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Cards area
        humanHand.setAlignment(Pos.CENTER);
        humanHand.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); " +
                "-fx-border-radius: 5; -fx-padding: 10;");

        // Play button
        playButton.setDisable(true);
        playButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 20; " +
                "-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                "-fx-border-radius: 5; -fx-font-weight: bold;");
        playButton.setOnAction(e -> handleHumanCardPlay());

        // Playing advice
        Label adviceLabel = new Label(" Click a card to select, then click 'Play Selected Card'");
        adviceLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #ecf0f1;");

        humanArea.getChildren().addAll(humanLabel, humanHand, playButton, adviceLabel);
        gameArea.add(humanArea, 1, 2);
    }

    private void setupAIPlayersAreas(GridPane gameArea) {
        // Top AI (Bot 1)
        aiHands[1] = createAIPlayerArea(game.getPlayers().get(2), "top", "#e67e22");
        gameArea.add(aiHands[1], 1, 0);

        // Left AI (Bot 2)
        aiHands[0] = createAIPlayerArea(game.getPlayers().get(1), "left", "#9b59b6");
        gameArea.add(aiHands[0], 0, 1);

        // Right AI (Bot 3)
        aiHands[2] = createAIPlayerArea(game.getPlayers().get(3), "right", "#27ae60");
        gameArea.add(aiHands[2], 2, 1);
    }

    private VBox createAIPlayerArea(Player player, String position, String color) {
        VBox area = new VBox(10);
        area.setAlignment(Pos.CENTER);
        area.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); " +
                "-fx-border-color: " + color + "; -fx-border-width: 2; " +
                "-fx-border-radius: 10; -fx-padding: 15;");

        // Player name and type
        String playerType = (player instanceof BotPlayer) ?
                " " + player.getName() + " [" + ((BotPlayer)player).getStrategy() + "]" :
                " " + player.getName();

        Label nameLabel = new Label(playerType);
        nameLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Cards display
        HBox cards = new HBox(5);
        cards.setAlignment(Pos.CENTER);
        cards.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2); " +
                "-fx-border-radius: 5; -fx-padding: 5;");

        // Add card backs
        updateAIPlayerCards(cards, player, position);

        // Hand size indicator
        Label handSizeLabel = new Label("Cards: " + player.getHandSize());
        handSizeLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #ecf0f1;");

        area.getChildren().addAll(nameLabel, cards, handSizeLabel);
        return area;
    }

    private void updateAIPlayerCards(HBox cardContainer, Player player, String position) {
        cardContainer.getChildren().clear();

        for (int i = 0; i < player.getHandSize(); i++) {
            ImageView cardBack = new ImageView(CardLoader.getCardBackImage());
            cardBack.setFitWidth(60);
            cardBack.setFitHeight(84);

            // Rotate cards based on position
            switch (position) {
                case "top" -> cardBack.setRotate(180);
                case "left" -> cardBack.setRotate(90);
                case "right" -> cardBack.setRotate(-90);
            }

            cardContainer.getChildren().add(cardBack);
        }
    }

    private void setupStatusArea() {
        VBox statusArea = new VBox(10);
        statusArea.setAlignment(Pos.CENTER);
        statusArea.setStyle("-fx-background-color: rgba(44, 62, 80, 0.8); " +
                "-fx-padding: 15; -fx-border-color: #34495e; -fx-border-width: 1 0 0 0;");

        // Turn info
        turnInfoLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #f39c12;");

        // Status message
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #ecf0f1;");

        statusArea.getChildren().addAll(turnInfoLabel, statusLabel);
        setBottom(statusArea);
    }

    private void handleHumanCardPlay() {
        if (!gameInProgress) return;

        CardView selectedCardView = getSelectedCardView();
        if (selectedCardView == null) {
            statusLabel.setText(" Please select a card first!");
            return;
        }

        Card selectedCard = selectedCardView.getCard();
        HumanPlayer humanPlayer = (HumanPlayer) game.getPlayers().get(0);

        // Set selected card and play turn
        humanPlayer.setSelectedCard(selectedCard);

        boolean gameOver = game.playTurn(selectedCard);
        updateGameView();

        if (gameOver) {
            handleGameEnd();
        } else {
            // Continue with AI turns
            waitingForHumanInput = false;
            playButton.setDisable(true);
            scheduleAITurns();
        }
    }

    private CardView getSelectedCardView() {
        return humanHand.getChildren().stream()
                .filter(node -> node instanceof CardView)
                .map(node -> (CardView) node)
                .filter(CardView::isSelected)
                .findFirst()
                .orElse(null);
    }

    private void scheduleAITurns() {
        if (!gameInProgress) return;

        Player currentPlayer = game.getCurrentPlayer();
        if (currentPlayer instanceof BotPlayer) {
            statusLabel.setText("🤖 " + currentPlayer.getName() + " is thinking...");

            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                if (gameInProgress) {
                    boolean gameOver = game.playTurn(null); // AI chooses automatically
                    updateGameView();

                    if (gameOver) {
                        handleGameEnd();
                    } else {
                        // Check if it's human's turn now
                        if (game.getCurrentPlayer() instanceof HumanPlayer) {
                            prepareHumanTurn();
                        } else {
                            scheduleAITurns(); // Continue AI turns
                        }
                    }
                }
            });
            pause.play();
        } else {
            prepareHumanTurn();
        }
    }

    private void prepareHumanTurn() {
        waitingForHumanInput = true;
        statusLabel.setText(" Your turn! Select a card to give to the next player.");

        // Show playing advice for human
        if (game.getPlayers().get(0) instanceof HumanPlayer humanPlayer) {
            humanPlayer.displayHandForUI();
        }
    }

    private void updateGameView() {
        updateHumanPlayerView();
        updateAIPlayersView();
        updateTurnInfo();
        updateGameStatus();
    }

    private void updateHumanPlayerView() {
        humanHand.getChildren().clear();

        Player humanPlayer = game.getPlayers().get(0);
        for (Card card : humanPlayer.getHand()) {
            CardView cardView = new CardView(card, cardLoader);

            // Add click handler for card selection
            cardView.setOnMouseClicked(e -> {
                if (waitingForHumanInput && game.getCurrentPlayer() instanceof HumanPlayer) {
                    // Clear other selections
                    humanHand.getChildren().stream()
                            .filter(node -> node instanceof CardView)
                            .map(node -> (CardView) node)
                            .forEach(cv -> {
                                if (cv != cardView) {
                                    cv.setSelected(false);
                                }
                            });

                    cardView.toggleSelection();

                    // Enable play button if card selected
                    boolean hasSelection = humanHand.getChildren().stream()
                            .filter(node -> node instanceof CardView)
                            .map(node -> (CardView) node)
                            .anyMatch(CardView::isSelected);

                    playButton.setDisable(!hasSelection);

                    if (hasSelection) {
                        statusLabel.setText("✓ Card selected: " + cardView.getCard().getSuit().getDisplayName());
                    }
                }
            });

            humanHand.getChildren().add(cardView);
        }
    }

    private void updateAIPlayersView() {
        for (int i = 0; i < 3; i++) {
            Player aiPlayer = game.getPlayers().get(i + 1);
            VBox aiArea = aiHands[i];
            if (game.getCurrentPlayer().getName().equals("You")) {
                playButton.setDisable(false);
            }
            if (game.getCurrentPlayer().getName().equals("You")) {
                playButton.setDisable(false);
            }


            // Update hand size label
            Label handSizeLabel = (Label) aiArea.getChildren().get(2);
            handSizeLabel.setText("Cards: " + aiPlayer.getHandSize());

            // Update card backs
            HBox cardContainer = (HBox) aiArea.getChildren().get(1);
            String position = i == 0 ? "top" : (i == 1 ? "left" : "right");
            updateAIPlayerCards(cardContainer, aiPlayer, position);
        }
    }

    private void updateTurnInfo() {
        Player currentPlayer = game.getCurrentPlayer();
        String turnText = " TURN " + game.getTurnCount() + " • Current Player: " +
                currentPlayer.getName();

        if (currentPlayer instanceof BotPlayer botPlayer) {
            turnText += " [" + botPlayer.getStrategy() + " Strategy]";
        }

        turnInfoLabel.setText(turnText);
    }

    private void updateGameStatus() {
        if (game.isGameOver()) {
            statusLabel.setText("🎉 GAME OVER! Winner: " + game.getWinner().getName() + " 🎉");
        } else {
            Player currentPlayer = game.getCurrentPlayer();
            if (currentPlayer instanceof HumanPlayer) {
                prepareHumanTurn();
            } else {
                statusLabel.setText("⏳ " + currentPlayer.getName() + "'s turn in progress...");
            }
        }
    }

    private void handleGameEnd() {
        gameInProgress = false;
        waitingForHumanInput = false;
        playButton.setDisable(true);

        Player winner = game.getWinner();
        String winMessage = "🏆 GAME OVER! 🏆\n" + winner.getName() + " wins with 4 " +
                winner.getWinningSuit().getDisplayName() + " cards!";

        turnInfoLabel.setText(winMessage);
        statusLabel.setText("Game completed in " + game.getTurnCount() + " turns.");

        // Highlight winner's area
        if (winner instanceof HumanPlayer) {
            humanHand.getParent().setStyle(humanHand.getParent().getStyle() +
                    "; -fx-border-color: gold; -fx-border-width: 3;");
        } else {
            // Find and highlight AI winner area
            for (int i = 1; i < game.getPlayers().size(); i++) {
                if (game.getPlayers().get(i).equals(winner)) {
                    aiHands[i-1].setStyle(aiHands[i-1].getStyle() +
                            "; -fx-border-color: gold; -fx-border-width: 3;");
                    break;
                }
            }
        }
    }

    // Method for GameController compatibility
    public List<Card> getCards() {
        return game.getPlayers().get(0).getHand();
    }

    // Initialize game start
    public void startGame() {
        if (game.getCurrentPlayer() instanceof HumanPlayer) {
            prepareHumanTurn();
        } else {
            scheduleAITurns();
        }
    }
}