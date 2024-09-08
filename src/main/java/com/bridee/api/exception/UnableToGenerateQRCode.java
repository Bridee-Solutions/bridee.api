package com.bridee.api.exception;

public class UnableToGenerateQRCode extends RuntimeException {

    public static final String MESSAGE = "Não foi possível gerar o QRCode";

    public UnableToGenerateQRCode() {
        super(MESSAGE);
    }

    public UnableToGenerateQRCode(String message) {
        super(message);
    }

    public UnableToGenerateQRCode(String message, Throwable cause) {
        super(message, cause);
    }
}
