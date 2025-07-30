package com.vovgoo.demo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "stripe.api")
public class StripeProperties {
    private String secretKey;
    private String publishedKey;
}
