package com.vovgoo.demo.config.yandex;

import com.vovgoo.demo.config.properties.YandexS3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@RequiredArgsConstructor
public class YandexS3Config {

    private final YandexS3Properties yandexS3Properties;

    @Bean
    public S3Client createS3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(yandexS3Properties.getEndpoint()))
                .region(Region.of(yandexS3Properties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                yandexS3Properties.getAccessKey(),
                                yandexS3Properties.getSecretKey()
                        )
                ))
                .httpClient(ApacheHttpClient.create())
                .overrideConfiguration(ClientOverrideConfiguration.builder().build())
                .build();
    }
}
