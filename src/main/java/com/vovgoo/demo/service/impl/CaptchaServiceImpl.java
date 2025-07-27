package com.vovgoo.demo.service.impl;

import com.vovgoo.demo.config.properties.CaptchaProperties;
import com.vovgoo.demo.dtos.captcha.CaptchaResponse;
import com.vovgoo.demo.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    private final CaptchaProperties captchaProperties;
    private final WebClient webClient;

    @Override
    public Boolean verifyCaptcha(String token) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("secret", captchaProperties.getSecret());
        body.add("response", token);

        CaptchaResponse captchaResponse = webClient.post()
                .uri(captchaProperties.getVerifyUrl())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(CaptchaResponse.class)
                .block();

        return captchaResponse != null && captchaResponse.isSuccess();
    }
}
