package com.esewa.usermanagement.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JWTConfigurationProperties {

    private Map<String, String> secret;
    private String expiry;

}
