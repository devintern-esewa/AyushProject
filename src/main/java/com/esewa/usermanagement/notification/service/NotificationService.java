package com.esewa.usermanagement.notification.service;

import com.esewa.usermanagement.entity.User;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

public abstract class NotificationService {

    @Async
    public abstract void sendNotification(User userDetail);

    public String compileMessage(String templateMessage, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            templateMessage = templateMessage.replaceAll("\\{" + entry.getKey() + "}", entry.getValue());
        }
        return templateMessage;
    }
}
