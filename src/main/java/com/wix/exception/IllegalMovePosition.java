package com.wix.exception;

public class IllegalMovePosition extends RuntimeException {
    public IllegalMovePosition(String message) {
        super(message);
    }
}
