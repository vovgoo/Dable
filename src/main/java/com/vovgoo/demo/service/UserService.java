package com.vovgoo.demo.service;

import com.vovgoo.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getCurrentUser();
}
