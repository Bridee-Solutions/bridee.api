package com.bridee.api.pattern.strategy.message;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface MessageStrategy<T, K> {
    T sendMessage(K messageObject) throws IOException, WriterException;
}
