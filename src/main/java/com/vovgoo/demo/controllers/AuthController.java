package com.vovgoo.demo.controllers;

import com.vovgoo.demo.dtos.auth.*;
import com.vovgoo.demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
                    description = "Bad request — invalid input or CAPTCHA failed.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized — invalid username or password.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error — unexpected issue during authentication.",
                    content = @Content()
            )
    })
    @PostMapping("/signIn")
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @Operation(
            summary = "Sign up",
            description = """
        Initiates user registration. If the request is valid, a confirmation email is sent to the provided address.
        User is not immediately created — must confirm registration via email.
    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Registration initiated. Confirmation email sent.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request — invalid input or CAPTCHA failed.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict — email or username already exists.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "429",
                    description = "Too many requests — registration already in process for this email.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error — failed to process registration.",
                    content = @Content()
            )
    })
    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return ResponseEntity.accepted().build();
    }

    @Operation(
            summary = "Check Sign-Up Confirmation Token",
            description = """
        Verifies whether the given sign-up confirmation token exists and is valid.
        This endpoint is typically used to check the validity of the confirmation token
        sent to the user's email during the registration process.
    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Token check completed. Response indicates whether the token is valid.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class),
                            examples = {
                                    @ExampleObject(name = "ValidToken", value = "true"),
                                    @ExampleObject(name = "InvalidToken", value = "false")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request — invalid or missing token in the request payload.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error — Redis or system error occurred during token check.",
                    content = @Content()
            )
    })
    @PostMapping("/confirmSignUpCheck")
    public ResponseEntity<Boolean> confirmSignUpCheck(@Valid @RequestBody ConfirmSignUpCheckRequest confirmSignUpCheckRequest) {
        Boolean confirmSignUpCheckResult = authService.confirmSignUpCheck(confirmSignUpCheckRequest);
        return ResponseEntity.ok(confirmSignUpCheckResult);
    }

    @Operation(
            summary = "Confirm user registration",
            description = """
        Confirms a user's registration using a confirmation token sent via email.
        If the token is valid and not expired, a new user account is created and a JWT token is returned.
        The returned token should be used in the `Authorization` header as `Bearer <token>` to access protected endpoints.
    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User successfully registered and authenticated. JWT token returned.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request — input validation failed (e.g. missing or invalid token).",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Confirmation token not found or expired.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error — failed to create user or generate token.",
                    content = @Content()
            )
    })
    @PostMapping("/confirmSignUp")
    public ResponseEntity<JwtResponse> confirmSignUp(@Valid @RequestBody ConfirmSignUpRequest confirmSignUpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.confirmSignUp(confirmSignUpRequest));
    }
}
