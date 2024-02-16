package org.ui.util;

public class Cursor {
    private int column;


    private int row;
    private final Colors colors;

    public Cursor(int column, int row, Colors colors) {
        this.column = column;
        this.row = row;
        this.colors = colors;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void move_cursor_up() {
        row = fix_row(row - 1);
    }

    public void move_cursor_down() {
        row = fix_row(row + 1);
    }

    public void move_cursor_right() {
        column = fix_column(column + 1);
    }

    public void move_cursor_left() {
        column = fix_column(column - 1);
    }

    private int fix_column(int x) {
        return x > 14 ? 0 : x < 0 ? 14 : x;
    }

    private int fix_row(int y) {
        return y > 14 ? 0 : y < 0 ? 14 : y;
    }

    public int getColumn() {
        return this.column;
    }

    @Override
    public String toString() {
        return "Cursor{" +
                "column=" + column +
                ", row=" + row +
                ", colors=" + colors +
                '}';
    }

    public int getRow() {
        return this.row;
    }

    public Colors getColors() {
        return colors;
    }

}
