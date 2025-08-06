package com.example.quadclashjodumodhu.MyGame.UI;
import com.example.quadclashjodumodhu.MyGame.Core.Game;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
public class GameController {
    @FXML
    private StackPane gameContainer;

    private Game game;
    private GameBoard gameBoard;

    public void initialize() {

        game = new Game();
        gameBoard = new GameBoard(game);


        gameContainer.getChildren().add(gameBoard);
    }


}
