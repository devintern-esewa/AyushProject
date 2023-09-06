package com.esewa.usermanagement.service;

import com.esewa.usermanagement.configuration.EmailConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailConfigProperties emailConfigProperties;
    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailServiceImpl(JavaMailSender javaMailSender, EmailConfigProperties emailConfigProperties) {
        this.javaMailSender = javaMailSender;
        this.emailConfigProperties = emailConfigProperties;
    }

    @Async
    @Override
    public void sendEmail(String recipientEmail) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(recipientEmail);
            mailMessage.setText(emailConfigProperties.getMsgBody());
            mailMessage.setSubject(emailConfigProperties.getSubject());
            javaMailSender.send(mailMessage);
        }

        catch (Exception e) {
            log.error("Error while Sending Mail");
        }
    }
}
