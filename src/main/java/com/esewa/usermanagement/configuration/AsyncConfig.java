package com.esewa.usermanagement.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@RequiredArgsConstructor
public class AsyncConfig {

    private final ThreadConfigurationProperties threadConfigurationProperties;

    @Bean(name = "saveMultipleUsersThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor multipleUsersThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadConfigurationProperties.getCorePoolSize());
        executor.setMaxPoolSize(threadConfigurationProperties.getMaxPoolSize());
        executor.setQueueCapacity(threadConfigurationProperties.getQueueCapacity());
        executor.setThreadNamePrefix(threadConfigurationProperties.getThreadNamePrefix());
        executor.initialize();
        return executor;
    }
}