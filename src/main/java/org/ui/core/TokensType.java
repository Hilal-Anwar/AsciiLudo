package org.ui.core;

import org.ui.util.Colors;
import org.ui.util.Text;

public enum TokensType {
    RED("██", Colors.RED, 0),
    BLUE("██", Colors.BLUE, 0),
    YELLOW("██", Colors.YELLOW, 0),
    GREEN("██", Colors.GREEN, 0),
    NONE("  ", Colors.WHITE, 0);
    private final String colorText;
    private final Colors colors;
    private int count;

    TokensType(String text, Colors colors, int count) {
        this.colors = colors;
        this.colorText = Text.getColorText(text, colors);
        this.count = count;
    }

    public String getColorText() {
        return colorText;
    }

    public Colors getColors() {
        return colors;
    }

    public void moveToken(int c) {
        this.count = this.count + c;
    }

    public void resetPosition() {
        this.count = 0;
    }

    public int getTokenValue() {
        return this.count;
    }


}
