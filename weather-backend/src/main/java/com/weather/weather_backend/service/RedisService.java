package com.weather.weather_backend.service;

import com.fasterxml.jackson.core.type.TypeReference;

import java.time.Duration;
import java.util.Set;

public interface RedisService {

    void save(String key,
              Object value,
              Duration ttl);

    <T> T get(String key, TypeReference<T> typeReference);

    boolean exists(String key);

    void delete(String key);

    void deleteByPattern(String pattern);

    void expire(String key,
                Duration ttl);

    Long getExpire(String key);

    Set<String> keys(String pattern);

}