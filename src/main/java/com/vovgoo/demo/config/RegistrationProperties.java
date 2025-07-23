package com.vovgoo.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.registration")
@Data
public class RegistrationProperties {
    private String confirmationUrlTemplate;
}
