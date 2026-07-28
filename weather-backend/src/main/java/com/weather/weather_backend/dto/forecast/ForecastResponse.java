package com.weather.weather_backend.dto.forecast;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ForecastResponse {

    private Long id;

    private Long cityId;

    private String cityName;

    private Long stationId;

    private String stationCode;

    private String stationName;

    private LocalDateTime forecastTime;

    private BigDecimal temperature;

    private BigDecimal feelsLike;

    private Integer humidity;

    private Integer pressure;

    private BigDecimal windSpeed;

    private String windDirection;

    private Integer cloud;

    private String description;

    private String icon;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}