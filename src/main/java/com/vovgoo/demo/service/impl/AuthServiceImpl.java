package com.vovgoo.demo.service.impl;

import com.vovgoo.demo.dtos.auth.ConfirmSignUpRequest;
import com.vovgoo.demo.dtos.auth.JwtResponse;
import com.vovgoo.demo.dtos.auth.SignInRequest;
import com.vovgoo.demo.dtos.auth.SignUpRequest;
import com.vovgoo.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Override
    public JwtResponse signIn(SignInRequest signInRequest) {
        return null;
    }

    @Override
    public void signUp(SignUpRequest signUpRequest) {

    }

    @Override
    public JwtResponse confirmSignUp(ConfirmSignUpRequest confirmSignUpRequest) {
        return null;
    }
}
