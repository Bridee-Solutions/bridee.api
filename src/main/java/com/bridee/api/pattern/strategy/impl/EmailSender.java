package com.bridee.api.pattern.strategy.impl;

import com.bridee.api.pattern.strategy.MessageStrategy;
import org.springframework.stereotype.Component;

@Component
public class EmailSender implements MessageStrategy {
    @Override
    public void sendMessage(String message) {

    }
}
