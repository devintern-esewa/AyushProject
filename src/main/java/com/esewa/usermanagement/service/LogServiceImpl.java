package com.esewa.usermanagement.service;

import com.esewa.usermanagement.entity.RegistrationLog;
import com.esewa.usermanagement.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{

    private final LogRepository logRepository;
    @Override
    public void saveLog(RegistrationLog log) {
        logRepository.save(log);
    }
}
