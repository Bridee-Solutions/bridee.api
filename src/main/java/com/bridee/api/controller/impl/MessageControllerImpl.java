package com.bridee.api.controller.impl;

import com.bridee.api.client.dto.request.WhatsappRequestDto;
import com.bridee.api.client.dto.response.WhatsappResponseDto;
import com.bridee.api.controller.MessageController;
import com.bridee.api.dto.request.ConviteMessageDto;
import com.bridee.api.service.ConviteService;
import com.bridee.api.service.WhatsappService;
import com.google.zxing.WriterException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageControllerImpl implements MessageController {

    private final WhatsappService whatsappService;
    private final ConviteService conviteService;

    @PostMapping("/whatsapp")
    public ResponseEntity<WhatsappResponseDto> sendWhatsappMessage(@RequestBody WhatsappRequestDto whatsappRequestDto){
        log.info("MESSAGE: enviando mensagem para o número {}", whatsappRequestDto.getContactPhoneNumber());
        return ResponseEntity.ok(whatsappService.sendMessage(whatsappRequestDto));
    }

    @PostMapping("/whatsapp/convites")
    public ResponseEntity<Void> sendConvitesWhatsappMessages(@RequestBody @Valid ConviteMessageDto conviteMessageDto){
        log.info("MESSAGE: enviando convites para o casamento de id {}", conviteMessageDto.getCasamentoId());
        conviteService.sendConvidadosConvites(conviteMessageDto);
        return ResponseEntity.ok().build();
    }

}
