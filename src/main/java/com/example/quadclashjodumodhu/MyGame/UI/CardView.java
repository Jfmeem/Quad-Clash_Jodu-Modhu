package com.example.quadclashjodumodhu.MyGame.UI;

import com.example.quadclashjodumodhu.MyGame.Model.Card;
import com.example.quadclashjodumodhu.MyGame.Util.CardLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CardView extends StackPane {
    private final Card card;
    private final ImageView imageView;

    public CardView(Card card, CardLoader cardLoader) {
        this.card = card;
        this.imageView = new ImageView(cardLoader.loadCardImage(card));
        imageView.setFitWidth(100);
        imageView.setFitHeight(140);

        getChildren().add(imageView);
//        setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 1);");
    }

    public Card getCard() {
        return card;
    }
}