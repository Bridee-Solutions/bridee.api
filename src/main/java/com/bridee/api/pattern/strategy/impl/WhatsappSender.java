package com.bridee.api.pattern.strategy.impl;

import com.bridee.api.client.WhatsappClient;
import com.bridee.api.client.enums.WhatsappMessageType;
import com.bridee.api.client.dto.request.WhatsappRequestDto;
import com.bridee.api.pattern.strategy.MessageStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WhatsappSender implements MessageStrategy {

    @Value("${services.bridee.whatsapp.apiKey}")
    private String apiKey;
    @Value("${services.bridee.whatsapp.phoneSender}")
    private String phoneSender;
    private final WhatsappClient whatsappClient;

    @Override
    public void sendMessage(String to, String from, String message) {
        WhatsappRequestDto requestDto = new WhatsappRequestDto();
        requestDto.setApiKey(apiKey);
        requestDto.setMessageCaption("Seu convite para o casamento");
        requestDto.setPhoneNumber(phoneSender);
        requestDto.setMessageBody(message);
        requestDto.setMessageType(WhatsappMessageType.IMAGE.getValue());
        requestDto.setContactPhoneNumber(to);
        requestDto.setMessageCustomId("Bridee");
        requestDto.setMessageBodyFilename("Invite.png");
        requestDto.setMessageBodyMimetype("image/png");
        whatsappClient.sendMessage(requestDto);
    }
}
