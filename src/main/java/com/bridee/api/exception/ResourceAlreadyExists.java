package com.bridee.api.exception;

public class ResourceAlreadyExists extends RuntimeException{

    public static final String MESSAGE = "Recurso já cadastrado!";

    public ResourceAlreadyExists() {
        super(MESSAGE);
    }

    public ResourceAlreadyExists(String message) {
        super(message);
    }

    public ResourceAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
