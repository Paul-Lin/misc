package com.moon.memorize;

/**
 * Created by lin on 2/11/17.
 */
public class NoViableException extends RuntimeException{
    public NoViableException() {
    }

    public NoViableException(String message) {
        super(message);
    }
}
