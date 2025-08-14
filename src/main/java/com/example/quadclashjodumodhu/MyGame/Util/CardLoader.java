package com.example.quadclashjodumodhu.MyGame.Util;

//import com.example.quadclashjodumodhu.HelloApplication;
import com.example.quadclashjodumodhu.MyGame.Model.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

//import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
//import java.util.Objects;

public class CardLoader {
    private static final String RESOURCE_PATH = "file:D:\\nabila\\java_project\\Quad-Clash_Jodu-Modhu\\src\\main\\resources\\Suits";
    private static final Map<String, Image> imageCache = new HashMap<>();
    //private static final String CARD_BACK_IMAGE = "/images/card_back.jpg";

    public CardLoader() {
        loadCardBack();
    }


    public Image loadCardImage(Card card) {
        String imageName_val = card.getSuit().name().toLowerCase() + ".jpg";
//        String imagePath_val = RESOURCE_PATH + "\\" + imageName_val;

        if (!imageCache.containsKey(imageName_val)) {
            try  {
//                (InputStream is = getClass().getResourceAsStream(imagePath))
//                if (true) {
                    String a = "file:./images/" + imageName_val;
                    System.out.println(a);
                    Image image = new Image(a, 100, 140, true, true);
                    imageCache.put(imageName_val, image);
                    System.out.println(new ImageView(image).getX());
//                } else {
//                    System.err.println("Image not found--: " + imagePath);
//                    return createPlaceholderImage(card.getSuit());
//                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
                return createPlaceholderImage(card.getSuit());
            }
        }
        return imageCache.get(imageName_val);
    }

//    private void loadCardBack() {
//        String backImagePath ="card_back.jpg";
//        try (InputStream is = getClass().getResourceAsStream(backImagePath)) {
//            if (is != null) {
//                Image backImage = new Image(is);
//                imageCache.put("back", backImage);
//            } else {
//                System.err.println("Card back image not found at: " + backImagePath);
//            }
//        } catch (Exception e) {
//            System.err.println("Error loading card back: " + e.getMessage());
//        }
//    }
//private void loadCardBack() {
//
//    String backImagePath = "file:./images/card_back.jpg";
//    try (InputStream is = getClass().getResourceAsStream(backImagePath)) {
//        if (is != null) {
//            Image backImage = new Image(is, 100, 140, true, true);
//            imageCache.put("back", backImage);
//        } else {
//            System.err.println("Card back image not found at: " + backImagePath);
//        }
//    } catch (Exception e) {
//        System.err.println("Error loading card back: " + e.getMessage());
//    }
//}
private void loadCardBack() {
    String backImagePath = "file:./images/card_back.jpg";

    if (!imageCache.containsKey("back")) {
        try {
            Image backImage = new Image(backImagePath, 100, 140, true, true);
            imageCache.put("back", backImage);
            System.out.println("Card back loaded from: " + backImagePath);
        } catch (Exception e) {
            System.err.println("Error loading card back: " + e.getMessage());
            // Create default back image if loading fails
            Image defaultBack = createDefaultBackImage();
            imageCache.put("back", defaultBack);
        }
    }
}





    private Image createPlaceholderImage(Card.Suit suit) {
        WritableImage img = new WritableImage(100, 140);
        PixelWriter pw = img.getPixelWriter();
        Color color = switch(suit) {
            case DEVILQUEEN -> Color.RED;
            case EMBERLORE -> Color.BLUE;
            case VALORA -> Color.GREEN;
            case WHISPERSOFWINGS -> Color.PURPLE;

        };

        for (int y = 0; y < 140; y++) {
            for (int x = 0; x < 100; x++) {
                pw.setColor(x, y, color);
            }
        }
        return img;
    }

    public static Image getCardBackImage() {
        return imageCache.get("back");
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

    public Image getCardImage(Card card) {
        return null;
    }
}