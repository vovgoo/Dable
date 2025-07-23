package com.vovgoo.demo.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConfirmSignUpRequest {

    @NotBlank
    private String token;
}
