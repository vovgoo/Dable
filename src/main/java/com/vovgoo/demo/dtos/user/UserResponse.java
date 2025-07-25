package com.vovgoo.demo.dtos.user;

import com.vovgoo.demo.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserResponse {

    @Schema(
            description = "Уникальный идентификатор пользователя",
            example = "c8a3fc54-402d-4b45-b1e2-3c25b5c5b6a1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID id;

    @Schema(
            description = "Имя пользователя (username)",
            example = "john_doe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @Schema(
            description = "Электронная почта пользователя",
            example = "john.doe@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @Schema(
            description = "Дата и время создания пользователя",
            example = "2024-06-01T12:34:56",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Роль пользователя в системе",
            example = "USER",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Role role;
}
