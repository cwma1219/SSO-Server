package org.example.cas.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.cas.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    HashOperations hashOperations;

    public RedisServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(String key, Map<String, String> value) {
        for (Map.Entry<String, String> entry : value.entrySet()) {
            log.debug("key: {},entryKey: {}, value: {}", key, entry.getKey(), entry.getValue());
            hashOperations.put(key, entry.getKey(), entry.getValue());
        }
        redisTemplate.expire(key, 60, TimeUnit.MINUTES);
    }

    @Override
    public Map<String, String> get(String key) {
        return hashOperations.entries(key);
    }

    @Override
    public void deleteHash(String hKey, String key) {
        hashOperations.delete(hKey, key);
    }

}
