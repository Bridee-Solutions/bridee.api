package com.bridee.api.controller;

import com.bridee.api.dto.request.EmailDto;
import com.bridee.api.dto.request.WhatsappRequestDto;
import com.bridee.api.dto.response.WhatsappResponseDto;
import com.bridee.api.pattern.strategy.impl.WhatsappSender;
import com.bridee.api.service.EmailService;
import com.bridee.api.utils.QRCodeUtils;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final EmailService emailService;
    private final WhatsappSender whatsappSender;

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto emailDto){
        emailService.sendEmail(emailDto.getTo(), emailDto.getSubject(), emailDto.getMessage());
        return ResponseEntity.ok("Email enviado com sucesso");
    }

    @PostMapping("/whatsapp")
    public ResponseEntity<String> sendWhatsappMessage(@RequestBody WhatsappRequestDto whatsappRequestDto) throws IOException, WriterException {
        whatsappSender.sendMessage(whatsappRequestDto.getContactPhoneNumber(), "",Base64.getEncoder().encodeToString(QRCodeUtils.gerarQRCode("localhost", "", "UTF-8", 200, 200)));
        return ResponseEntity.ok("Mensagem enviada com sucesso");
    }

}
