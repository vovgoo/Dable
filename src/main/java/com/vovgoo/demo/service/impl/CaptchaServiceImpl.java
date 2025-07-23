package com.vovgoo.demo.service.impl;

import com.vovgoo.demo.config.properties.CaptchaProperties;
import com.vovgoo.demo.dtos.captcha.CaptchaResponse;
import com.vovgoo.demo.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    private final CaptchaProperties captchaProperties;
    private final RestTemplate restTemplate;

    @Override
    public Boolean verifyCaptcha(String token) {
        HttpEntity<String> request = buildRequest(token);
        ResponseEntity<CaptchaResponse> response = sendRequest(request);
        return parseResponse(response);
    }

    private HttpEntity<String> buildRequest(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = buildRequestBody(token);
        return new HttpEntity<>(body, headers);
    }

    private String buildRequestBody(String token) {
        return "secret=" + captchaProperties.getSecret() +
                "&response=" + token;
    }

    private ResponseEntity<CaptchaResponse> sendRequest(HttpEntity<String> request) {
        return restTemplate.postForEntity(captchaProperties.getVerifyUrl(), request, CaptchaResponse.class);
    }

    private boolean parseResponse(ResponseEntity<CaptchaResponse> response) {
        return response.getBody().isSuccess();
    }
}
