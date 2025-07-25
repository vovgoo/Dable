package com.vovgoo.demo.controllers;

import com.vovgoo.demo.dtos.user.UserResponse;
import com.vovgoo.demo.entity.User;
import com.vovgoo.demo.mappers.UserResponseMapper;
import com.vovgoo.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vovgoo.demo.config.swagger.OpenApiConstants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("/api/v1/self")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserResponseMapper userResponseMapper;

    @Operation(
            summary = "Get current authenticated user",
            description = """
            Returns information about the currently authenticated user.
            Requires the user to be authenticated.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User found and returned successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized — user is not authenticated.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found — authenticated user does not exist in the database.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error — unexpected error occurred.",
                    content = @Content()
            )
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(userResponseMapper.toDto(currentUser));
    }
}
