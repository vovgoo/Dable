package com.vovgoo.demo.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConfirmSignUpCheckRequest {

    @NotBlank
    private String token;
}
