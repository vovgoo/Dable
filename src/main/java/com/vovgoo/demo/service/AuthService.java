package com.vovgoo.demo.service;

import com.vovgoo.demo.dtos.auth.ConfirmSignUpRequest;
import com.vovgoo.demo.dtos.auth.JwtResponse;
import com.vovgoo.demo.dtos.auth.SignInRequest;
import com.vovgoo.demo.dtos.auth.SignUpRequest;

public interface AuthService {
    JwtResponse signIn(SignInRequest signInRequest);
    void signUp(SignUpRequest signUpRequest);
    JwtResponse confirmSignUp(ConfirmSignUpRequest confirmSignUpRequest);
}
