package com.moon.memorize;

/**
 * Created by lin on 2/11/17.
 */
public class BacktrackLexer {
    public static final char EOF = (char) -1;
    public static final int EOF_TYPE = 1;
    public static final int LBRACK = 2;
    public static final int COMMA = 3;
    public static final int RBRACK = 4;
    public static final int EQUALS = 5;
    public static final int NAME = 6;

    public static final String tokenNames[] = {"n/a", "<EOF>", "[", ",", "]", "=", "<NAME>"};

    String input;
    int i = 0;
    char c;

    public BacktrackLexer(String input) {
        this.input = input;
        c = input.charAt(i);
    }

    public void consume() {
        i++;
        if (i >= input.length()) c = EOF;
        else c = input.charAt(i);
    }

    public void match(char x) {
        if (c == x) consume();
        else throw new RuntimeException("excepting " + x + "; found " + c);
    }

    public Token nextToken() {
        while (c != EOF) {
            if (c == ' ' || c == '\t' || c == '\n' || c == '\r')
                consume();
            else if (c == '[') {
                consume();
                return new Token(BacktrackLexer.LBRACK, tokenNames[BacktrackLexer.LBRACK]);
            } else if (c == ',') {
                consume();
                return new Token(BacktrackLexer.COMMA, tokenNames[BacktrackLexer.COMMA]);
            } else if (c == ']') {
                consume();
                return new Token(BacktrackLexer.RBRACK, tokenNames[BacktrackLexer.RBRACK]);
            } else if (c == '=') {
                consume();
                return new Token(BacktrackLexer.EQUALS, tokenNames[BacktrackLexer.EQUALS]);
            } else if (isLetter(c)){
                StringBuilder buf=new StringBuilder();
                while(isLetter(c)) {
                    buf.append(c);
                    consume();
                }
                return new Token(BacktrackLexer.NAME,buf.toString());
            }else{
                throw new RuntimeException("unknown token");
            }

        }
        return new Token(EOF_TYPE,tokenNames[EOF_TYPE]);
    }

    public boolean isLetter(char ch) {
        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
            return true;
        return false;
    }
}

