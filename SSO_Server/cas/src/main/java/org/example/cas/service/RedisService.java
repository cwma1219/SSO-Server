package org.example.cas.service;

import java.util.Map;

public interface RedisService {
    void save(String key, Map<String, String> value);

    Map<String, String> get(String key);

    void deleteHash(String hKey, String key);

}
