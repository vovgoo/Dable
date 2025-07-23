package com.vovgoo.demo.dtos.user;

import com.vovgoo.demo.entity.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private Role role;
}
