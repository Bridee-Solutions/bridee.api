package com.bridee.api.controller.impl;

import com.bridee.api.client.dto.response.WhatsappResponseDto;
import com.bridee.api.controller.MessageController;
import com.bridee.api.dto.request.ConviteMessageDto;
import com.bridee.api.dto.request.EmailDto;
import com.bridee.api.client.dto.request.WhatsappRequestDto;
import com.bridee.api.mapper.request.ConviteMessageMapper;
import com.bridee.api.service.ConviteService;
import com.bridee.api.service.EmailService;
import com.bridee.api.service.WhatsappService;
import com.google.zxing.WriterException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageControllerImpl implements MessageController {

    private final EmailService emailService;
    private final WhatsappService whatsappService;
    private final ConviteService conviteService;
    private final ConviteMessageMapper conviteMapper;

    @PostMapping("/whatsapp")
    public ResponseEntity<WhatsappResponseDto> sendWhatsappMessage(@RequestBody WhatsappRequestDto whatsappRequestDto) throws IOException, WriterException {
        return ResponseEntity.ok(whatsappService.sendMessage(whatsappRequestDto));
    }

    @PostMapping("/whatsapp/convites")
    public ResponseEntity<Void> sendConvitesWhatsappMessages(@RequestBody @Valid ConviteMessageDto conviteMessageDto){
        conviteService.sendConvidadosConvites(conviteMessageDto);
        return ResponseEntity.ok().build();
    }

}
