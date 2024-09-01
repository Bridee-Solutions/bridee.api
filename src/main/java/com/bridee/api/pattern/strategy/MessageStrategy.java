package com.bridee.api.pattern.strategy;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface MessageStrategy {
    void sendMessage(String to, String from, String message) throws IOException, WriterException;
}
