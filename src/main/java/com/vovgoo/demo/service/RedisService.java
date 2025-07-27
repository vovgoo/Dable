package com.vovgoo.demo.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {
    void setValue(String key, String value);
    boolean setIfAbsent(String key, String value, long timeout, TimeUnit unit);
    void setValue(String key, String value, long timeout, TimeUnit unit);
    String getValue(String key);
    void deleteValue(String key);
    boolean hasKey(String key);
}
