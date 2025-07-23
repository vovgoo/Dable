package com.vovgoo.demo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vovgoo.demo.aop.captcha.VerifyCaptcha;
import com.vovgoo.demo.dtos.auth.*;
import com.vovgoo.demo.entity.User;
import com.vovgoo.demo.exceptions.TokenNotFoundException;
import com.vovgoo.demo.repository.UserRepository;
import com.vovgoo.demo.service.AuthService;
import com.vovgoo.demo.service.EmailService;
import com.vovgoo.demo.service.JwtService;
import com.vovgoo.demo.service.RedisService;
import com.vovgoo.demo.utils.redis.RedisKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;
    private final RedisService redisService;

    @Override
    @VerifyCaptcha
    public JwtResponse signIn(SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails.getUsername());

        return JwtResponse.builder()
                .token(token)
                .build();
    }

    @Override
    @VerifyCaptcha
    public void signUp(SignUpRequest signUpRequest) {

        String signUpEmailKey = RedisKeys.signUpEmailKey(signUpRequest.getEmail());

        String oldSignUpTokenKey = redisService.getValue(signUpEmailKey);

        if (oldSignUpTokenKey != null) {
            redisService.deleteValue(signUpEmailKey);
            redisService.deleteValue(oldSignUpTokenKey);
        }

        try {
            String jsonData = objectMapper.writeValueAsString(signUpRequest);

            String token = UUID.randomUUID().toString();

            String signUpTokenKey = RedisKeys.signUpTokenKey(token);

            redisService.setValue(signUpTokenKey, jsonData, 30, TimeUnit.MINUTES);
            redisService.setValue(signUpEmailKey, token, 30, TimeUnit.MINUTES);

            // Вынести этот блок в другое место наверное надо

            String confirmationLink = "http://frontendurl:8080/api/auth/confirm?token=" + token;

            emailService.sendEmail(signUpRequest.getEmail(), "Регаем тебя тип", confirmationLink);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error of serializable object");
        }
    }

    @Override
    public Boolean confirmSignUpCheck(ConfirmSignUpCheckRequest confirmSignUpCheckRequest) {

        String signUpTokenKey = RedisKeys.signUpTokenKey(confirmSignUpCheckRequest.getToken());
        String email = redisService.getValue(signUpTokenKey);

        return email != null;
    }

    @Override
    @Transactional
    public JwtResponse confirmSignUp(ConfirmSignUpRequest confirmSignUpRequest) {

        String signUpTokenKey = RedisKeys.signUpTokenKey(confirmSignUpRequest.getToken());
        String jsonData = redisService.getValue(signUpTokenKey);

        if(jsonData == null) {
            throw new TokenNotFoundException("Sign-up confirmation token not found or expired");
        }

        try {
            SignUpRequest signUpRequest = objectMapper.readValue(jsonData, SignUpRequest.class);

            User user = User.builder()
                    .username(signUpRequest.getUsername())
                    .email(signUpRequest.getEmail())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .build();

            userRepository.save(user);

            String token = jwtService.generateToken(user.getUsername());

            return JwtResponse.builder()
                    .token(token)
                    .build();

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error of serializable object");
        }
    }
}
