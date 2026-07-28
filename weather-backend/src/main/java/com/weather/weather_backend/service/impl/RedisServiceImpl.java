package com.weather.weather_backend.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weather_backend.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public void save(String key,
                     Object value,
                     Duration ttl) {

        redisTemplate.opsForValue().set(key, value, ttl);

        log.debug("Saved cache key={}", key);
    }

    @Override
    public <T> T get(String key,
                     TypeReference<T> typeReference) {

        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return null;
        }

        return objectMapper.convertValue(value, typeReference);
    }

    @Override
    public boolean exists(String key) {

        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public void delete(String key) {

        redisTemplate.delete(key);

        log.debug("Deleted cache key={}", key);
    }

    @Override
    public void deleteByPattern(String pattern) {

        Set<String> keys = redisTemplate.keys(pattern);

        if (keys != null && !keys.isEmpty()) {

            redisTemplate.delete(keys);

            log.debug("Deleted {} cache keys", keys.size());

        }

    }

    @Override
    public void expire(String key,
                       Duration ttl) {

        redisTemplate.expire(key, ttl);

    }

    @Override
    public Long getExpire(String key) {

        return redisTemplate.getExpire(key, TimeUnit.SECONDS);

    }

    @Override
    public Set<String> keys(String pattern) {

        return redisTemplate.keys(pattern);

    }

}