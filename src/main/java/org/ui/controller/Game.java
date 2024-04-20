package org.ui.controller;


import org.ui.core.*;
import org.ui.util.*;

import java.util.*;

import static org.ui.util.Colors.*;


public class Game extends Display{

    private final LudoBox[][] ludoBoxs;
    private GameStatus status;
    private int diceValue;
    private TreeMap<TokensType, List<Token>> list_token = new TreeMap<>();
    TreeMap<TokensType, Point> endPathDirection = new TreeMap<>(Map.of(TokensType.BLUE, new Point(0, -1),
            TokensType.RED, new Point(1, 0), TokensType.GREEN, new Point(0, 1),
            TokensType.YELLOW, new Point(-1, 0)));
    private final TreeMap<TokensType, Boolean> isAtLeastOneOut = new TreeMap<>(
            Map.of(TokensType.RED, false,
                    TokensType.BLUE, false,
                    TokensType.GREEN, false,
                    TokensType.YELLOW, false)
    );
    private boolean isAnyBoxSelected = false;
    private final LinkedList<Point> special_red = new LinkedList<>(List.of(new Point(2, 8)
            , new Point(1, 6), new Point(1, 7), new Point(2, 7),
            new Point(3, 7), new Point(4, 7), new Point(5, 7)));
    private final LinkedList<Point> special_yellow =
            new LinkedList<>(List.of(new Point(12, 6), new Point(13, 8), new Point(9, 7),
                    new Point(10, 7), new Point(11, 7), new Point(12, 7), new Point(13, 7)));
    private final LinkedList<Point> special_green =
            new LinkedList<>(List.of(new Point(6, 2), new Point(8, 1), new Point(7, 1),
                    new Point(7, 2), new Point(7, 3), new Point(7, 4), new Point(7, 5)));
    private final LinkedList<Point> special_blue =
            new LinkedList<>(List.of(new Point(8, 12), new Point(6, 13), new Point(7, 13),
                    new Point(7, 12),
                    new Point(7, 11), new Point(7, 10), new Point(7, 9)));
    private final TreeMap<TokensType, Point> start_points = new TreeMap<>(Map.of(
            TokensType.RED, new Point(1, 6)
            , TokensType.GREEN, new Point(8, 1),
            TokensType.YELLOW, new Point(13, 8),
            TokensType.BLUE, new Point(6, 13)));


    private final int[][] dice_reset = {
            {6, 6}, {6, 7}, {6, 8},
            {7, 6}, {7, 7}, {7, 8},
            {8, 6}, {8, 7}, {8, 8}};
    private TreeMap<String, Point> red_init_position;
    private TreeMap<String, Point> green_init_position;
    private TreeMap<String, Point> blue_init_position;
    private TreeMap<String, Point> yellow_init_position;
    private final TreeMap<TokensType, Point[]> token_tree = new TreeMap<>();
    private final LudoBoard ludoBoard = new LudoBoard(new Cursor(7, 7, CYAN));
    private final LinkedList<Point> path = new LinkedList<>(
            List.of(new Point(0, 6), new Point(1, 6),
                    new Point(2, 6), new Point(3, 6),
                    new Point(4, 6), new Point(5, 6),
                    new Point(6, 5), new Point(6, 4),
                    new Point(6, 3), new Point(6, 2),
                    new Point(6, 1), new Point(6, 0),
                    new Point(7, 0), new Point(8, 0),
                    new Point(8, 1), new Point(8, 2),
                    new Point(8, 3), new Point(8, 4),
                    new Point(8, 5), new Point(9, 6),
                    new Point(10, 6), new Point(11, 6),
                    new Point(12, 6), new Point(13, 6),
                    new Point(14, 6), new Point(14, 7),
                    new Point(14, 8), new Point(13, 8),
                    new Point(12, 8), new Point(11, 8),
                    new Point(10, 8), new Point(9, 8),
                    new Point(8, 9), new Point(8, 10),
                    new Point(8, 11), new Point(8, 12),
                    new Point(8, 13), new Point(8, 14),
                    new Point(7, 14), new Point(6, 14),
                    new Point(6, 13), new Point(6, 12),
                    new Point(6, 11), new Point(6, 10),
                    new Point(6, 9), new Point(5, 8),
                    new Point(4, 8), new Point(3, 8),
                    new Point(2, 8), new Point(1, 8),
                    new Point(0, 8), new Point(0, 7)
            ));

    public Game() {
        ludoBoxs = ludoBoard.getLudoBoxes();
        status = GameStatus.Rolling;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (i % 14 == 0 || j % 14 == 0) {
                    var c = getColorAsPerLocation(i, j);
                    if (c!= WHITE) {
                        LudoBox xu = new LudoBox(true, c,
                                new LinkedList<>(List.of(new Token("", TokensType.NONE))));
                        xu.setFilled(true);
                        ludoBoxs[i][j] = xu;
                    } else
                        ludoBoxs[i][j] = new LudoBox(false, WHITE,
                                new LinkedList<>(List.of(new Token("", TokensType.NONE))));
                } else
                    ludoBoxs[i][j] = new LudoBox(false, WHITE,
                            new LinkedList<>(List.of(new Token("", TokensType.NONE))));
            }
        }
        init();
        markSpecialPoint(special_green, GREEN);
        markSpecialPoint(special_red, RED);
        markSpecialPoint(special_blue, BLUE);
        markSpecialPoint(special_yellow, YELLOW);
    }

    private void markSpecialPoint(LinkedList<Point> points, Colors colors) {
        for (var p : points) {
            ludoBoxs[p.y()][p.x()] = new LudoBox(true, colors,
                    new LinkedList<>(List.of(new Token("", TokensType.NONE))));
        }
    }


    private Colors getColorAsPerLocation(int i, int j) {
        if (i <= 5 && j <= 5)
            return RED;
        else if (i <= 5 && j >= 9)
            return GREEN;
        else if (i >= 9 && j <= 5)
            return BLUE;
        else if (i >= 9 && j >= 9)
            return YELLOW;
        return WHITE;
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

    private boolean isAmongSpecialPoint(Point point) {
        return special_blue.contains(point) || special_red.contains(point) ||
                special_green.contains(point) || special_yellow.contains(point);
    }

    private void createPlayer(int x, int y, TokensType tokensType) {
        char id = tokensType.name().toLowerCase(Locale.ROOT).charAt(0);
        var t1 = new Token(id + "1", tokensType);
        var t2 = new Token(id + "2", tokensType);
        var t3 = new Token(id + "3", tokensType);
        var t4 = new Token(id + "4", tokensType);
        list_token.put(tokensType, List.of(t1, t2, t3, t4));
        ludoBoxs[y][x] = new LudoBox(true, tokensType.getColors(),
                new LinkedList<>(List.of(t1)));
        ludoBoxs[y + 2][x] = new LudoBox(true, tokensType.getColors(),
                new LinkedList<>(List.of(t2)));
        ludoBoxs[y][x + 2] = new LudoBox(true, tokensType.getColors(),
                new LinkedList<>(List.of(t3)));
        ludoBoxs[y + 2][x + 2] = new LudoBox(true, tokensType.getColors(),
                new LinkedList<>(List.of(t4)));
        final TreeMap<String, Point> points = new TreeMap<>(Map.of(
                id + "1", new Point(x, y),
                id + "2", new Point(x, y + 2),
                id + "3", new Point(x + 2, y),
                id + "4", new Point(x + 2, y + 2)));
        final Point[] point = {new Point(x, y), new Point(x, y + 2),
                new Point(x + 2, y), new Point(x + 2, y + 2)};
        switch (tokensType) {
            case RED -> red_init_position = points;
            case BLUE -> blue_init_position = points;
            case GREEN -> green_init_position = points;
            case YELLOW -> yellow_init_position = points;
        }
        token_tree.put(tokensType, point);

    }

    public void start() throws InterruptedException {
        TokensType turn = null;
        KeyBoardInput keyBoardInput = new KeyBoardInput(this);
        this.clear_display();
        ludoBoard.draw("Press enter to role the dice",
                terminal.getWidth(), terminal.getHeight());
        int i = 0;
        int itr = 0;
        Point[] cur = new Point[]{new Point(7, 7)};
        while (true) {
            String message = "Press enter to role the dice";
            var key = keyBoardInput.getKeyBoardKey();
            switch (key) {
                case UP, DOWN, RIGHT, LEFT -> {
                    if (status.equals(GameStatus.Selection)) {
                        itr = itr == cur.length - 1 ? 0 : itr + 1;
                        ludoBoard.setRow(cur[itr].y());
                        ludoBoard.setColumn(cur[itr].x());
                        isAnyBoxSelected = true;
                    }
                }
                case ENTER -> {
                    Point pnt;
                    if (status.equals(GameStatus.Rolling)) {
                        turn = TokensType.values()[i];
                        cur = token_tree.get(turn);
                        itr = 0;
                        message = role_dice(turn);
                        if (isAtLeastOneOut.get(turn) || diceValue >= 6) {
                            status = GameStatus.Selection;
                        }
                        if (diceValue < 6)
                            i = i == TokensType.values().length - 2 ? 0 : i + 1;
                    } else if (status.equals(GameStatus.Selection) &&
                            isAnyBoxSelected && turn != null) {
                        if (isAmongInitialPosition(turn, cur[itr]) && diceValue >= 6) {
                            move_the_token(turn, cur[itr],
                                    start_points.get(turn), diceValue);
                            diceValue = diceValue - 6;
                            status = GameStatus.Rolling;
                        } else if (diceValue >= 0) {
                            var ipt = cur[itr];
                            if (path.contains(ipt)) {
                                int index = path.indexOf(ipt);
                                int ce = diceValue;
                                var ioy = ludoBoxs[ipt.y()][ipt.x()].
                                        getToken(turn).getTokenValue();
                                if (ioy + diceValue <= 51) {
                                    pnt = path.get(run(index));
                                    move_the_token(turn, ipt, pnt, ce);
                                } else {
                                    if (ioy < 51) {
                                        int vtr = (ioy + diceValue - 51);
                                        diceValue = diceValue - vtr;
                                        pnt = path.get(run(index));
                                        var av = pnt;
                                        move_the_token(turn, ipt, pnt, diceValue);
                                        //System.out.print(pnt + "   " + vtr + "   " + diceValue + "   " + av);
                                        //System.exit(-1);
                                        pnt = new Point(pnt.x() + endPathDirection.get(turn).x() * vtr,
                                                pnt.y() + endPathDirection.get(turn).y() * vtr);
                                        move_the_token(turn, av, pnt, vtr);
                                    } else {
                                        pnt = new Point(ipt.x() + endPathDirection.get(turn).x() * diceValue,
                                                ipt.y() + endPathDirection.get(turn).y() * diceValue);
                                        move_the_token(turn, ipt, pnt, diceValue);
                                    }
                                }
                                status = GameStatus.Rolling;
                                isAnyBoxSelected = false;
                                diceValue = 0;
                            } else if(!isAmongInitialPosition(turn,cur[itr])) {
                                var ioy = ludoBoxs[ipt.y()][ipt.x()].
                                        getToken(turn).getTokenValue();
                                if (ioy + diceValue < 57) {
                                    pnt = new Point(ipt.x() + endPathDirection.get(turn).x() * diceValue,
                                            ipt.y() + endPathDirection.get(turn).y() * diceValue);
                                    move_the_token(turn, ipt, pnt, diceValue);
                                } else if (ioy + diceValue == 57) {
                                    move_the_token(turn, ipt, new Point(0, 0), diceValue);
                                } else {
                                    status = GameStatus.Rolling;
                                    isAnyBoxSelected = false;
                                    diceValue = 0;
                                }
                            }
                        }
                    }

                    //message = message + "\n" + list_token;
                }
                case ESC -> System.exit(-1);
            }
            if (!key.equals(Key.NONE)) {
                this.clear_display();
                ludoBoard.draw(message,  terminal.getWidth(), terminal.getHeight());
            }
            keyBoardInput.setKeyBoardKey(Key.NONE);
            Thread.sleep(10);
        }
    }

    private String role_dice(TokensType turn) {
        for (var z : dice_reset) {
            ludoBoxs[z[0]][z[1]] = new LudoBox(false,
                    WHITE, new LinkedList<>(List.of(
                    new Token("",
                            TokensType.NONE))));
        }
        var m = Dice.getDiceValue();
        for (var z : m.dice()) {
            ludoBoxs[z[0]][z[1]] = new LudoBox(false,
                    WHITE, new LinkedList<>(List.of(
                    new Token("dice", turn))));
        }
        diceValue = diceValue >= 6 ? diceValue + m.value() : m.value();
        //diceValue = m.value();

        return nonsense(turn);

    }

    private String nonsense(TokensType turn) {
        if (!isAtLeastOneOut.get(turn) && diceValue < 6) {
            return "You must get atleast one 6 to get the token out";
        } else if (diceValue == 6) {
            isAtLeastOneOut.replace(turn, true);
            return "You got 6 you can roll the dice once more.";
        }
        return "Move the token";
    }

    private boolean isAmongInitialPosition(TokensType turn, Point point) {
        return switch (turn) {
            case RED -> red_init_position.containsValue(point);
            case YELLOW -> yellow_init_position.containsValue(point);
            case GREEN -> green_init_position.containsValue(point);
            case BLUE -> blue_init_position.containsValue(point);
            case NONE -> false;
        };
    }

    private void move_the_token(TokensType turn, Point ip, Point fp, int co) {
        var initPoint = ludoBoxs[ip.y()][ip.x()].getTokens();
        var fpPoint = ludoBoxs[fp.y()][fp.x()].getTokens();
        if (isAmongSpecialPoint(fp) || (!fpPoint.isEmpty() && isAmongSpecialPoint(ip) &&
                initPoint.getFirst().tokensType() == fpPoint.getFirst().tokensType())) {
            initPoint.getFirst().moveToken(isAmongInitialPosition(turn, ip) ? 1 : co);
            fpPoint.push(initPoint.getFirst());
            initPoint.pop();
            initPoint.add(new Token("", TokensType.NONE));
        } else {

            for (int j = 0; j < fpPoint.size(); j++) {

                if (fpPoint.get(j).tokensType() != turn) {
                    var token = fpPoint.get(j);

                    var tyr = switch (token.tokensType()) {
                        case TokensType.RED, TokensType.NONE -> red_init_position;
                        case TokensType.GREEN -> green_init_position;
                        case TokensType.BLUE -> blue_init_position;
                        case TokensType.YELLOW -> yellow_init_position;
                    };
                    Point index = tyr.get(token.tokenId());
                    if (index != null) {
                        ludoBoxs[index.y()][index.x()].getTokens().
                                addFirst(fpPoint.get(j));
                        fpPoint.get(j).resetPosition();
                        var type = fpPoint.get(j).tokensType();
                        var list = token_tree.get(type);
                        for (int i = 0; i < list.length; i++) {
                            if (list[i] == fp) {
                                list[i] = index;
                                break;
                            }
                        }
                    }
                    fpPoint.remove(j);
                }
            }
            //fpPoint.pop();
            int i;
            for (i = 0; i < initPoint.size(); i++) {
                if (initPoint.get(i).tokensType() == turn)
                    break;
            }
            fpPoint.add(initPoint.get(i));
            initPoint.get(i).moveToken(co);
            initPoint.remove(i);
            initPoint.add(new Token("", TokensType.NONE));
        }
        var list = token_tree.get(turn);
        for (int i = 0; i < list.length; i++) {
            if (list[i] == ip) {
                list[i] = fp;
                break;
            }
        }
        //System.out.println(initPoint);
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

    private int run(int a) {
        if (diceValue <= 6) {
            a = a + diceValue;
            if (a >= 52) {
                a = a - 52;
                diceValue = 0;

            }
        } else {
            a = a + 6;
            if (a >= 52) {
                a = a - 52;
                diceValue = diceValue - 6;
            }

        }
        return a;
    }

    record Path(PathType pathType, int a) {
    }
}
