package com.moon.memorize;

/**
 * Created by lin on 2/11/17.
 */
public class PreviousParsedException extends RuntimeException{
    public PreviousParsedException() {
    }

    public PreviousParsedException(String message) {
        super(message);
    }
}
