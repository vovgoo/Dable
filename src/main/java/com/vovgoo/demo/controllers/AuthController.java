package com.vovgoo.demo.controllers;

import com.vovgoo.demo.dtos.auth.*;
import com.vovgoo.demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "User registration and login")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Sign in",
            description = """
        Authenticates a user using their username and password.
        On successful authentication, returns a JWT token that must be included in the 
        `Authorization` header as `Bearer <token>` for accessing protected endpoints.
    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Authentication successful. JWT token returned.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request — invalid CAPTCHA or malformed request payload."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized — invalid username or password."
            )
    })
    @PostMapping("/signIn")
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/confirmSignUpCheck")
    public ResponseEntity<Boolean> confirmSignUpCheck(@Valid @RequestBody ConfirmSignUpCheckRequest confirmSignUpCheckRequest) {
        Boolean confirmSignUpCheckResult = authService.confirmSignUpCheck(confirmSignUpCheckRequest);
        return ResponseEntity.ok(confirmSignUpCheckResult);
    }

    @PostMapping("/confirmSignUp")
    public ResponseEntity<JwtResponse> confirmSignUp(@Valid @RequestBody ConfirmSignUpRequest confirmSignUpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.confirmSignUp(confirmSignUpRequest));
    }
}
