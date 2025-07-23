package com.vovgoo.demo.controllers;

import com.vovgoo.demo.dtos.auth.*;
import com.vovgoo.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signIn")
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/confirmSignUpCheck")
    public ResponseEntity<Boolean> confirmSignUpCheck(@Valid @RequestBody ConfirmSignUpCheckRequest confirmSignUpCheckRequest) {

        Boolean confirmSignUpCheckResult = authService.confirmSignUpCheck(confirmSignUpCheckRequest);
        return ResponseEntity.ok(confirmSignUpCheckResult);
    }

    @PostMapping("/confirmSignUp")
    public ResponseEntity<JwtResponse> confirmSignUp(@Valid @RequestBody ConfirmSignUpRequest confirmSignUpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.confirmSignUp(confirmSignUpRequest));
    }
}
