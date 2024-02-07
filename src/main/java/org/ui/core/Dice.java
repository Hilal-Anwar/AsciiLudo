package org.ui.core;


import java.util.ArrayList;
import java.util.Arrays;

public class Dice {

    private static int roll_dice() {
        return (int) (Math.random() * 6 + 1);
    }

    public ArrayList<int[]> getDiceValue() {
        int vale = roll_dice();
        ArrayList<int[]> dice = new ArrayList<>();
        switch (vale) {
            case 1 -> {
                dice.add(new int[]{7, 7});
            }
            case 2 -> {
                dice.add(new int[]{6, 6});
                dice.add(new int[]{8, 8});
            }
            case 3 -> {
                dice.add(new int[]{6, 6});
                dice.add(new int[]{7, 7});
                dice.add(new int[]{8, 8});

            }
            case 4 -> {
                dice.add(new int[]{6, 6});
                dice.add(new int[]{6, 8});
                dice.add(new int[]{8, 6});
                dice.add(new int[]{8, 8});
            }

            case 5 -> {
                dice.add(new int[]{6, 6});
                dice.add(new int[]{6, 8});
                dice.add(new int[]{7, 7});
                dice.add(new int[]{8, 6});
                dice.add(new int[]{8, 8});
            }
            case 6 -> {
                dice.add(new int[]{6, 6});
                dice.add(new int[]{6, 8});
                dice.add(new int[]{7, 6});
                dice.add(new int[]{7, 8});
                dice.add(new int[]{8, 6});
                dice.add(new int[]{8, 8});
            }

        }
        return dice;
    }

}
