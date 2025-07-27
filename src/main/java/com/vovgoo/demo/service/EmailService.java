package com.vovgoo.demo.service;

public interface EmailService {
    void sendEmail(String to, String subject, String htmlBody);
}
