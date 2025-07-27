package com.vovgoo.demo.service.email.impl;

import com.vovgoo.demo.config.properties.RegistrationProperties;
import com.vovgoo.demo.service.EmailService;
import com.vovgoo.demo.service.RedisService;
import com.vovgoo.demo.service.email.RegistrationEmailService;
import com.vovgoo.demo.utils.redis.RedisKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class RegistrationEmailServiceImpl implements RegistrationEmailService {

    private final EmailService emailService;
    private final RedisService redisService;
    private final SpringTemplateEngine templateEngine;
    private final RegistrationProperties registrationProperties;

    @Async
    @Retryable(
            retryFor = RuntimeException.class,
            backoff = @Backoff(delay = 1000)
    )
    @Override
    public void sendRegistrationEmail(String email, String token) {
        Context context = new Context();

        String confirmationLink = registrationProperties.getConfirmationUrlTemplate().formatted(token);
        context.setVariable("confirmationLink", confirmationLink);

        String htmlContent = templateEngine.process("registrationEmail", context);
        String subject = "Registration Confirmation";

        emailService.sendEmail(email, subject, htmlContent);
    }

    @Recover
    @Override
    public void recover(RuntimeException e, String email, String token) {
        redisService.deleteValue(RedisKeys.signUpEmailKey(email));
        redisService.deleteValue(RedisKeys.signUpTokenKey(token));
    }
}
