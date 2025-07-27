package com.vovgoo.demo.service.impl;

import com.vovgoo.demo.config.properties.MailProperties;
import com.vovgoo.demo.exceptions.EmailSendingException;
import com.vovgoo.demo.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Override
    public void sendEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = createMimeMessage(to, subject, htmlBody);
            mailSender.send(message);
        } catch (MessagingException | MailException ex) {
            throw new EmailSendingException("Failed to send email to" + to + ": " + ex.getMessage());
        }
    }

    private MimeMessage createMimeMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(mailProperties.getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        return mimeMessage;
    }
}
