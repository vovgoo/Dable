package com.vovgoo.demo.dtos.auth;

import com.vovgoo.demo.dtos.CaptchaAware;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest implements CaptchaAware {

    @Schema(
            description = "User's login or email",
            example = "john.doe"
    )
    @NotBlank
    private String username;

    @Schema(
            description = "User's password",
            example = "P@ssw0rd123"
    )
    @NotBlank
    private String password;

    @Schema(
            description = "CAPTCHA validation code from the frontend",
            example = "xk7Fhd9"
    )
    @NotBlank
    private String captcha;
}
