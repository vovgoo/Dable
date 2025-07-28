package com.vovgoo.demo.controllers;

import com.vovgoo.demo.dtos.image.UploadImageRequest;
import com.vovgoo.demo.dtos.user.UserResponse;
import com.vovgoo.demo.entity.User;
import com.vovgoo.demo.mappers.UserResponseMapper;
import com.vovgoo.demo.service.ProfileService;
import com.vovgoo.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vovgoo.demo.config.swagger.OpenApiConstants.SECURITY_SCHEME_NAME;

@Tag(
        name = "User Profile",
        description = "Endpoints for managing the authenticated user's profile, including retrieving profile information and uploading a profile image."
)
@RestController
@RequestMapping("/api/v1/self")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProfileService profileService;
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

    @Operation(
            summary = "Upload profile image for current user",
            description = """
        Uploads a new profile image for the currently authenticated user.
        If the user already has a profile image, it will be replaced.
        The image file must be provided as multipart/form-data.
    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Profile image uploaded successfully, returns updated user info.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request — invalid or missing image file.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized — user is not authenticated.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found — current authenticated user does not exist.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error — failed to upload image.",
                    content = @Content()
            )
    })
    @PostMapping(value = "/uploadProfileImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> uploadProfileImage(UploadImageRequest uploadImageRequest) {
        User user = profileService.uploadProfileImage(uploadImageRequest);
        return ResponseEntity.ok(userResponseMapper.toDto(user));
    }
}
