package com.bridee.api.exception;

public class BadRequestEntityException extends RuntimeException {
    public BadRequestEntityException(String message) {
        super(message);
    }
}
