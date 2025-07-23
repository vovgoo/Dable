package com.vovgoo.demo.service;

import com.vovgoo.demo.dtos.auth.*;

public interface AuthService {
    JwtResponse signIn(SignInRequest signInRequest);
    void signUp(SignUpRequest signUpRequest);
    Boolean confirmSignUpCheck(ConfirmSignUpCheckRequest confirmSignUpCheckRequest);
    JwtResponse confirmSignUp(ConfirmSignUpRequest confirmSignUpRequest);
}
