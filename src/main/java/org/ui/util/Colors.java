package org.ui.util;

public enum Colors {
    Black("\033[0;30"), //BLACK
    RED("\033[0;31m"),   // RED
    GREEN("\033[0;32m"),   // GREEN
    YELLOW("\033[0;33m"),  // YELLOW
    WHITE("\033[0;97m"),  // WHITE
    BLUE("\033[0;34m"),    // BLUE
    PURPLE("\033[0;35m"),  // PURPLE
    CYAN("\033[0;36m"),  // CYAN
    CYAN_BRIGHT("\033[0;96m");
    private final String color;

    Colors(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
