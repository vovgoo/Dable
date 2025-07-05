package com.vovgoo.demo.controllers;

import com.vovgoo.demo.dtos.user.UserResponse;
import com.vovgoo.demo.entity.User;
import com.vovgoo.demo.mappers.UserResponseMapper;
import com.vovgoo.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/self")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserResponseMapper userResponseMapper;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(userResponseMapper.toDto(currentUser));
    }

}
