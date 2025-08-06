package com.example.quadclashjodumodhu.MyGame.Model;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer() {
        super("You");
    }
    @Override
    public Card playCard() {
        // Let human choose card via UI or console
        System.out.println(name + ", choose a card to play:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(i + ": " + hand.get(i));
        }

        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();

        return hand.remove(index);
    }


    @Override
    public Card chooseCardToPlay() {
        // UI দ্বারা নির্বাচিত কার্ড রিটার্ন করবে
        return null;
    }
}