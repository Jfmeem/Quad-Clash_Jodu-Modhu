package com.example.quadclashjodumodhu.MyGame.Util;

import com.example.quadclashjodumodhu.MyGame.Model.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CardLoader {
    private static final String RESOURCE_PATH = "file:D:\\nabila\\java_project\\Quad-Clash_Jodu-Modhu\\src\\main\\resources\\Suits";
    private final Map<String, Image> imageCache = new HashMap<>();

    public CardLoader() {
        loadCardBack();
    }

    public Image loadCardImage(Card card) {
        String imageName = card.getSuit().name().toLowerCase() + ".jpg";
        String imagePath = RESOURCE_PATH + "\\" + imageName;

        if (!imageCache.containsKey(imagePath)) {
            try  {
//                (InputStream is = getClass().getResourceAsStream(imagePath))
                if (true) {
                    Image image = new Image(getClass().getResource("Suits/" + imageName).toExternalForm(), 100, 140, true, true);
                    imageCache.put(imagePath, image);
                    System.out.println(new ImageView(image).getX());
                } else {
                    System.err.println("Image not found--: " + imagePath);
                    return createPlaceholderImage(card.getSuit());
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
                return createPlaceholderImage(card.getSuit());
            }
        }
        return imageCache.get(imagePath);
    }

    private void loadCardBack() {
        String backImagePath = RESOURCE_PATH + "card_back.jpg";
        try (InputStream is = getClass().getResourceAsStream(backImagePath)) {
            if (is != null) {
                Image backImage = new Image(is);
                imageCache.put("back", backImage);
            } else {
                System.err.println("Card back image not found at: " + backImagePath);
            }
        } catch (Exception e) {
            System.err.println("Error loading card back: " + e.getMessage());
        }
    }

    private Image createPlaceholderImage(Card.Suit suit) {
        WritableImage img = new WritableImage(100, 140);
        PixelWriter pw = img.getPixelWriter();
        Color color = switch(suit) {
            case DEVIL_QUEEN -> Color.RED;
            case EMBERLORE -> Color.BLUE;
            case VALORA -> Color.GREEN;
            case WHISPERS_OF_WINGS -> Color.PURPLE;
        };

        for (int y = 0; y < 140; y++) {
            for (int x = 0; x < 100; x++) {
                pw.setColor(x, y, color);
            }
        }
        return img;
    }

    public Image getCardBackImage() {
        return imageCache.getOrDefault("back", createDefaultBackImage());
    }

    private Image createDefaultBackImage() {
        WritableImage img = new WritableImage(100, 140);
        PixelWriter pw = img.getPixelWriter();
        for (int y = 0; y < 140; y++) {
            for (int x = 0; x < 100; x++) {
                pw.setColor(x, y, Color.GRAY);
            }
        }
        return img;
    }
}