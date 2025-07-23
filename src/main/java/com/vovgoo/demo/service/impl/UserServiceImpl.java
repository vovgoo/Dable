package com.vovgoo.demo.service.impl;

import com.vovgoo.demo.entity.User;
import com.vovgoo.demo.repository.UserRepository;
import com.vovgoo.demo.service.SecurityService;
import com.vovgoo.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found user with username: " + username));
    }

    @Override
    public User getCurrentUser() {
        String username = securityService.getAuthenticatedUsername();
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found user with username: " + username));
    }
}
