package com.example.quadclashjodumodhu.MyGame.Model;

public class HumanPlayer extends Player {
    public HumanPlayer() {
        super("You");
    }

    @Override
    public Card chooseCardToPlay() {
        // UI দ্বারা নির্বাচিত কার্ড রিটার্ন করবে
        return null;
    }
}