package com.example.quadclashjodumodhu.MyGame.UI;
import com.example.quadclashjodumodhu.MyGame.Model.Card;
import com.example.quadclashjodumodhu.MyGame.Util.CardLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CardView extends StackPane {
    private final Card card;
    private final ImageView cardImage;
    private final Button cardButton;
    private boolean isSelected;

    public CardView(Card card, CardLoader cardLoader) {
        this.card = card;
        this.isSelected = false;


        cardImage = new ImageView(cardLoader.loadCardImage(card));
        cardImage.setFitWidth(100);
        cardImage.setFitHeight(140);
        cardImage.setPreserveRatio(true);

        cardButton = new Button();
        cardButton.setGraphic(cardImage);
        cardButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        cardButton.setOnAction(e -> toggleSelection());

        getChildren().add(cardButton);
        setPrefSize(110, 150);
        updateStyle();
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

    public Card getCard() { return card; }
    public boolean isSelected() { return isSelected; }
}
