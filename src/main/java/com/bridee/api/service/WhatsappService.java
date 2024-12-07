package com.bridee.api.service;

import com.bridee.api.client.dto.request.WhatsappRequestDto;
import com.bridee.api.client.dto.response.WhatsappResponseDto;
import com.bridee.api.client.enums.WhatsappClientType;
import com.bridee.api.client.enums.WhatsappMessageType;
import com.bridee.api.exception.UnableToGenerateQRCode;
import com.bridee.api.pattern.strategy.message.impl.WhatsappSender;
import com.bridee.api.utils.QRCodeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class WhatsappService {

    private final WhatsappSender whatsappSender;

    public WhatsappResponseDto sendMessage(WhatsappRequestDto requestDto){
        WhatsappResponseDto whatsappResponseDto = new WhatsappResponseDto();
        if (requestDto.getWhatsappClientType().equals(WhatsappClientType.CONVIDADO)){
            whatsappResponseDto = sendMessageToGuest(requestDto);
        }else if(requestDto.getWhatsappClientType().equals(WhatsappClientType.CASAL)){
            whatsappResponseDto = sendMessageToCouple(requestDto);
        }
        return whatsappResponseDto;
    }

    public WhatsappResponseDto sendMessageToGuest(WhatsappRequestDto requestDto){
        if(requestDto.getMessageType().equals(WhatsappMessageType.IMAGE.getValue())){
            requestDto.setMessageBody(generateQRCode(requestDto));
        }
        return whatsappSender.sendMessage(requestDto);
    }

    public WhatsappResponseDto sendMessageToCouple(WhatsappRequestDto requestDto){
        return whatsappSender.sendMessage(requestDto);
    }

    public String generateQRCode(WhatsappRequestDto requestDto){

        try {
            return Base64.getEncoder().encodeToString(QRCodeUtils
                    .gerarQRCode("%s#%s".formatted(requestDto.getPinCode(), requestDto.getContactPhoneNumber()), 250, 250));
        } catch (Exception e){
            throw new UnableToGenerateQRCode();
        }

    }}
