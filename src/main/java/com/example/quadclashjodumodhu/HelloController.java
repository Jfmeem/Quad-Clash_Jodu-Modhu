package com.example.quadclashjodumodhu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.example.quadclashjodumodhu.MyGame.Core.Game;
public class HelloController {
    @FXML
    private Label welcomeText;
    Game g = new Game();
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("This is java projects");
    }
}
