package com.esewa.usermanagement.cache.service;

import com.esewa.usermanagement.notification.entity.EmailTemplate;
import com.esewa.usermanagement.notification.repository.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailTemplateCacheService {

    private final EmailTemplateRepository emailTemplateRepository;
    @Cacheable(cacheNames = "registrationEmailTemplate", key = "#templateCode", unless = "#result.isEmpty()")
    public Optional<EmailTemplate> getTemplateMessage(long templateCode) {
        return emailTemplateRepository.findById(templateCode);
    }
}
