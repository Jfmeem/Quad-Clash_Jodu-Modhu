package com.example.quadclashjodumodhu.MyGame.UI;

import com.example.quadclashjodumodhu.MyGame.Model.BotPlayer;
import com.example.quadclashjodumodhu.MyGame.Model.Card;
import com.example.quadclashjodumodhu.MyGame.Model.Player;
import com.example.quadclashjodumodhu.MyGame.Core.Game;
import com.example.quadclashjodumodhu.MyGame.Util.CardLoader;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class GameBoard extends BorderPane {
    private final Game game;
    private final CardLoader cardLoader;
    private final HBox humanHand;
    private final VBox[] aiHands;
    private final Button playButton;
    private final Label statusLabel;

    public GameBoard(Game game) {
        this.game = game;
        this.cardLoader = new CardLoader();


        setPadding(new Insets(15));
        setStyle("-fx-background-color: #f0e6d2;");


        Label title = new Label("Quad Clash: Jodu-Modhu");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #5d4037;");
        setTop(title);


        GridPane gameArea = new GridPane();
        gameArea.setAlignment(Pos.CENTER);
        gameArea.setHgap(20);
        gameArea.setVgap(20);


        humanHand = new HBox(10);
        humanHand.setAlignment(Pos.CENTER);

        aiHands = new VBox[3];
        setupAIPlayers(gameArea);


        playButton = new Button("Play Card");
        playButton.setDisable(false);
        playButton.setOnAction(e -> playSelectedCard());

        VBox humanArea = new VBox(15, new Label("Your Hand"), humanHand, playButton);
        humanArea.setAlignment(Pos.CENTER);
        gameArea.add(humanArea, 1, 2);

        setCenter(gameArea);


        statusLabel = new Label(game.getCurrentPlayer().getName() + "'s turn");
        setBottom(statusLabel);

        updateGameView();
    }

    private void setupAIPlayers(GridPane gameArea) {

        aiHands[0] = createAIPlayerArea(game.getPlayers().get(1), "top");
        gameArea.add(aiHands[0], 1, 0);


        aiHands[1] = createAIPlayerArea(game.getPlayers().get(2), "left");
        gameArea.add(aiHands[1], 0, 1);


        aiHands[2] = createAIPlayerArea(game.getPlayers().get(3), "right");
        gameArea.add(aiHands[2], 2, 1);
    }
    private void playAITurn() {
        if (game.getCurrentPlayer() instanceof BotPlayer bot) {
            Card card = game.getCurrentPlayer().chooseCardToPlay();
            boolean gameOver = game.playTurn(card);
            updateGameView();

            if (!gameOver) {
                // Schedule next AI turn if needed
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> playAITurn());
                pause.play();
            } else {
                statusLabel.setText(game.getWinner().getName() + " wins!");
            }
        }
    }

    private VBox createAIPlayerArea(Player player, String position) {
        VBox area = new VBox(10);
        area.setAlignment(Pos.CENTER);
        area.setStyle("-fx-border-color: #8d6e63; -fx-border-width: 2; -fx-border-radius: 5;");

        Label nameLabel = new Label(player.getName());
        HBox cards = new HBox(5);
        cards.setAlignment(Pos.CENTER);


        for (int i = 0; i < player.getHand().size(); i++) {
            ImageView cardBack = new ImageView(CardLoader.getCardBackImage());
            cardBack.setFitWidth(80);
            cardBack.setFitHeight(120);

            switch (position) {
                case "top" -> cardBack.setRotate(180);
                case "left" -> cardBack.setRotate(90);
                case "right" -> cardBack.setRotate(-90);
            }

            cards.getChildren().add(cardBack);
        }

        area.getChildren().addAll(nameLabel, cards);
        return area;
    }
//    public Image getCardBackImage(){
//        return  imageCache.get(back);
//    }

    private void updateGameView() {

        humanHand.getChildren().clear();
        game.getPlayers().get(0).getHand().forEach(card -> {
            CardView cardView = new CardView(card, cardLoader);
            cardView.setOnMouseClicked(e -> {
                if (game.getCurrentPlayer().equals(game.getPlayers().get(0))) {
                    cardView.toggleSelection();
                    playButton.setDisable(humanHand.getChildren().stream()
                            .noneMatch(node -> ((CardView) node).isSelected()));
                }
            });
            humanHand.getChildren().add(cardView);
        });


        for (int i = 0; i < 3; i++) {
            updateAIPlayerDisplay(i);
        }

        statusLabel.setText(game.getCurrentPlayer().getName() + "'s turn");
    }

    private void updateAIPlayerDisplay(int aiIndex) {
        HBox cards = (HBox) aiHands[aiIndex].getChildren().get(1);
        cards.getChildren().clear();

        Player aiPlayer = game.getPlayers().get(aiIndex + 1);
        for (int i = 0; i < aiPlayer.getHand().size(); i++) {
            ImageView cardBack = new ImageView(CardLoader.getCardBackImage());
            cardBack.setFitWidth(80);
            cardBack.setFitHeight(120);
            cards.getChildren().add(cardBack);
        }
    }

    private void playSelectedCard() {
        humanHand.getChildren().stream()
                .filter(node -> ((CardView) node).isSelected())
                .findFirst()
                .ifPresent(cardNode -> {
                    boolean gameOver=game.playTurn(((CardView) cardNode).getCard());
                    updateGameView();

                    if (gameOver) {
                        statusLabel.setText(game.getWinner().getName() + " wins!");
                        playButton.setDisable(false);
                    }
                    else{
                        playAITurn();
                    }
                });
    }

}