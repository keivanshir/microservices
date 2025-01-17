package com.codes.common.exception;

public class AccountDisabledException extends RuntimeException{
    public AccountDisabledException(String message) {
        super(message);
    }
}
