package com.example.quadclashjodumodhu;

import com.example.quadclashjodumodhu.MyGame.Core.Game;
import com.example.quadclashjodumodhu.MyGame.UI.GameBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        Game game = new Game();
        GameBoard gameBoard = new GameBoard(game);

        Scene scene = new Scene(gameBoard, 800, 600);
        primaryStage.setTitle("Quad Clash: Jodu-Modhu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}