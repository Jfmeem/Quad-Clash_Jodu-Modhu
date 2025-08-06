package com.example.quadclashjodumodhu.MyGame.UI;

import com.example.quadclashjodumodhu.MyGame.Core.Game;
import com.example.quadclashjodumodhu.MyGame.Model.*;
import com.example.quadclashjodumodhu.MyGame.Util.CardLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class GameBoard extends BorderPane {
    private final Game game;
    private final CardLoader cardLoader;
    private final HBox humanHand;
    private final Button playButton;
    private final Label statusLabel;

    public GameBoard(Game game) {
        this.game = game;
        this.cardLoader = new CardLoader();

        // UI Setup
        setStyle("-fx-background-color: #f5f5dc;");
        setPadding(new Insets(20));

        // Title
        Label title = new Label("Quad Clash: Jodu-Modhu");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        setTop(title);

        // Game Area
        GridPane gameArea = new GridPane();
        gameArea.setAlignment(Pos.CENTER);
        gameArea.setHgap(20);
        gameArea.setVgap(20);

        // Player Areas
        setupPlayerAreas(gameArea);

        // Human Hand
        humanHand = new HBox(10);
        humanHand.setAlignment(Pos.CENTER);

        // Play Button
        playButton = new Button("Play Selected Card");
        playButton.setDisable(true);
        playButton.setOnAction(e -> playSelectedCard());

        VBox humanArea = new VBox(15, new Label("Your Hand"), humanHand, playButton);
        humanArea.setAlignment(Pos.CENTER);
        gameArea.add(humanArea, 1, 2);

        setCenter(gameArea);

        // Status Bar
        statusLabel = new Label(game.getCurrentPlayer().getName() + "'s turn");
        setBottom(statusLabel);

        updateGameUI();
    }

    private void setupPlayerAreas(GridPane gameArea) {
        // Implement AI player areas similarly
    }

    private void updateGameUI() {
        humanHand.getChildren().clear();

        game.getCurrentPlayer().getHand().forEach(card -> {
            CardView cardView = new CardView(card, cardLoader);
            cardView.setOnMouseClicked(e -> selectCard(cardView));
            humanHand.getChildren().add(cardView);
        });

        statusLabel.setText(game.getCurrentPlayer().getName() + "'s turn");
    }

    private void selectCard(CardView cardView) {
        humanHand.getChildren().forEach(node ->
                node.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 1);"));
        cardView.setStyle("-fx-effect: dropshadow(three-pass-box, gold, 10, 0.7, 0, 0);");
        playButton.setDisable(false);
    }

    private void playSelectedCard() {
        // Implement card playing logic
    }
}