package com.esewa.usermanagement.service.impl;

import com.esewa.usermanagement.entity.RegistrationLog;
import com.esewa.usermanagement.repository.RegistrationLogRepository;
import com.esewa.usermanagement.service.RegistrationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationLogServiceImpl implements RegistrationLogService {

    private final RegistrationLogRepository logRepository;

    @Override
    public void saveLog(RegistrationLog log) {
        logRepository.save(log);
    }

}
