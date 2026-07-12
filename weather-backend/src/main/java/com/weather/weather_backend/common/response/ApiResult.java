package com.weather.weather_backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ApiResult<T> {

    private final boolean success;

    private final String message;

    private final T data;

    @Builder.Default
    private final LocalDateTime timestamp = LocalDateTime.now();
}