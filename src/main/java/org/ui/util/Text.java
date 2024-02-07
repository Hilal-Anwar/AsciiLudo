package org.ui.util;

public class Text {
    public static String getColorText(String text, Colors colors) {
        return colors != null ? colors.getColor() + text + "\33[0m":text;
    }
}
