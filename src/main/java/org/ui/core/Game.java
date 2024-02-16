package org.ui.core;

import org.ui.util.*;

import java.util.*;


public class Game {
    private final int column = 15;
    private final int row = 15;
    private LudoBox ludoBoxs[][];
    private int[][] dice_reset = {
            {6, 6}, {6, 7}, {6, 8},
            {7, 6}, {7, 7}, {7, 8},
            {8, 6}, {8, 7}, {8, 8}};
    private LinkedList<Point> red_init_position;
    private LinkedList<Point> green_init_position;
    private LinkedList<Point> blue_init_position;
    private LinkedList<Point> yellow_init_position;
    private TreeMap<TokensType, Point[]> token_tree = new TreeMap<>();
    private final LudoBoard ludoBoard = new LudoBoard(new Cursor(7, 7, Colors.CYAN));
    private final LinkedHashSet<Point> path = new LinkedHashSet<>(List.of(new Point(0, 6), new Point(1, 6),
            new Point(2, 6), new Point(3, 6), new Point(4, 6), new Point(5, 6),
            new Point(6, 5), new Point(6, 4), new Point(6, 3), new Point(6, 2),
            new Point(6, 1), new Point(6, 0), new Point(7, 0), new Point(8, 0),
            new Point(8, 1), new Point(8, 2), new Point(8, 3), new Point(8, 4),
            new Point(8, 5), new Point(9, 6), new Point(10, 6), new Point(11, 6),
            new Point(12, 6), new Point(13, 6), new Point(14, 6), new Point(14, 7),
            new Point(14, 8), new Point(13, 8), new Point(12, 8), new Point(11, 8),
            new Point(10, 8), new Point(9, 8), new Point(8, 9), new Point(8, 10),
            new Point(8, 11), new Point(8, 12), new Point(8, 13), new Point(8, 14),
            new Point(7, 14), new Point(6, 14), new Point(6, 13), new Point(6, 12),
            new Point(6, 11), new Point(6, 10), new Point(6, 9), new Point(5, 8),
            new Point(4, 8), new Point(3, 8), new Point(2, 8), new Point(1, 8),
            new Point(0, 8), new Point(0, 7)
    ));

    Game() {
        ludoBoxs = ludoBoard.getLudoBoxes();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (i % 14 == 0 || j % 14 == 0) {
                    var c = getColorAsPerLocation(i, j);
                    if (!c.equals(Colors.WHITE)) {
                        LudoBox xu = new LudoBox(true, c, null);
                        xu.setFilled(true);
                        ludoBoxs[i][j] = xu;
                    } else
                        ludoBoxs[i][j] = new LudoBox(false, Colors.WHITE, null);
                } else
                    ludoBoxs[i][j] = new LudoBox(false, Colors.WHITE, null);
            }
        }

        init();

        int[][] special_red = new int[][]{{2, 8}, {1, 6}, {1, 7}, {2, 7}, {3, 7}, {4, 7}, {5, 7}};
        int[][] special_yellow = new int[][]{{12, 6}, {13, 8}, {9, 7}, {10, 7}, {11, 7}, {12, 7}, {13, 7}};
        int[][] special_green = new int[][]{{6, 2}, {8, 1}, {7, 1}, {7, 2}, {7, 3}, {7, 4}, {7, 5}};
        int[][] special_blue = new int[][]{{8, 12}, {6, 13}, {7, 13}, {7, 12}, {7, 11}, {7, 10}, {7, 9}};

        /*

        int[][] special_red = new int[][]{{6, 2}, {1, 6}, {1, 7}, {2, 7}, {3, 7}, {4, 7}, {5, 7}};
        int[][] special_yellow = new int[][]{{8, 12}, {13, 8}, {9, 7}, {10, 7}, {11, 7}, {12, 7}, {13, 7}};
        int[][] special_green = new int[][]{{12, 6}, {8, 1}, {7, 1}, {7, 2}, {7, 3}, {7, 4}, {7, 5}};
        int[][] special_blue = new int[][]{{2, 8}, {6, 13}, {7, 13}, {7, 12}, {7, 11}, {7, 10}, {7, 9}};
        */
        markSpecialPoint(special_green, Colors.GREEN);
        markSpecialPoint(special_red, Colors.RED);
        markSpecialPoint(special_blue, Colors.BLUE);
        markSpecialPoint(special_yellow, Colors.YELLOW);
        /*
        for (var u : path) {
            LudoBox xu = new LudoBox(true, Colors.CYAN, null);
            xu.setFilled(true);
            ludoBoxs[u.y()][u.x()] = xu;
        }
        */
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
        ludoBoxs[y][x] = new LudoBox(true, tokensType.getColors(),
                new Token(id + "1", tokensType));
        ludoBoxs[y + 2][x] = new LudoBox(true, tokensType.getColors(),
                new Token(id + "1", tokensType));
        ludoBoxs[y][x + 2] = new LudoBox(true, tokensType.getColors(),
                new Token(id + "3", tokensType));
        ludoBoxs[y + 2][x + 2] = new LudoBox(true, tokensType.getColors(),
                new Token(id + "4", tokensType));
        final Point[] points = {new Point(x, y), new Point(x, y + 2),
                new Point(x + 2, y), new Point(x + 2, y + 2)};
        final Point[] point = {new Point(x, y), new Point(x, y + 2),
                new Point(x + 2, y), new Point(x + 2, y + 2)};
        switch (tokensType) {
            case RED -> red_init_position = new LinkedList<>(Arrays.asList(points));
            case BLUE -> blue_init_position = new LinkedList<>(Arrays.asList(points));
            case GREEN -> green_init_position = new LinkedList<>(Arrays.asList(points));
            case YELLOW -> yellow_init_position = new LinkedList<>(Arrays.asList(points));
        }
        token_tree.put(tokensType, point);

    }

    public static void main(String[] args) {
        Game in = new Game();
        try {
            in.start();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws InterruptedException {
        TokensType turn = null;
        var display = new Display();
        KeyBoardInput keyBoardInput = new KeyBoardInput(display);
        display.clear_display();
        ludoBoard.draw("Press enter to role the dice", 0, 0);
        int i = 0;
        int itr = 0;
        Point[] cur = new Point[]{new Point(7, 7)};
        while (true) {
            String message = "Press enter to role the dice";
            var key = keyBoardInput.getKeyBoardKey();
            switch (key) {
                case UP, DOWN, RIGHT, LEFT -> {
                    ludoBoard.setRow(cur[itr].y());
                    ludoBoard.setColumn(cur[itr].x());
                    itr = itr == cur.length - 1 ? 0 : itr + 1;
                }
                case ESC -> System.exit(-1);
                case ENTER -> {
                    turn = TokensType.values()[i];
                    cur = token_tree.get(turn);
                    itr = 0;
                    message = move(turn);
                    i = i == TokensType.values().length - 1 ? 0 : i + 1;
                }
            }
            if (!key.equals(Key.NONE)) {
                System.out.print("\u001b[H");
                ludoBoard.draw(message, 0, 0);
            }
            keyBoardInput.setKeyBoardKey(Key.NONE);
            Thread.sleep(10);
        }
    }

    private String move(TokensType turn) {
        String message = "";
        for (var z : dice_reset) {
            ludoBoxs[z[0]][z[1]] = new LudoBox(false, Colors.WHITE, null);
        }
        var m = Dice.getDiceValue();
        for (var z : m.dice()) {
            ludoBoxs[z[0]][z[1]] = new LudoBox(false, Colors.WHITE, new Token("dice", turn));
        }
        int dice_value = m.value();
        switch (turn) {
            case RED -> {
                if (dice_value != 6 && red_init_position.size() == 4) {
                    return "You should get at least 6 to start the game";
                }
                else {

                }
            }
            case GREEN -> {

            }
            case YELLOW -> {

            }
            case BLUE -> {

            }
        }
        for (var key : token_tree.keySet()) {
            for (var v : token_tree.get(key)) {
                ludoBoxs[v.y()][v.x()] = new LudoBox(false, Colors.WHITE, new Token("", key));
            }
        }
        return "";
    }

    private void move_the_token(TokensType turn, int ix, int iy, int fx, int fy) {

    }

    private ArrayList<Point> createPath() {
        ArrayList<Point> path = new ArrayList<>();
        int x = 1, y = 7;
        int p = 0, q = 0;
        int count = 1;
        int m = 1, n = 1;
        int v = 0;
        while (count <= 52) {
            path.add(new Point(x, y));
            if (v % 2 == 0)
                x = x + m;
            else y = y + n;

            if (count % 6 == 0) {
                if (count % 12 == 0) {
                    if (v % 2 != 0)
                        x = x + m;
                    else y = y + n;
                    path.add(new Point(x, y));
                } else {
                    v++;
                }
                p++;
                q++;
                m = p % 4 == 0 ? 1 : -1;

                n = q % 2 == 0 ? 1 : -1;
            }
            count++;
        }
        return path;
    }
}
