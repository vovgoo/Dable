package com.vovgoo.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mail")
@Data
public class MailProperties {
    private String host;
    private int port;
    private String username;
    private String password;
}
