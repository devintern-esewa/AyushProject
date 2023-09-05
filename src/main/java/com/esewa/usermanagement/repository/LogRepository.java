package com.esewa.usermanagement.repository;

import com.esewa.usermanagement.entity.RegistrationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<RegistrationLog, Long> {
}
