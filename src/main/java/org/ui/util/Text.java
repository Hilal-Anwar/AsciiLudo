package org.ui.util;

import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;


public class Text {
    public static String getColorText(String text, Colors colors) {

        return addTextureToString(text,AttributedStyle.BLACK,getAnsiColorCode(colors));

        //return addTextureToString(text, Colors.WHITE, colors);
    }

    private static String addTextureToString(String s, int bColors, int fColors) {
        return new AttributedStringBuilder().
                style(AttributedStyle.DEFAULT.

                        foreground(fColors)).
                append(s).toAnsi();
    }
    private static int  getAnsiColorCode(Colors colors) {
        return switch (colors){
            case RED -> AttributedStyle.RED;
            case BLUE -> AttributedStyle.BLUE;
            case GREEN -> AttributedStyle.GREEN;
            case YELLOW -> AttributedStyle.YELLOW;
            case CYAN -> AttributedStyle.CYAN;
            case WHITE -> AttributedStyle.WHITE;
            case Black -> AttributedStyle.BLACK;
            default -> throw new IllegalStateException("Unexpected value: " + colors);
        };
    }
}
