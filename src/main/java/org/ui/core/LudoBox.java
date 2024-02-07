package org.ui.core;

import org.ui.util.Colors;

import java.util.Arrays;

public class LudoBox {
    private Token tokens[];
    private boolean selected;
    private Colors colors = Colors.WHITE;
    private boolean isFilled=false;

    public LudoBox(boolean selected, Colors colors,Token ...tokens) {
        this.tokens = tokens;
        this.selected = selected;
        this.colors = colors;
    }
    public boolean isFilled(){
        return isFilled;
    }
    public void setFilled(boolean condition){
        isFilled=condition;
    }

    public Token[] getTokens() {
        return tokens;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected, Colors colors) {
        this.selected = selected;
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "LudoBox{" +
                "tokens=" + Arrays.toString(tokens) +
                ", selected=" + selected +
                ", colors=" + colors +
                ", isFilled=" + isFilled +
                '}';
    }

    public Colors getColors() {
        return colors;
    }
}
