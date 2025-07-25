package com.vovgoo.demo.dtos.auth;

import com.vovgoo.demo.dtos.CaptchaAware;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest implements CaptchaAware {

    @Schema(
            description = "Unique username to register with.",
            example = "john_doe",
            minLength = 5,
            maxLength = 50,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    @Size(min = 5, max = 50)
    private String username;

    @Schema(
            description = "Valid email address for registration and confirmation.",
            example = "john@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Email
    @NotBlank
    private String email;

    @Schema(
            description = "Password with minimum 8 characters.",
            example = "Str0ngP@ssword!",
            minLength = 8,
            maxLength = 255,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Size(min = 8, max = 255)
    private String password;

    @Schema(
            description = "CAPTCHA validation code from the frontend",
            example = "xk7Fhd9",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String captcha;
}
