package org.ui.core;

import org.ui.util.*;

import java.util.Locale;


public class Main {
    private final int column = 15;
    private final int row = 15;
    private LudoBox ludoBoxs[][];
    Dice dice = new Dice();
    private int[][] dice_reset = {{6, 6}, {6, 7}, {6, 8},
            {7, 6}, {7, 7}, {7, 8}, {8, 6}, {8, 7}, {8, 8}};
    private final LudoBoard ludoBoard = new LudoBoard(new Cursor(3, 6, Colors.PURPLE));


    Main() {
        ludoBoxs = ludoBoard.getLudoBoxes();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (i % 14 == 0 || j % 14 == 0) {
                    var c = getColorAsPerLocation(i, j);
                    if (!c.equals(Colors.WHITE)) {
                        LudoBox xu = new LudoBox(true, c, null);
                        xu.setFilled(true);
                        ludoBoxs[i][j] = xu;
                    } else ludoBoxs[i][j] = new LudoBox(false, Colors.WHITE, null);
                } else ludoBoxs[i][j] = new LudoBox(false, Colors.WHITE, null);
            }
        }
        init();
        int[][] special_red = new int[][]{{2, 8}, {1, 6}, {1, 7}, {2, 7}, {3, 7}, {4, 7}, {5, 7}};
        int[][] special_yellow = new int[][]{{12, 6}, {13, 8}, {9, 7}, {10, 7}, {11, 7}, {12, 7}, {13, 7}};
        int[][] special_green = new int[][]{{6, 2}, {8, 1}, {7, 1}, {7, 2}, {7, 3}, {7, 4}, {7, 5}};
        int[][] special_blue = new int[][]{{8, 12}, {6, 13}, {7, 13}, {7, 12}, {7, 11}, {7, 10}, {7, 9}};
        markSpecialPoint(special_red, Colors.RED);
        markSpecialPoint(special_green, Colors.GREEN);
        markSpecialPoint(special_blue, Colors.BLUE);
        markSpecialPoint(special_yellow, Colors.YELLOW);


    }

    private void markSpecialPoint(int[][] points, Colors colors) {
        for (var p : points) {
            ludoBoxs[p[1]][p[0]] = new LudoBox(true, colors, null);
        }
    }

    private Colors getColorAsPerLocation(int i, int j) {
        if (i <= 5 && j <= 5)
            return Colors.RED;
        else if (i <= 5 && j >= 9)
            return Colors.GREEN;
        else if (i >= 9 && j <= 5)
            return Colors.BLUE;
        else if (i >= 9 && j >= 9)
            return Colors.YELLOW;
        return Colors.WHITE;
    }

    private void init() {
        //Red Box
        createPlayer(2, 2, TokensType.RED);
        //Red Green
        createPlayer(10, 2, TokensType.GREEN);
        //Red Blue
        createPlayer(2, 10, TokensType.BLUE);
        //Red Yellow
        createPlayer(10, 10, TokensType.YELLOW);
    }

    private void createPlayer(int x, int y, TokensType tokensType) {
        char id = tokensType.name().toLowerCase(Locale.ROOT).charAt(0);
        ludoBoxs[y][x] = new LudoBox(
                true, tokensType.getColors(), new Token(id + "1", tokensType));
        ludoBoxs[y + 2][x] = new LudoBox(
                true, tokensType.getColors(), new Token(id + "1", tokensType));
        ludoBoxs[y][x + 2] = new LudoBox(
                true, tokensType.getColors(), new Token(id + "3", tokensType));
        ludoBoxs[y + 2][x + 2] = new LudoBox(
                true, tokensType.getColors(), new Token(id + "4", tokensType));
    }

    public static void main(String[] args) {
        Main in = new Main();
        try {
            in.start();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws InterruptedException {
        var display = new Display();
        KeyBoardInput keyBoardInput = new KeyBoardInput(display);
        display.clear_display();   
        ludoBoard.draw("", 0, 0);
        int i = 0;
        while (true) {
            var key = keyBoardInput.getKeyBoardKey();
            switch (key) {
                case UP -> ludoBoard.move_cursor_up();
                case DOWN -> ludoBoard.move_cursor_down();
                case RIGHT -> ludoBoard.move_cursor_right();
                case LEFT -> ludoBoard.move_cursor_left();
                case ESC -> System.exit(-1);
                case ENTER -> {
                    var turn = TokensType.values()[i];
                    move(turn);
                    i = i == TokensType.values().length - 1 ? 0 : i + 1;
                }
            }
            if (!key.equals(Key.NONE)) {
                System.out.print("\u001b[H");
                ludoBoard.draw("", 0, 0);
            }
            keyBoardInput.setKeyBoardKey(Key.NONE);
            Thread.sleep(10);

        }
    }

    private void move(TokensType turn) {
        for (var z : dice_reset) {
            ludoBoxs[z[0]][z[1]] = new LudoBox(false, Colors.WHITE, null);
        }
        var m = dice.getDiceValue();
        for (var z : m) {
            ludoBoxs[z[0]][z[1]] = new LudoBox(
                    false, Colors.WHITE, new Token("dice", turn));
        }
    }

}
