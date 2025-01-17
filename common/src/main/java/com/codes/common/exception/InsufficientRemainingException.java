package com.codes.common.exception;

public class InsufficientRemainingException extends RuntimeException{
    public InsufficientRemainingException(String message) {
        super(message);
    }
}
