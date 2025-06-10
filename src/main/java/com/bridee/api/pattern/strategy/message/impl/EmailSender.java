package com.bridee.api.pattern.strategy.message.impl;

import com.bridee.api.dto.request.EmailDto;
import com.bridee.api.exception.SendEmailException;
import com.bridee.api.pattern.strategy.message.MessageStrategy;
import com.bridee.api.utils.EmailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Properties;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class EmailSender implements MessageStrategy<String, EmailDto> {

    private static final Integer GMAIL_SMTP_PORT = 587;
    private final EmailProperties emailProperties;

    @Override
    public String sendMessage(EmailDto email){

        MimeMessage mimeMessage = javaMailSender().createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try{
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email.getTo());
            mimeMessageHelper.setFrom(emailProperties.getHost());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getMessage(), email.isHTML());
            javaMailSender().send(mimeMessage);
        }catch (MessagingException e){
            log.error("Houve um erro ao realizar o envio do email: {}", e.getMessage());
            throw new SendEmailException();
        }
        return "Email enviado com sucesso";
    }

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(emailProperties.getHost());
        javaMailSender.setPort(GMAIL_SMTP_PORT);
        javaMailSender.setDefaultEncoding("UTF-8");

        javaMailSender.setUsername(emailProperties.getUser());
        javaMailSender.setPassword(emailProperties.getPassword());
        Properties javaMailProperties = javaMailSender.getJavaMailProperties();
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.debug", "debug");
        return javaMailSender;
    }
}
