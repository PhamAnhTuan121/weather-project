package com.weather.weather_backend.dto.weather_station;


import com.weather.weather_backend.entity.StationStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherStationRequest {

    @NotBlank(message = "Station code is required")
    @Size(max = 50)
    private String stationCode;

    @NotBlank(message = "Station name is required")
    @Size(max = 150)
    private String stationName;

    @NotBlank(message = "Provider is required")
    @Size(max = 50)
    private String provider;

    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private BigDecimal latitude;

    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private BigDecimal longitude;

    @NotNull(message = "Status is required")
    private StationStatus status;

    @NotNull(message = "City id is required")
    private Long cityId;
}