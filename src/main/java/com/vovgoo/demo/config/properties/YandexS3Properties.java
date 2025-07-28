package com.vovgoo.demo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "yandex.s3")
public class YandexS3Properties {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String region;
    private String endpoint;
}
