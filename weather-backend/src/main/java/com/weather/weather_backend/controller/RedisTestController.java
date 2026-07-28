package com.weather.weather_backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.weather.weather_backend.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/cache")
@RequiredArgsConstructor
public class RedisTestController {

    private final RedisService redisService;

    @GetMapping("/test")
    public String test() {

        redisService.save(
                "hello",
                "Redis Working",
                Duration.ofMinutes(5));

        return redisService.get(
                "hello",
                new TypeReference<String>() {
                });

    }

}