package com.esewa.usermanagement.service;

import com.esewa.usermanagement.enums.EmailMessage;
import com.esewa.usermanagement.entity.EmailLog;
import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.enums.EmailStatus;
import com.esewa.usermanagement.repository.EmailLogRepository;
import com.esewa.usermanagement.repository.EmailTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailLogRepository emailLogRepository;
    private final EmailTemplateRepository emailTemplateRepository;
    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailServiceImpl(JavaMailSender javaMailSender, EmailLogRepository emailLogRepository, EmailTemplateRepository emailTemplateRepository) {
        this.javaMailSender = javaMailSender;
        this.emailLogRepository = emailLogRepository;
        this.emailTemplateRepository =  emailTemplateRepository;
    }

    @Async
    @Override
    public void sendEmail(User userDetail) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(userDetail.getEmail());
        try {
            emailTemplateRepository.findById(EmailMessage.REGISTRATION.getTemplateCode())
                    .ifPresentOrElse(emailTemplate -> {
                        String message = compileMessage(emailTemplate.getMessage(), Map.of("user", userDetail.getName(), "username", userDetail.getName()));
                        log.info(message);
                        mailMessage.setSubject(emailTemplate.getSubject());
                        mailMessage.setText(message.replace("{username}", userDetail.getName()));
                    }, () -> {
                        log.error("Error while sending email. Unable to find template for {}", EmailMessage.REGISTRATION.getTemplateMessage());
                        throw new RuntimeException(String.format("Error while sending email. Unable to find template for %s", EmailMessage.REGISTRATION.getTemplateMessage()));
                    });

            //javaMailSender.send(mailMessage);
            emailLogRepository.save(new EmailLog(userDetail.getEmail(), EmailStatus.SENT, mailMessage.getText()));
        } catch (Exception exception) {
            log.error(exception.getMessage());
            emailLogRepository.save(new EmailLog(userDetail.getEmail(), EmailStatus.FAILED, mailMessage.getText()));
        }
    }


    private String compileMessage(String templateMessage, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            templateMessage = templateMessage.replaceAll("\\{" + entry.getKey() + "}", entry.getValue());
        }
        return templateMessage;
    }
}
