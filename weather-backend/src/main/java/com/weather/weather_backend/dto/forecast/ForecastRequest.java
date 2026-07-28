package com.weather.weather_backend.dto.forecast;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ForecastRequest {

    @NotNull
    private Long cityId;

    @NotNull
    private Long stationId;

    @NotNull
    private LocalDateTime forecastTime;

    @NotNull
    private BigDecimal temperature;

    private BigDecimal feelsLike;

    private Integer humidity;

    private Integer pressure;

    private BigDecimal windSpeed;

    private String windDirection;

    private Integer cloud;

    private String description;

    private String icon;
}