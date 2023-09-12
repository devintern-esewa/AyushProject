package com.esewa.usermanagement.notification.service;

import com.esewa.usermanagement.cache.service.EmailTemplateCacheService;
import com.esewa.usermanagement.configuration.FlagsConfigurationProperties;
import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.enums.EmailMessage;
import com.esewa.usermanagement.enums.EmailStatus;
import com.esewa.usermanagement.notification.entity.EmailLog;
import com.esewa.usermanagement.notification.repository.EmailLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class EmailService extends NotificationService {

    private final JavaMailSender javaMailSender;
    private final EmailLogRepository emailLogRepository;
    private final FlagsConfigurationProperties flagsConfigurationProperties;
    private final EmailTemplateCacheService emailTemplateCacheService;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${emailLogChar.maxLength}")
    private int emailLogMaxCharacterLength;

    public EmailService(JavaMailSender javaMailSender, EmailTemplateCacheService emailTemplateCacheService, EmailLogRepository emailLogRepository, FlagsConfigurationProperties flagsConfigurationProperties) {
        this.javaMailSender = javaMailSender;
        this.emailTemplateCacheService = emailTemplateCacheService;
        this.emailLogRepository = emailLogRepository;
        this.flagsConfigurationProperties = flagsConfigurationProperties;
    }

    @Async
    @Override
    public void sendNotification(User userDetail) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(userDetail.getEmail());
        try {
             emailTemplateCacheService.getTemplateMessage(EmailMessage.REGISTRATION.getTemplateCode())
                    .ifPresentOrElse(emailTemplate -> {
                        String message = compileMessage(emailTemplate.getMessage(), Map.of("user", userDetail.getName(), "username", userDetail.getName()));
                        log.info(message);
                        mailMessage.setSubject(emailTemplate.getSubject());
                        mailMessage.setText(message.replace("{username}", userDetail.getName()));
                    }, () -> {
                        log.error("Error while sending email. Unable to find template for {}", EmailMessage.REGISTRATION.getTemplateMessage());
                        throw new RuntimeException(String.format("Error while sending email. Unable to find template for %s", EmailMessage.REGISTRATION.getTemplateMessage()));
                    });

            if (flagsConfigurationProperties.isSendEmail())
                javaMailSender.send(mailMessage);

            emailLogRepository.save(new EmailLog(userDetail.getEmail(), EmailStatus.SENT, mailMessage.getText(), null));
        } catch (Exception exception) {
            log.error(exception.getMessage());
            emailLogRepository.save(new EmailLog(userDetail.getEmail(), EmailStatus.FAILED, mailMessage.getText(), exception.getMessage().substring(0, emailLogMaxCharacterLength)));
        }
    }

}
