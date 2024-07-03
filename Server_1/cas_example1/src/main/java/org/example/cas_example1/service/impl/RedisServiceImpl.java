package org.example.cas_example1.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.cas_example1.service.RedisService;
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
    RedisTemplate<String,String> redisTemplate;


    @Override
    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 60, TimeUnit.MINUTES);
        log.debug("Saved key: {}, value: {}", key, value);
    }

    @Override
    public String get(String key) {
        String value = redisTemplate.opsForValue().get(key);
        log.debug("Retrieved value for key {}: {}", key, value);
        return value;
    }

}
