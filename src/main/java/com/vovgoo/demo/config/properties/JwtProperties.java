package com.vovgoo.demo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.jwt")
@Data
public class JwtProperties {
    private String secret;
    private long expiration;
}
