package com.bridee.api.exception;

public class SendEmailException extends RuntimeException {

    public static final String MESSAGE = "Erro ao enviar email";

  public SendEmailException() {
    super(MESSAGE);
  }

  public SendEmailException(String message) {
        super(message);
    }
}
