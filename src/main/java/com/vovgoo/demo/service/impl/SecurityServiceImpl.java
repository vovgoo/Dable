package com.vovgoo.demo.service.impl;

import com.vovgoo.demo.service.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            throw new RuntimeException("User not authenticated");
        }

        return authentication.getName();
    }
}
