package com.vovgoo.demo.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {

    @Schema(
            description = "JWT access token used for authenticated requests. Pass it in the Authorization header as: Bearer <token>",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    private String token;
}
