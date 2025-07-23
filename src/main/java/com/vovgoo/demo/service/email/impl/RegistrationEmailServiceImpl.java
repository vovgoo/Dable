package com.vovgoo.demo.service.email.impl;

import com.vovgoo.demo.config.RegistrationProperties;
import com.vovgoo.demo.service.EmailService;
import com.vovgoo.demo.service.email.RegistrationEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationEmailServiceImpl implements RegistrationEmailService {

    private final EmailService emailService;
    private final RegistrationProperties registrationProperties;

    @Override
    public void sendRegistrationEmail(String email, String token) {
        String confirmationLink = registrationProperties.getConfirmationUrlTemplate().formatted(token);

        String subject = "Registration Confirmation";
        String body = """
            Thank you for registering!
            Please confirm your account by clicking the link below:
            %s
            """.formatted(confirmationLink);

        emailService.sendEmail(email, subject, body);
    }
}
