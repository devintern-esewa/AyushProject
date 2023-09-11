package com.esewa.usermanagement.notification.entity;

import com.esewa.usermanagement.enums.EmailStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emailId;
    @Enumerated(EnumType.STRING)
    private EmailStatus status;
    private String emailMessage;
    private String exceptionMessage;

    public EmailLog(String emailId, EmailStatus status, String emailMessage, String exceptionMessage) {
        this.emailId = emailId;
        this.status = status;
        this.emailMessage = emailMessage;
        this.exceptionMessage = exceptionMessage;
    }

}
