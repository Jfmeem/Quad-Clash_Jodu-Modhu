package com.example.quadclashjodumodhu.MyGame.UI;

import com.example.quadclashjodumodhu.MyGame.Model.Card;
import com.example.quadclashjodumodhu.MyGame.Util.CardLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CardView extends StackPane {
    private final Card card;
    private final ImageView imageView;
    private boolean isSelected;
    private final Button cardButton;


    public CardView(Card card, CardLoader cardLoader) {
        this.card = card;
        this.isSelected=false;
        this.imageView = new ImageView(cardLoader.loadCardImage(card));
        imageView.setFitWidth(100);
        imageView.setFitHeight(140);
        imageView.setPreserveRatio(true);
        cardButton = new Button();
        cardButton.setGraphic(imageView);
        cardButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        cardButton.setOnAction(e -> toggleSelection());


        getChildren().add(cardButton);
        setPrefSize(110, 150);
        updateStyle();
//        setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 1);");
    }
    private void updateStyle() {
        String shadow = isSelected ?
                "-fx-effect: dropshadow(gaussian, gold, 20, 0.7, 0, 0);" :
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0.5, 0, 1);";
        setStyle(shadow);
    }
    public void toggleSelection() {
        isSelected = !isSelected;
        updateStyle();
    }
    public boolean isSelected() { return isSelected; }

    public Card getCard() {
        return card;
    }
}