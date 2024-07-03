package org.example.cas_example1.service;

import java.util.Map;

public interface RedisService {

    String get(String key);

    void save(String key, String value);
}
