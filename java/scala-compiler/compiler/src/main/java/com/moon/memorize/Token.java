package com.moon.memorize;

/**
 * Created by lin on 2/11/17.
 */
public class Token {
    private int type;
    private String value;

    public Token(int type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }

    public int getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
