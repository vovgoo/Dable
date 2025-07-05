package com.vovgoo.demo.service;

import com.vovgoo.demo.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService {
    String getAuthenticatedUsername();
}
