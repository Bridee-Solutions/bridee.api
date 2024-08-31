package com.bridee.api.pattern.strategy;

public interface MessageStrategy {
    void sendMessage(String to, String from, String message);
}
