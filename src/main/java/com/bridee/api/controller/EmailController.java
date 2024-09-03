package com.bridee.api.controller;

import com.bridee.api.dto.request.EmailDto;
import com.bridee.api.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto emailDto){
        emailService.sendEmail(emailDto.getTo(), emailDto.getSubject(), emailDto.getMessage());
        return ResponseEntity.ok("Email enviado com sucesso");
    }

}
