package com.wix.exception;

public class IllegalTailNumber extends RuntimeException {
    public IllegalTailNumber(String message) {
        super(message);
    }
}
