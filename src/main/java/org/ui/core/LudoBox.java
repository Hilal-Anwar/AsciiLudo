package org.ui.core;

import org.ui.util.Colors;

import java.util.Arrays;
import java.util.LinkedList;

public class LudoBox {
    private LinkedList<Token> tokens;
    private boolean selected;
    private Colors colors = Colors.WHITE;
    private boolean isFilled = false;

    public LudoBox(boolean selected, Colors colors, LinkedList<Token> tokens) {
        this.tokens = tokens;
        this.selected = selected;
        this.colors = colors;
    }

    public void setToken(LinkedList<Token> tokens) {
        this.tokens = tokens;
    }
    public Token getToken(TokensType turn){
        for(var vr:getTokens()){
            if (vr.tokensType().equals(turn))
                return vr;
        }
        return null;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean condition) {
        isFilled = condition;
    }

    public LinkedList<Token> getTokens() {
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
                "tokens=" + tokens +
                ", selected=" + selected +
                ", colors=" + colors +
                ", isFilled=" + isFilled +
                '}';
    }

    public Colors getColors() {
        return colors;
    }
}
