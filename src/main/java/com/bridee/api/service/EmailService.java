package com.bridee.api.service;

import com.bridee.api.dto.request.EmailDto;
import com.bridee.api.pattern.strategy.impl.EmailSender;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailSender emailSender;

    public String sendEmail(EmailDto emailDto){
            return emailSender.sendMessage(emailDto);
    }
}
