package com.vovgoo.demo.utils.redis;

public final class RedisKeys {

    public static String signUpEmailKey(String email) {
        return "signup:email:%s".formatted(email);
    }

    public static String signUpTokenKey(String token) {
        return "signup:token:%s".formatted(token);
    }
}