package com.esewa.usermanagement.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "admin")
public class AdminConfigurationProperties {

    private String name;
    private String password;
    private String email;
    private String role;
    private String phone;

}
