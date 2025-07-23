package com.vovgoo.demo.dtos.auth;

import com.vovgoo.demo.dtos.CaptchaAware;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest implements CaptchaAware {

    @NotBlank
    @Size(min = 5, max = 50)
    private String username;

    @Email
    @NotBlank
    private String email;

    @Size(min = 8, max = 255)
    private String password;

    @NotBlank
    private String captcha;
}
