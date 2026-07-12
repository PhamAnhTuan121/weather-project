package com.weather.weather_backend.dto.region;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionResponse {

    private Long id;

    private String name;

    private String description;

}