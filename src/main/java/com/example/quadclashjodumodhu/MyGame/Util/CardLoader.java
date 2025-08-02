package com.example.quadclashjodumodhu.MyGame.Util;
import com.example.quadclashjodumodhu.MyGame.Model.Card;
import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;
public class CardLoader {
    private static final String CARD_IMAGE_BASE = "/images/cards/";
    private static final String CARD_BACK_IMAGE = "/images/card_back.png";
    private final Map<String, Image> imageCache = new HashMap<>();

    public CardLoader() {
        loadCardBack();
    }

    private void loadCardBack() {
        try {
            Image backImage = new Image(getClass().getResourceAsStream(CARD_BACK_IMAGE));
            imageCache.put("back", backImage);
        } catch (Exception e) {
            System.err.println("Error loading card back: " + e.getMessage());
        }
    }

    public Image loadCardImage(Card card) {
        String key = card.getSuit().name() + "_" + card.hashCode();
        if (!imageCache.containsKey(key)) {
            String imagePath = CARD_IMAGE_BASE + card.getSuit().name().toLowerCase() +
                    "_" + (Math.abs(card.hashCode()) % 4 + 1) + ".png";
            try {
                Image image = new Image(getClass().getResourceAsStream(imagePath));
                imageCache.put(key, image);
            } catch (Exception e) {
                System.err.println("Error loading card image: " + imagePath);
                return getCardBackImage();
            }
        }
        return imageCache.get(key);
    }

    public Image getCardBackImage() {
        return imageCache.get("back");
    }
}

