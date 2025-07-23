package com.vovgoo.demo.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "captcha")
@Data
public class CaptchaProperties {
    private String secret;
    private String verifyUrl;
}
