package org.ui.core;

import org.ui.util.Colors;
import org.ui.util.Text;




public enum TokensType {
    RED("██", Colors.RED),
    BLUE("██", Colors.BLUE),
    YELLOW("██", Colors.YELLOW),
    GREEN("██", Colors.GREEN),
    NONE("  ", Colors.WHITE);
    private final String colorText;
    private final Colors colors;


    TokensType(String text, Colors colors) {
        this.colors = colors;
        this.colorText = Text.getColorText(text, colors);
    }


    public String getColorText() {
        return colorText;
    }

    public Colors getColors() {
        return colors;
    }


}
