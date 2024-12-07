package com.bridee.api.pattern.strategy.message.impl;

import com.bridee.api.client.WhatsappClient;
import com.bridee.api.client.dto.response.WhatsappResponseDto;
import com.bridee.api.client.dto.request.WhatsappRequestDto;
import com.bridee.api.pattern.strategy.message.MessageStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WhatsappSender implements MessageStrategy<WhatsappResponseDto, WhatsappRequestDto> {

    @Value("${services.bridee.whatsapp.apiKey}")
    private String apiKey;
    @Value("${services.bridee.whatsapp.phoneSender}")
    private String phoneSender;

    private final WhatsappClient whatsappClient;

    @Override
    public WhatsappResponseDto sendMessage(WhatsappRequestDto requestDto) {
        requestDto.setApiKey(apiKey);
        requestDto.setPhoneNumber(phoneSender);
        requestDto.setMessageCustomId("Bridee");
        requestDto.setMessageBodyMimetype("image/png");
        WhatsappResponseDto whatsappResponse = whatsappClient.sendMessage(requestDto).getBody();
        if (whatsappResponse == null){
            throw new IllegalArgumentException("Não foi possível realizar o envio da mensagem de whatsapp");
        }
        return whatsappResponse;
    }
}
