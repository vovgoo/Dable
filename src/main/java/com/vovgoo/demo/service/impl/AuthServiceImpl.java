package com.vovgoo.demo.service.impl;

import com.vovgoo.demo.dtos.auth.ConfirmSignUpRequest;
import com.vovgoo.demo.dtos.auth.JwtResponse;
import com.vovgoo.demo.dtos.auth.SignInRequest;
import com.vovgoo.demo.dtos.auth.SignUpRequest;
import com.vovgoo.demo.repository.UserRepository;
import com.vovgoo.demo.service.AuthService;
import com.vovgoo.demo.service.CaptchaService;
import com.vovgoo.demo.service.EmailService;
import com.vovgoo.demo.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CaptchaService captchaService;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public JwtResponse signIn(SignInRequest signInRequest) {
        return null;
    }

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        captchaService.verifyCaptcha(signUpRequest.getCaptcha());
    }

    @Override
    public JwtResponse confirmSignUp(ConfirmSignUpRequest confirmSignUpRequest) {
        return null;
    }
}
