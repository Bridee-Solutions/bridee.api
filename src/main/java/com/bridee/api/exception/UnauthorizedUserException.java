package com.bridee.api.exception;

public class UnauthorizedUserException extends RuntimeException{

    public static final String MESSAGE = "Usuário não autorizado";

    public UnauthorizedUserException() {
        super(MESSAGE);
    }

    public UnauthorizedUserException(String message) {
        super(message);
    }
}
