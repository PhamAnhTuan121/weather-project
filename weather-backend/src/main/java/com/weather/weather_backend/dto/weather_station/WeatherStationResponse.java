package com.weather.weather_backend.dto.weather_station;

import com.weather.weather_backend.entity.StationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherStationResponse {

    private Long id;

    private String stationCode;

    private String stationName;

    private String provider;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private StationStatus status;

    private Long cityId;

    private String cityName;

    private Long regionId;

    private String regionName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}