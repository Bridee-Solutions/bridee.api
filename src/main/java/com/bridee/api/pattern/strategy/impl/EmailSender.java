package com.bridee.api.pattern.strategy.impl;

import com.bridee.api.dto.request.EmailDto;
import com.bridee.api.exception.SendEmailException;
import com.bridee.api.pattern.strategy.MessageStrategy;
import com.bridee.api.utils.EmailProperties;
import com.bridee.api.utils.QRCodeUtils;
import com.google.zxing.WriterException;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.flywaydb.core.internal.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

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
        }catch (MessagingException e){
            throw new SendEmailException();
        }
        javaMailSender().send(mimeMessage);
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
