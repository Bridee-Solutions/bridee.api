package com.bridee.api.controller;

import com.bridee.api.client.dto.response.WhatsappResponseDto;
import com.bridee.api.dto.request.EmailDto;
import com.bridee.api.client.dto.request.WhatsappRequestDto;
import com.bridee.api.pattern.strategy.impl.WhatsappSender;
import com.bridee.api.service.EmailService;
import com.bridee.api.service.WhatsappService;
import com.bridee.api.utils.QRCodeUtils;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final EmailService emailService;
    private final WhatsappService whatsappService;
    private final WhatsappSender whatsappSender;

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto emailDto){
        return ResponseEntity.ok(emailService.sendEmail(emailDto));
    }

    @PostMapping("/whatsapp")
    public ResponseEntity<WhatsappResponseDto> sendWhatsappMessage(@RequestBody WhatsappRequestDto whatsappRequestDto) throws IOException, WriterException {
        return ResponseEntity.ok(whatsappService.sendMessage(whatsappRequestDto));
    }

}
