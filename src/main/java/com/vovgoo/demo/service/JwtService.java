package com.vovgoo.demo.service;

public interface JwtService {
    String generateToken(String username);
    String extractUsername(String token);
    Boolean validateToken(String token);
    Boolean isTokenExpired(String token);
}
