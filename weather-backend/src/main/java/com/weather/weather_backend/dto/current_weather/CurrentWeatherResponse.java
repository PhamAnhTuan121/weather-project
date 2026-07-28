package com.weather.weather_backend.dto.current_weather;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrentWeatherResponse {

    private Long id;

    private Long cityId;

    private String cityName;

    private Long stationId;

    private String stationName;

    private BigDecimal temperature;

    private Integer humidity;

    private Integer pressure;

    private BigDecimal windSpeed;

    private String windDirection;

    private Integer visibility;

    private Integer cloud;

    private BigDecimal uvIndex;

    private String description;

    private String icon;

    private LocalDateTime observationTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}