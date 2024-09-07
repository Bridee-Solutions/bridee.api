package com.bridee.api.exception;

public class ImagesNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Images not found";

    public ImagesNotFoundException() {
        super(MESSAGE);
    }

    public ImagesNotFoundException(String message) {
        super(message);
    }

    public ImagesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
