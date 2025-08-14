package com.example.quadclashjodumodhu.MyGame.UI;

import com.example.quadclashjodumodhu.MyGame.Model.Card;
import com.example.quadclashjodumodhu.MyGame.Util.CardLoader;
import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class CardView extends StackPane {
    private final Card card;
    private final ImageView cardImageView;
    private final Button cardButton;
    private boolean isSelected = false;
    private boolean isEnabled = true;

    // Animation properties
    private ScaleTransition hoverGrowTransition;
    private ScaleTransition hoverShrinkTransition;
    private RotateTransition selectionSpinTransition;

    public CardView(Card card, CardLoader cardLoader) {
        this.card = card;

        // Load and setup card image
        this.cardImageView = new ImageView(cardLoader.loadCardImage(card));
        setupCardImage();

        // Create interactive button
        this.cardButton = new Button();
        setupCardButton();

        // Setup animations
        setupAnimations();

        // Setup tooltip with card info
        setupTooltip();

        // Initial styling
        updateVisualState();

        System.out.println(" CardView created for: " + card.getSuit());
    }

    private void setupCardImage() {
        cardImageView.setFitWidth(100);
        cardImageView.setFitHeight(140);
        cardImageView.setPreserveRatio(true);
        cardImageView.setSmooth(true);

        // Add subtle shadow
        cardImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0.3, 2, 2);");
    }

    private void setupCardButton() {
        cardButton.setGraphic(cardImageView);
        cardButton.setStyle("-fx-background-color: transparent; " +
                "-fx-border-color: transparent; " +
                "-fx-padding: 0; " +
                "-fx-cursor: hand;");

        // Button event handlers
        cardButton.setOnAction(e -> toggleSelection());
        cardButton.setOnMouseEntered(e -> handleMouseEnter());
        cardButton.setOnMouseExited(e -> handleMouseExit());

        getChildren().add(cardButton);
        setPrefSize(120, 160); // Slightly larger than image for padding
    }

    private void setupAnimations() {
        // Hover grow animation
        hoverGrowTransition = new ScaleTransition(Duration.millis(150), this);
        hoverGrowTransition.setToX(1.1);
        hoverGrowTransition.setToY(1.1);

        // Hover shrink animation
        hoverShrinkTransition = new ScaleTransition(Duration.millis(150), this);
        hoverShrinkTransition.setToX(1.0);
        hoverShrinkTransition.setToY(1.0);

        // Selection spin animation
        selectionSpinTransition = new RotateTransition(Duration.millis(300), this);
        selectionSpinTransition.setByAngle(360);
    }

    private void setupTooltip() {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(buildTooltipText());
        tooltip.setStyle("-fx-background-color: rgba(44, 62, 80, 0.95); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 12px; " +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5; " +
                "-fx-padding: 8;");

        Tooltip.install(cardButton, tooltip);
    }

    private String buildTooltipText() {
        return " Card: " + card.getSuit() + "\n" +
                "Type: " + card.getSuit().name() + "\n" +
                "\n Click to select this card";
    }

    public void toggleSelection() {
        if (!isEnabled) {
            System.out.println("⚠ Card is disabled, cannot select");
            return;
        }

        setSelected(!isSelected);

        // Play selection animation
        if (isSelected) {
            selectionSpinTransition.play();
            System.out.println(" Selected: " + card.getSuit());
        } else {
            System.out.println(" Deselected: " + card.getSuit());
        }
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        updateVisualState();
    }

    private void handleMouseEnter() {
        if (!isEnabled) return;

        // Stop any running shrink animation
        hoverShrinkTransition.stop();
        hoverGrowTransition.play();

        // Add glow effect
        setStyle(getStyle() + "; -fx-effect: dropshadow(gaussian, #3498db, 15, 0.7, 0, 0);");
    }

    private void handleMouseExit() {
        if (!isEnabled) return;

        // Stop any running grow animation
        hoverGrowTransition.stop();
        hoverShrinkTransition.play();

        // Return to normal visual state
        updateVisualState();
    }


    private void updateVisualState() {
        String baseStyle = "-fx-background-radius: 10; -fx-border-radius: 10; -fx-padding: 5;";

        if (!isEnabled) {
            // Disabled state
            setStyle(baseStyle +
                    "-fx-opacity: 0.5; " +
                    "-fx-effect: none;");
            cardButton.setDisable(true);
        } else if (isSelected) {
            // Selected state - golden glow
            setStyle(baseStyle +
                    "-fx-background-color: rgba(241, 196, 15, 0.3); " +
                    "-fx-border-color: #f1c40f; " +
                    "-fx-border-width: 3; " +
                    "-fx-effect: dropshadow(gaussian, gold, 20, 0.8, 0, 0);");
            cardButton.setDisable(false);
        } else {
            // Normal state
            setStyle(baseStyle +
                    "-fx-background-color: transparent; " +
                    "-fx-border-color: rgba(255, 255, 255, 0.2); " +
                    "-fx-border-width: 1; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0.3, 2, 2);");
            cardButton.setDisable(false);
        }
    }


    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
        updateVisualState();

        if (!enabled && isSelected) {
            setSelected(true);
        }
    }

    public void playEntranceAnimation() {
        // Start from small scale and fade in
        setScaleX(0.1);
        setScaleY(0.1);
        setOpacity(0.0);

        // Scale up animation
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(400), this);
        scaleIn.setToX(1.0);
        scaleIn.setToY(1.0);

        // Fade in animation
        FadeTransition fadeIn = new FadeTransition(Duration.millis(400), this);
        fadeIn.setToValue(1.0);

        // Play both animations together
        ParallelTransition entrance = new ParallelTransition(scaleIn, fadeIn);
        entrance.play();
    }

    public void playExitAnimation(Runnable onComplete) {
        // Fade out and scale down
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), this);
        fadeOut.setToValue(0.0);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(300), this);
        scaleOut.setToX(0.1);
        scaleOut.setToY(0.1);

        ParallelTransition exit = new ParallelTransition(fadeOut, scaleOut);
        exit.setOnFinished(e -> {
            if (onComplete != null) {
                onComplete.run();
            }
        });
        exit.play();
    }

    public void flashHighlight(String color) {
        String originalStyle = getStyle();

        Timeline flash = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(styleProperty(), originalStyle +
                                "; -fx-effect: dropshadow(gaussian, " + color + ", 25, 1.0, 0, 0);")),
                new KeyFrame(Duration.millis(200),
                        new KeyValue(styleProperty(), originalStyle)),
                new KeyFrame(Duration.millis(400),
                        new KeyValue(styleProperty(), originalStyle +
                                "; -fx-effect: dropshadow(gaussian, " + color + ", 25, 1.0, 0, 0);")),
                new KeyFrame(Duration.millis(600),
                        new KeyValue(styleProperty(), originalStyle))
        );
        flash.play();
    }

    // Getter methods
    public Card getCard() {
        return card;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String toString() {
        return "CardView{" +
                "card=" + card.getSuit() +
                ", selected=" + isSelected +
                ", enabled=" + isEnabled +
                "}";
    }
}