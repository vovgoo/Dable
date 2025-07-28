package com.vovgoo.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService {
    UserDetails getCurrentUserDetails();
}
