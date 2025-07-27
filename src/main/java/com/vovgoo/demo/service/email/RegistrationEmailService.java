package com.vovgoo.demo.service.email;

public interface RegistrationEmailService {
    void sendRegistrationEmail(String email, String token);
    void recover(RuntimeException e, String email, String token);
}
