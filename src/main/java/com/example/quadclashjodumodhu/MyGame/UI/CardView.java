package com.example.quadclashjodumodhu.MyGame.UI;

import com.example.quadclashjodumodhu.MyGame.Model.Card;
import com.example.quadclashjodumodhu.MyGame.Util.CardLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CardView extends StackPane {
    private final Card card;
    private final ImageView imageView;
    private boolean isSelected;


    public CardView(Card card, CardLoader cardLoader) {
        this.card = card;
        this.imageView = new ImageView(cardLoader.loadCardImage(card));
        imageView.setFitWidth(100);
        imageView.setFitHeight(140);

        getChildren().add(imageView);
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