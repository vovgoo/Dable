package com.vovgoo.demo.service.impl;

import com.vovgoo.demo.aop.captcha.VerifyCaptcha;
import com.vovgoo.demo.config.properties.RegistrationProperties;
import com.vovgoo.demo.dtos.auth.*;
import com.vovgoo.demo.entity.User;
import com.vovgoo.demo.entity.enums.Role;
import com.vovgoo.demo.exceptions.RegistrationInProgressException;
import com.vovgoo.demo.exceptions.TokenNotFoundException;
import com.vovgoo.demo.exceptions.UserAlreadyExistsException;
import com.vovgoo.demo.repository.UserRepository;
import com.vovgoo.demo.service.AuthService;
import com.vovgoo.demo.service.JwtService;
import com.vovgoo.demo.service.RedisService;
import com.vovgoo.demo.service.email.RegistrationEmailService;
import com.vovgoo.demo.utils.json.JsonUtils;
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

    private final RegistrationProperties registrationProperties;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final JsonUtils jsonUtils;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final RegistrationEmailService registrationEmailService;

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
//    @VerifyCaptcha
    public void signUp(SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UserAlreadyExistsException("User with this username already exists");
        }

        String signUpEmailKey = RedisKeys.signUpEmailKey(signUpRequest.getEmail());
        String token = UUID.randomUUID().toString();
        String signUpTokenKey = RedisKeys.signUpTokenKey(token);
        String jsonData = jsonUtils.toJson(signUpRequest);

        boolean success = redisService.setIfAbsent(signUpEmailKey, signUpTokenKey, registrationProperties.getRedisConfirmationTokenExpirationMinutes(), TimeUnit.MINUTES);

        if (!success) {
            throw new RegistrationInProgressException("Registration already in process for this email. Please wait.");
        }

        redisService.setValue(signUpTokenKey, jsonData, registrationProperties.getRedisConfirmationTokenExpirationMinutes(), TimeUnit.MINUTES);

        registrationEmailService.sendRegistrationEmail(signUpRequest.getEmail(), token);
    }

    @Override
    public Boolean confirmSignUpCheck(ConfirmSignUpCheckRequest confirmSignUpCheckRequest) {
        String signUpTokenKey = RedisKeys.signUpTokenKey(confirmSignUpCheckRequest.getToken());
        return redisService.getValue(signUpTokenKey) != null;
    }

    @Override
    @Transactional
    public JwtResponse confirmSignUp(ConfirmSignUpRequest confirmSignUpRequest) {

        String signUpTokenKey = RedisKeys.signUpTokenKey(confirmSignUpRequest.getToken());
        String jsonData = redisService.getValue(signUpTokenKey);

        if(jsonData == null) {
            throw new TokenNotFoundException("Sign-up confirmation token not found or expired");
        }

        SignUpRequest signUpRequest = jsonUtils.fromJson(jsonData, SignUpRequest.class);

        String signUpEmailKey = RedisKeys.signUpEmailKey(signUpRequest.getEmail());

        redisService.deleteValue(signUpEmailKey);
        redisService.deleteValue(signUpTokenKey);

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());

        return JwtResponse.builder()
                .token(token)
                .build();
    }
}
