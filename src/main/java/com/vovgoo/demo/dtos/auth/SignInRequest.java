package com.vovgoo.demo.dtos.auth;

import com.vovgoo.demo.dtos.CaptchaAware;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest implements CaptchaAware {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String captcha;
}
