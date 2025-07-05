package com.vovgoo.demo.dtos.user;

import com.vovgoo.demo.entity.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private Role role;
}
