package com.weather.weather_backend.dto.city;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityResponse {

    private Long id;

    private String name;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Long regionId;

    private String regionName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}