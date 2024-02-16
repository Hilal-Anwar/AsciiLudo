package org.ui.core;

import org.ui.util.Colors;
import org.ui.util.Cursor;
import org.ui.util.Text;

public class LudoBoard extends Cursor {
    private final LudoBox[][] ludoBoxes = new LudoBox[15][15];

    public LudoBox[][] getLudoBoxes() {
        return ludoBoxes;
    }

    private final int size = 4;

    public LudoBoard(Cursor cursor) {
        super(cursor.getColumn(), cursor.getRow(), cursor.getColors());
    }

    public void draw(String message, int width, int height) {
        StringBuilder grid = new StringBuilder();
        String color_text = Text.getColorText("██", Colors.YELLOW);
        int row = 15, column = 15;
        var home = new Box(size * 6, size * 6, size * 3);
        var player_box = new Box[]{new Box(size, size, size * 5), new Box(size, size * 9, size * 5),
                new Box(size * 9, size, size * 5), new Box(size * 9, size * 9, size * 5)};
        var non = new Box[]{new Box(size * 2, size * 2, size), new Box(size * 4, size * 2, size),
                new Box(size * 2, size * 4, size), new Box(size * 4, size * 4, size),
                new Box(size * 2, size * 10, size), new Box(size * 4, size * 10, size),
                new Box(size * 10, size * 2, size), new Box(size * 12, size * 2, size),
                new Box(size * 10, size * 4, size), new Box(size * 12, size * 4, size),
                new Box(size * 2, size * 12, size), new Box(size * 4, size * 12, size),
                new Box(size * 10, size * 10, size), new Box(size * 12, size * 10, size),
                new Box(size * 10, size * 12, size), new Box(size * 12, size * 12, size)
        };
        for (int i = 0; i < row * size + 1; i++) {
            for (int j = 0; j < column * size + 1; j++) {
                int u = 0;
                for (var p : player_box) {
                    if (((j > p.x() && j < (p.x() + p.c())) &&
                            (i > p.y() && i < (p.y() + p.c()))) && isNon(i, j, non)) {
                        u = 1;
                        break;
                    }
                }
                if (u == 0 && (j % size == 0 || i % size == 0) || ludoBoxes[i / size][j / size].isFilled())
                 {
                    if (((j > home.x() && j < (home.x() + home.c())) && (i > home.y() && i < (home.y() + home.c()))))
                        grid.append("  ");
                    else
                        grid.append(getText(i, j, "██"));
                } else if (i % (size / 2) == 0 && j % (size / 2) == 0 && ludoBoxes[i / size][j / size].getTokens() != null)
                    grid.append(getText(i, j, ludoBoxes[i / size][j / size].getTokens()[0].tokensType().getColorText()));
                else
                    grid.append("  ");
            }
            grid.append('\n');
        }
        System.out.println(grid.append(message));
    }

    private boolean nonsense(int i, int j) {
        return (i % 6 == 0 && j % 6 == 0) &&
                (i % 7 == 0 && j % 7 == 0) &&
                (i % 8 == 0 && j % 8 == 0);
    }

    private boolean isNon(int i, int j, Box[] non) {
        for (var m : non) {
            if ((j >= m.x() && j <= (m.x() + m.c())) && (i >= m.y() && i <= (m.y() + m.c())))
                return false;
        }
        return true;
    }

    private String getText(int i, int j, String text) {
        var z = isSelectedBox(i, j);
        if (isCursorPoint(i, j))
            return Text.getColorText(text, getColors());
        else if (z.condition()) {
            return Text.getColorText(text, ludoBoxes[z.y][z.x].getColors());
        } else return text;
    }

    private Val isSelectedBox(int i, int j) {
        int x = j / size;
        int y = i / size;
        x = (x == 15) ? x - 1 : x;
        y = (y == 15) ? y - 1 : y;
        if (ludoBoxes[y][x].isSelected()) {
            return new Val(true, x, y);
        } else {
            int i1 = (j != 0 && j % size == 0) ? j / size - 1 : j / size;
            if (ludoBoxes[y][i1].isSelected()) {
                return new Val(true, i1, y);
            } else {
                int i2 = (i != 0 && i % size == 0) ? i / size - 1 : i / size;
                if (ludoBoxes[i2][x].isSelected())
                    return new Val(ludoBoxes[i2][x].isSelected(), x, i2);
                return new Val(ludoBoxes[i2][i1].isSelected(), i1, i2);
            }
        }

    }

    private record Val(boolean condition, int x, int y) {
    }


    private boolean isCursorPoint(int i, int j) {
        if (((j == getColumn() * size || j == getColumn() * size + size) &&
                (i >= getRow() * size && i <= getRow() * size + size)))
            return true;
        else return (i == getRow() * size || i == getRow() * size + size) &&
                (j >= getColumn() * size && j <= getColumn() * size + size);
    }
}
