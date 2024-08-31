package com.bridee.api.pattern.strategy.impl;

import com.bridee.api.pattern.strategy.MessageStrategy;
import com.bridee.api.utils.EmailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class EmailSender implements MessageStrategy {

    private static final Integer GMAIL_SMTP_PORT = 587;
    private final EmailProperties emailProperties;

    @Override
    public void sendMessage(String to, String subject, String message) {
        MimeMessage mimeMessage = javaMailSender().createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try{
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(emailProperties.getHost());
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }
        javaMailSender().send(mimeMessage);
    }

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(emailProperties.getHost());
        javaMailSender.setPort(GMAIL_SMTP_PORT);

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
