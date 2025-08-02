package com.example.quadclashjodumodhu;

import com.example.quadclashjodumodhu.MyGame.Core.Game;
import com.example.quadclashjodumodhu.MyGame.Model.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HelloController {
    @FXML private HBox playerHandContainer;
    @FXML private Label statusLabel;
    @FXML private Button playButton;

    private Game game;
    private Card selectedCard;

    @FXML
    public void initialize() {
        game = new Game();
        updateGameUI();
    }

    private void updateGameUI() {
        playerHandContainer.getChildren().clear();


        game.getCurrentPlayer().getHand().forEach(card -> {
            ImageView cardImage = new ImageView();
            cardImage.setFitWidth(80);
            cardImage.setFitHeight(120);

            cardImage.setOnMouseClicked(event -> {
                selectedCard = card;
                playButton.setDisable(false);

            });

            playerHandContainer.getChildren().add(cardImage);
        });

        statusLabel.setText(game.getCurrentPlayer().getName() + "'s turn");
    }

    @FXML
    private void handlePlayButton() {
        if (selectedCard != null && game.getCurrentPlayer().getHand().contains(selectedCard)) {
            game.playTurn(selectedCard);
            selectedCard = null;
            playButton.setDisable(true);
            updateGameUI();

            if (game.isGameOver()) {
                statusLabel.setText(game.getWinner().getName() + " wins!");
                playButton.setDisable(true);
            }
        }
    }
}