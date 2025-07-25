package com.vovgoo.demo.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConfirmSignUpRequest {

    @Schema(
            description = "The sign-up confirmation token sent to the user's email.",
            example = "3a1f6b45-cc8b-4d20-bd8d-bd9a7f10a4d3",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String token;
}
