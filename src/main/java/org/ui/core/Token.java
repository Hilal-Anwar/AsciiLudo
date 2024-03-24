package org.ui.core;

import java.util.Objects;

public final class Token {
    private final String tokenId;
    private final TokensType tokensType;
    private  int count;

    public Token(String tokenId, TokensType tokensType, int count) {
        this.tokenId = tokenId;
        this.tokensType = tokensType;
        this.count = count;
    }

    public TokensType tokensType() {
        return tokensType;
    }

    public String tokenId() {
        return tokenId;
    }

    public Token(String tokenId, TokensType tokensType) {
        this(tokenId, tokensType, 0);
    }
    public void moveToken(int c) {
        this.count = count + c;
    }

    public void resetPosition() {
        this.count = 0;
    }

    public int getTokenValue() {
        return this.count;
    }
    @Override
    public String toString() {
        return "Token[" +
                "tokenId=" + tokenId + ", " +
                "tokensType=" + tokensType + ", " +
                "count=" + count + ']';
    }


}
