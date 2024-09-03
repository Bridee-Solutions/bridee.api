package com.bridee.api.exception;

public class ResourceNotFoundException extends RuntimeException{

    public static final String MESSAGE = "Recurso não encontrado";

    public ResourceNotFoundException() {
        super(MESSAGE);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
