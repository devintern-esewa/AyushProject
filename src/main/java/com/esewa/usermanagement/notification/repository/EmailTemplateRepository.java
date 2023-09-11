package com.esewa.usermanagement.notification.repository;

import com.esewa.usermanagement.notification.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate,Long> {
}
