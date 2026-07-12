package com.weather.weather_backend.dto.region;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegionRequest {

    @NotBlank(message = "Region name is required")
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

}