package com.weather.weather_backend.dto.current_weather;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrentWeatherRequest {

    @NotNull
    private Long cityId;

    @NotNull
    private Long stationId;

    @NotNull
    private BigDecimal temperature;

    @NotNull
    private Integer humidity;

    @NotNull
    private Integer pressure;

    private BigDecimal windSpeed;

    private String windDirection;

    private Integer visibility;

    private Integer cloud;

    private BigDecimal uvIndex;

    private String description;

    private String icon;

    @NotNull
    private LocalDateTime observationTime;
}