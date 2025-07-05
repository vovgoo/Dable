package com.vovgoo.demo.dtos.auth;

import lombok.Data;

@Data
public class ConfirmSignUpRequest {
    private String username;
    private String confirmationCode;
}
