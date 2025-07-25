package com.vovgoo.demo.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConfirmSignUpCheckRequest {

    @Schema(
            description = "Sign-up confirmation token sent to the user's email.",
            example = "d3f3c4f7-8abc-4321-a9e8-4e2f1e6a4b09",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String token;
}
