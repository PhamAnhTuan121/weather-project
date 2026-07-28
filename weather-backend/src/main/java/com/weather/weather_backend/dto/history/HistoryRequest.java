package com.weather.weather_backend.dto.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HistoryRequest {

    private Long cityId;
    private Long stationId;

    private LocalDateTime observationTime;

    private BigDecimal temperature;
    private BigDecimal feelsLike;

    private Integer humidity;
    private Integer pressure;

    private BigDecimal windSpeed;
    private String windDirection;

    private Integer visibility;
    private Integer cloud;

    private BigDecimal uvIndex;

    private String description;
    private String icon;
}
